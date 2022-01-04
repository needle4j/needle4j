package org.needle4j.processor;

import org.needle4j.NeedleContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.needle4j.common.Preconditions.checkArgument;

/**
 * {@link NeedleProcessor} that calls chain of processors.
 */
public class ChainedNeedleProcessor implements NeedleProcessor {
  private final List<NeedleProcessor> processors = new ArrayList<>();

  public ChainedNeedleProcessor(final NeedleProcessor... processors) {
    addProcessor(processors);
  }

  public void addProcessor(final NeedleProcessor... processors) {
    Collections.addAll(this.processors, processors);
  }

  @Override
  public void process(final NeedleContext context) {
    checkArgument(context != null, "context must not be null");

    for (final NeedleProcessor processor : this.processors) {
      processor.process(context);
    }
  }
}
