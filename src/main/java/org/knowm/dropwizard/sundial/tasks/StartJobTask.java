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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMultimap;
import org.knowm.sundial.SundialJobScheduler;

/**
 * @author timmolter
 */
public class StartJobTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(StartJobTask.class);

  /**
   * Constructor
   */
  public StartJobTask() {

    super("startjob");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {

    logger.info(parameters.toString());

    Map<String, Object> params = new HashMap<String, Object>();

    for (Entry<String, String> entry : parameters.entries()) {
      params.put(entry.getKey(), entry.getValue());
    }

    if (!parameters.containsKey("JOB_NAME")) {
        throw new IllegalArgumentException("No JOB_NAME specified");
    }
    String jobName = (String) parameters.get("JOB_NAME").toArray()[0];

    SundialJobScheduler.startJob(jobName, params);
  }
}
