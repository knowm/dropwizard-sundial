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
public class AddJobTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(AddJobTask.class);

  /** Constructor */
  public AddJobTask() {

    super("addjob");
  }

  @Override
  public void execute(Map<String, List<String>> parameters, PrintWriter output) throws Exception {

    logger.info(parameters.toString());

    Map<String, Object> params = new HashMap<>();

    for (Entry<String, List<String>> entry : parameters.entrySet()) {
      params.put(entry.getKey(), entry.getValue().get(0));
    }

    String jobName = parameters.get("JOB_NAME").get(0);
    String jobClass = parameters.get("JOB_CLASS").get(0);

    SundialJobScheduler.addJob(jobName, jobClass, params, false);
  }
}
