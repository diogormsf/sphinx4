/*
 * Copyright 2013 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */

package edu.cmu.sphinx.api;

import java.io.IOException;

import edu.cmu.sphinx.frontend.util.StreamDataSource;
import edu.cmu.sphinx.recognizer.Recognizer;


/**
 * High-level class for live speech recognition.
 */
public class LiveSpeechRecognizer extends AbstractSpeechRecognizer {

    private final Microphone microphone;

    /**
     * Constructs new live recognition object.
     *
     * @param configuration common configuration
     * @throws IOException if model IO went wrong
     */
    public LiveSpeechRecognizer(Configuration configuration) throws IOException
    {
        super(configuration);
        microphone = speechSourceProvider.getMicrophone();
        context.getInstance(StreamDataSource.class)
            .setInputStream(microphone.getStream());
    }

    /**
     * Starts recognition process.
     *
     * @param clear clear cached microphone data
     * @see         LiveSpeechRecognizer#stopRecognition()
     */
    public void startRecognition(boolean clear) {
        recognizer.allocate();
        microphone.startRecording();
    }

    /**
     * Stops recognition process.
     *
     * Recognition process is paused until the next call to startRecognition.
     *
     * @see LiveSpeechRecognizer#startRecognition(boolean)
     */
    public void stopRecognition() {
        microphone.stopRecording();
        recognizer.deallocate();
    }


    /**
     * Stops Recognition process.
     *
     * Closes the Microphone Connection and Data Line is made available for other applications.
     */
    public void closeRecognizer(){
        microphone.stopRecording();
        recognizer.deallocate();
        microphone.closeConnection();
    }

    /**
     * Returns the current context to change the grammar in real time
     * NOT INITIALLY IMPLEMENTED BY THE LIBRARY
     * @return { Context } the current context of the recognizer
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * Returns the current microphone to force close the recognizer
     * NOT INITIALLY IMPLEMENTED BY THE LIBRARY
     * @return { Microphone } the current microphone
     */
    public Microphone getMicrophone() {
        return this.microphone;
    }

    /**
     * Returns the current recognizer instance to change the state of the recognizer
     * NOT INITIALLY IMPLEMENTED BY THE LIBRARY
     * @return { Recognizer } the current recognizer instance
     */
    public Recognizer getRecognizer() { return this.recognizer; }
}
