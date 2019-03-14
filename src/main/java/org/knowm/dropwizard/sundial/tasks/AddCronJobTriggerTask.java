package org.knowm.dropwizard.sundial.tasks;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMultimap;
import org.knowm.sundial.SundialJobScheduler;

/**
 * @author timmolter
 */
public class AddCronJobTriggerTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(AddCronJobTriggerTask.class);

  /**
   * Constructor
   */
  public AddCronJobTriggerTask() {

    super("addcronjobtrigger");
  }

  @Override
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {

    String triggerName = parameters.get("TRIGGER_NAME").get(0);
    String jobName = parameters.get("JOB_NAME").get(0);
    String cronExpression =parameters.get("CRON_EXPRESSION").get(0);

    logger.debug(triggerName + " " + jobName + " " + cronExpression);

    SundialJobScheduler.addCronTrigger(triggerName, jobName, cronExpression);

  }
}
