package org.needle4j.processor;

import org.needle4j.NeedleContext;

public interface NeedleProcessor {

    /**
     * @param context
     *            needle context for test class
     */
    void process(NeedleContext context);
}
