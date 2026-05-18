package org.knowm.dropwizard.sundial.tasks;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.sundial.SundialJobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.servlets.tasks.Task;

/**
 * @author timmolter
 */
public class StartJobTask extends Task {

  @FunctionalInterface
  public interface JobStarter {
    void start(String jobName, Map<String, Object> params) throws Exception;
  }

  private final Logger logger = LoggerFactory.getLogger(StartJobTask.class);
  private final JobStarter jobStarter;

  public StartJobTask() {
    this(SundialJobScheduler::startJob);
  }

  public StartJobTask(JobStarter jobStarter) {
    super("startjob");
    this.jobStarter = jobStarter;
  }

  @Override
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {

    logger.info(parameters.toString());

    Map<String, Object> params = new HashMap<String, Object>();

    for (Entry<String, List<String>> entry : parameters.entrySet()) {
      params.put(entry.getKey(), entry.getValue().get(0));
    }

    if (!parameters.containsKey("JOB_NAME")) {
      throw new IllegalArgumentException("No JOB_NAME specified");
    }
    String jobName = parameters.get("JOB_NAME").get(0);

    jobStarter.start(jobName, params);
  }
}
