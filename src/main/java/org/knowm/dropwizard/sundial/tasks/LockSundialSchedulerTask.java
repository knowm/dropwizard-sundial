package org.knowm.dropwizard.sundial.tasks;

import io.dropwizard.servlets.tasks.Task;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import org.knowm.sundial.SundialJobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author timmolter
 */
public class LockSundialSchedulerTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(LockSundialSchedulerTask.class);

  /** Constructor */
  public LockSundialSchedulerTask() {

    super("locksundialscheduler");
  }

  @Override
  public void execute(Map<String, List<String>> arg0, PrintWriter arg1) throws Exception {

    logger.info("Locking Sundial Scheduler...");

    SundialJobScheduler.lockScheduler();
  }
}
