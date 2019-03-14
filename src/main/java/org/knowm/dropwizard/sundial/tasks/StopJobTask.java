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
public class StopJobTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(StopJobTask.class);

  /**
   * Constructor
   */
  public StopJobTask() {

    super("stopjob");
  }

  @Override
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {

    logger.info(parameters.toString());

    String jobName = parameters.get("JOB_NAME").get(0);

    SundialJobScheduler.stopJob(jobName);

  }
}
