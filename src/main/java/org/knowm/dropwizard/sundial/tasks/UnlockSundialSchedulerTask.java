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
public class UnlockSundialSchedulerTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(UnlockSundialSchedulerTask.class);

  /**
   * Constructor
   */
  public UnlockSundialSchedulerTask() {

    super("unlocksundialscheduler");
  }

  @Override
  public void execute(Map<String, List<String>> arg0, PrintWriter arg1) throws Exception {

    logger.info("Locking Sundial Scheduler...");

    SundialJobScheduler.unlockScheduler();

  }

}
