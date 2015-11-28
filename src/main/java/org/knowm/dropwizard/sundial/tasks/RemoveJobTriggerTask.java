package org.knowm.dropwizard.sundial.tasks;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import org.knowm.sundial.SundialJobScheduler;

/**
 * @author timmolter
 */
public class RemoveJobTriggerTask extends Task {

  /**
   * Constructor
   */
  public RemoveJobTriggerTask() {

    super("removejobtrigger");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {

    ImmutableCollection<String> triggerNames = parameters.get("TRIGGER_NAME");

    for (String triggerName : triggerNames) {

      SundialJobScheduler.removeTrigger(triggerName);
    }

  }
}
