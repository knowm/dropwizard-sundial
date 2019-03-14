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
public class RemoveJobTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(RemoveJobTask.class);

  /**
   * Constructor
   */
  public RemoveJobTask() {

    super("removejob");
  }

  @Override
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {

    logger.info(parameters.toString());

    String jobName = (String) parameters.get("JOB_NAME").toArray()[0];

    SundialJobScheduler.removeJob(jobName);

  }
}
