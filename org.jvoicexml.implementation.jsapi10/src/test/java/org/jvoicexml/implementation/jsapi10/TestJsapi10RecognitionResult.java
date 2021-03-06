/*
 * File:    $HeadURL$
 * Version: $LastChangedRevision$
 * Date:    $Date$
 * Author:  $LastChangedBy$
 *
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2009-2013 JVoiceXML group - http://jvoicexml.sourceforge.net
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
package org.jvoicexml.implementation.jsapi10;

import java.io.StringReader;

import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.RuleGrammar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jvoicexml.event.error.SemanticError;

import com.cloudgarden.speech.CGEngineCentral;

/**
 * Test cases for {@link Jsapi10RecognitionResult}.
 * 
 * @author Dirk Schnelle-Walka
 * @version $Revision$
 * @since 0.7.2
 */
public class TestJsapi10RecognitionResult {
    /** The recognizer. */
    private Recognizer recognizer;

    /**
     * Global initialization.
     * 
     * @throws EngineException
     *             error registering the engine.
     */
    @BeforeClass
    public static void init() throws EngineException {
        Central.registerEngineCentral(CGEngineCentral.class.getName());
    }

    /**
     * Set up the test environment.
     * 
     * @throws Exception
     *             test failed
     * @since 0.7.3
     */
    @Before
    public void setUp() throws Exception {
//        final RecognizerModeDesc desc = new SphinxRecognizerModeDesc(config);
        recognizer = Central.createRecognizer(null);
        recognizer.allocate();
        recognizer.waitEngineState(Recognizer.ALLOCATED);
    }

    /**
     * Tear down the test environment.
     * 
     * @throws Exception
     *             test failed
     * @since 0.7.3
     */
    public void tearDown() throws Exception {
        if (recognizer != null) {
            recognizer.deallocate();
        }
    }

    /**
     * Test method for
     * {@link org.jvoicexml.implementation.jsapi10.Jsapi10RecognitionResult#getSemanticInterpretation()}
     * .
     * 
     * @exception Exception
     *                test failed
     */
    @Test
    public void testGetSemanticInterpretation() throws Exception, SemanticError {
        final String lf = System.getProperty("line.separator");
        final String grammar = "#JSGF V1.0;" + lf + "grammar test;" + lf
                + "public <test> = a{student.name='horst'}|b|c;";
        final StringReader reader = new StringReader(grammar);
        final RuleGrammar rule = recognizer.loadJSGF(reader);
        rule.setEnabled(true);
//        final BaseResult result = new BaseResult(rule, "a");
//        result.setResultState(BaseResult.ACCEPTED);
////
////        final DataModel model = Mockito.mock(DataModel.class);
////        final Jsapi10RecognitionResult res = new Jsapi10RecognitionResult(
////                result);
////        res.getSemanticInterpretation(model);
//        Mockito.verify(model).updateVariable("out.student.name", "horst");
        // Assert.assertEquals("horst", context.evaluateString(scope,
        // "out.student.name", "expr", 1, null));
    }

    /**
     * Test method for
     * {@link org.jvoicexml.implementation.jsapi10.Jsapi10RecognitionResult#getSemanticInterpretation()}
     * .
     * 
     * @exception Exception
     *                test failed
     */
    @Test
    public void testGetSemanticInterpretationSimple() throws Exception,
            SemanticError {
        final String lf = System.getProperty("line.separator");
        final String grammar = "#JSGF V1.0;" + lf + "grammar test;" + lf
                + "public <test> = yes{true}|no{false}|one{1234}|two{'horst'};";
        final StringReader reader = new StringReader(grammar);
        final RuleGrammar rule = recognizer.loadJSGF(reader);
        rule.setEnabled(true);

//        final BaseResult result1 = new BaseResult(rule, "yes");
//        result1.setResultState(BaseResult.ACCEPTED);
//        final DataModel model = Mockito.mock(DataModel.class);
//        final Jsapi10RecognitionResult res1 = new Jsapi10RecognitionResult(
//                result1);
//        res1.getSemanticInterpretation(model);
//        Mockito.verify(model).updateVariable("out", true);
        // Assert.assertEquals(Boolean.TRUE,
        // context.evaluateString(scope, "out", "expr", 1, null));

//        final BaseResult result2 = new BaseResult(rule, "one");
//        result2.setResultState(BaseResult.ACCEPTED);
//        final Jsapi10RecognitionResult res2 = new Jsapi10RecognitionResult(
//                result2);
//        res2.getSemanticInterpretation(model);
//        Mockito.verify(model).updateVariable("out", 1234);
        // Assert.assertEquals(new Integer(1234),
        // context.evaluateString(scope, "out", "expr", 1, null));

//        final BaseResult result3 = new BaseResult(rule, "two");
//        result3.setResultState(BaseResult.ACCEPTED);
//        final Jsapi10RecognitionResult res3 = new Jsapi10RecognitionResult(
//                result3);
//        res3.getSemanticInterpretation(model);
//        Mockito.verify(model).updateVariable("out", "horst");
        // Assert.assertEquals("horst",
        // context.evaluateString(scope, "out", "expr", 1, null));
    }
}
