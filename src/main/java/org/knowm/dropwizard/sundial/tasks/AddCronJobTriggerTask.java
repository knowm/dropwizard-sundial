/**
 * Copyright 2015 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2014-2015 Xeiam LLC (http://xeiam.com) and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.knowm.dropwizard.sundial.tasks;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

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
  public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {

    String triggerName = (String) parameters.get("TRIGGER_NAME").toArray()[0];
    String jobName = (String) parameters.get("JOB_NAME").toArray()[0];
    String cronExpression = (String) parameters.get("CRON_EXPRESSION").toArray()[0];

    logger.debug(triggerName + " " + jobName + " " + cronExpression);

    SundialJobScheduler.addCronTrigger(triggerName, jobName, cronExpression);

  }
}
