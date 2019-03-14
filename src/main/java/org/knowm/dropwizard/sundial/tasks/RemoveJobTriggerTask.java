package org.knowm.dropwizard.sundial.tasks;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import java.util.List;
import java.util.Map;
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
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {

    List<String> triggerNames = parameters.get("TRIGGER_NAME");

    for (String triggerName : triggerNames) {

      SundialJobScheduler.removeTrigger(triggerName);
    }

  }
}
