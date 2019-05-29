/*
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2006-2019 JVoiceXML group - http://jvoicexml.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jvoicexml.implementation;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import org.jvoicexml.DtmfRecognizerProperties;
import org.jvoicexml.SpeechRecognizerProperties;
import org.jvoicexml.event.error.BadFetchError;
import org.jvoicexml.event.error.NoresourceError;
import org.jvoicexml.event.error.UnsupportedFormatError;
import org.jvoicexml.event.error.UnsupportedLanguageError;
import org.jvoicexml.interpreter.datamodel.DataModel;
import org.jvoicexml.xml.srgs.GrammarType;
import org.jvoicexml.xml.srgs.ModeType;
import org.jvoicexml.xml.vxml.BargeInType;

/**
 * Facade for easy control and monitoring of the user's speech input as an
 * external resource.
 *
 * <p>
 * Objects that implement this interface are able to detect spoken input and to
 * control input detection interval duration with a timer whose length is
 * specified by a VoiceXML document.
 * </p>
 *
 * <p>
 * If an input resource is not available, an <code>error.noresource</code> event
 * must be thrown.
 * </p>
 * <p>
 * It is guaranteed that the session remains the same between the calls to
 * {@link org.jvoicexml.RemoteConnectable#connect(org.jvoicexml.ConnectionInformation)}
 * and
 * {@link org.jvoicexml.RemoteConnectable#disconnect(org.jvoicexml.ConnectionInformation)}.
 * </p>
 *
 * @author Dirk Schnelle-Walka
 * @since 0.5
 */
public interface UserInputImplementation extends ExternalResource {
    /**
     * Retrieves the mode type for this user input implementation.
     * @return the mode type
     * @since 0.7.9
     */
    ModeType getModeType();
    
    /**
     * Retrieves the grammar types that are supported by this implementation.
     * 
     * @return supported grammars.
     *
     * @since 0.5.5
     */
    Collection<GrammarType> getSupportedGrammarTypes();

    /**
     * Activates the given grammars. It is guaranteed that all grammars types
     * are supported by this implementation.
     * <p>
     * It is guaranteed that the {@link #loadGrammar(URI, GrammarType)} method
     * is always called before a grammar becomes active via this method.
     * </p>
     * 
     * @param grammars
     *            Grammars to activate.
     * @exception BadFetchError
     *                Grammar is not known by the recognizer.
     * @exception UnsupportedLanguageError
     *                The specified language is not supported.
     * @exception UnsupportedFormatError
     *                the grammar format is not supported
     * @exception NoresourceError
     *                The input resource is not available.
     */
    void activateGrammars(Collection<GrammarImplementation<?>> grammars)
            throws BadFetchError, UnsupportedLanguageError,
            UnsupportedFormatError, NoresourceError;

    /**
     * Deactivates the given grammar. Do nothing if the input resource is not
     * available. It is guaranteed that all grammars types are supported by this
     * implementation.
     *
     * @param grammars
     *            Grammars to deactivate.
     *
     * @exception BadFetchError
     *                Grammar is not known by the recognizer.
     * @exception NoresourceError
     *                The input resource is not available.
     */
    void deactivateGrammars(Collection<GrammarImplementation<?>> grammars)
            throws NoresourceError, BadFetchError;

    /**
     * Creates a {@link GrammarImplementation} from the contents provided by the
     * Reader. If the grammar contained in the Reader already exists, it is
     * over-written.
     *
     * <p>
     * This method is mainly needed for non SRGS grammars, e.g. JSGF. The
     * grammar implementation is platform specific, so it the responsibility of
     * the implementation platform to return load it's specific grammar.
     * However, loading an SRGS grammar is quite easy and can be implemented
     * e.g. as
     * </p>
     * <p>
     * <code>
     * final InputSource inputSource = new InputSource(reader);<br>
     * SrgsXmlDocument doc = new SrgsXmlDocument(inputSource);<br>
     * &#47;&#47; Pass it to the recognizer<br>
     * return doc;
     * </code>
     * </p>
     *
     * @param uri
     *            the URI to load the grammar
     * @param type
     *            type of the grammar to read. The type is one of the supported
     *            types of the implementation, that has been requested via
     *            {@link #getSupportedGrammarTypes()}.
     *
     * @return Read grammar.
     *
     * @since 0.3
     *
     * @exception NoresourceError
     *                the input resource is not available.
     * @exception IOException
     *                error reading the grammar.
     * @exception UnsupportedFormatError
     *                invalid grammar format.
     */
    GrammarImplementation<?> loadGrammar(URI uri, GrammarType type)
            throws NoresourceError, IOException, UnsupportedFormatError;

    /**
     * Retrieves the barge-in types supported by this <code>UserInput</code>.
     * 
     * @return Collection of supported barge-in types, an empty collection, if
     *         no types are supported.
     */
    Collection<BargeInType> getSupportedBargeInTypes();

    /**
     * Adds a listener for user input events.
     *
     * <p>
     * The implementation of this interface must notify the listener about all
     * events.
     * </p>
     *
     * @param listener
     *            The listener.
     * @since 0.5
     */
    void addListener(UserInputImplementationListener listener);

    /**
     * Removes a listener for user input events.
     *
     * @param listener
     *            The listener.
     * @since 0.6
     */
    void removeListener(UserInputImplementationListener listener);
    
    /**
     * Detects and reports character and/or spoken input simultaneously.
     *
     * @param model the data model to use for generating a semantic
     *          interpretation
     * @param speech the speech recognizer properties to use
     * @param dtmf the DTMF recognizer properties to use
     * @exception NoresourceError
     * The input resource is not available.
     * @exception BadFetchError
     * The active grammar contains some errors.
     */
    void startRecognition(DataModel model,
            SpeechRecognizerProperties speech,
            DtmfRecognizerProperties dtmf)
            throws NoresourceError, BadFetchError;

    /**
     * Stops a previously started recognition.
     *
     * @see #startRecognition
     */
    void stopRecognition();
}