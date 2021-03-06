/*
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2006-2017 JVoiceXML group - http://jvoicexml.sourceforge.net
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Library General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jvoicexml.implementation.dtmf;

import org.jvoicexml.RecognitionResult;
import org.jvoicexml.interpreter.datamodel.DataModel;
import org.jvoicexml.xml.srgs.ModeType;

/**
 * Result of a DTMF recognition process.
 *
 * @author Dirk Schnelle-Walka
 * @since 0.5
 */
class DtmfInputResult implements RecognitionResult {
    /** The recognized DTMF string. */
    private final String utterance;

    /** The last reached marker. */
    private String marker;

    /** {@code true} if the result is accepted. */
    private boolean accepted;

    /** The semantic interpretation. */
    private Object interpretation;

    /**
     * Constructs a new accepted result..
     * 
     * @param dtmf
     *            the recognized DTMF string.
     */
    DtmfInputResult(final String dtmf) {
        utterance = dtmf;
    }

    /**
     * Constructs a new object.
     * 
     * @param dtmf
     *            the recognized DTMF string.
     * @param isAccepted
     *            <code>true</code> if the result is accepted.
     * @since 0.7
     */
    DtmfInputResult(final String dtmf, final boolean isAccepted) {
        utterance = dtmf;
        accepted = isAccepted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMark() {
        return marker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUtterance() {
        return utterance;
    }

    /**
     * Marks the result as accepted.
     * 
     * @param isAccepted
     *            <code>true</code> if the result is accepted.
     * @since 0.7
     */
    public void setAccepted(final boolean isAccepted) {
        accepted = isAccepted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMark(final String mark) {
        marker = mark;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getConfidence() {
        return 1.0f;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float[] getWordsConfidence() {
        final float[] wordsConfidence = new float[utterance.length()];
        for (int i = 0; i < utterance.length(); i++) {
            wordsConfidence[i] = 1.0f;
        }
        return wordsConfidence;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getWords() {
        final String[] words = new String[utterance.length()];
        for (int i = 0; i < utterance.length(); i++) {
            final char digit = utterance.charAt(i);
            words[i] = Character.toString(digit);
        }
        return words;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeType getMode() {
        return ModeType.DTMF;
    }

    /**
     * Sets the semantic interpretation.
     * @param object the semantic interpretation
     * @since 0.7.8
     */
    public void setSemanticInterpretation(final Object object) {
        interpretation = object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getSemanticInterpretation(final DataModel model) {
        return interpretation;
    }
}
