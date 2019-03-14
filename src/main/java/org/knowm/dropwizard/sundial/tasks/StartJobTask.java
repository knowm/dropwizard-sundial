package org.knowm.dropwizard.sundial.tasks;

import io.dropwizard.servlets.tasks.Task;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.knowm.sundial.SundialJobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author timmolter */
public class StartJobTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(StartJobTask.class);

  /** Constructor */
  public StartJobTask() {

    super("startjob");
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

    SundialJobScheduler.startJob(jobName, params);
  }
}
