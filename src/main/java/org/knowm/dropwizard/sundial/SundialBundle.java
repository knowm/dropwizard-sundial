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
package org.knowm.dropwizard.sundial;

import org.eclipse.jetty.server.Server;
import org.knowm.dropwizard.sundial.tasks.AddCronJobTriggerTask;
import org.knowm.dropwizard.sundial.tasks.AddJobTask;
import org.knowm.dropwizard.sundial.tasks.LockSundialSchedulerTask;
import org.knowm.dropwizard.sundial.tasks.RemoveJobTask;
import org.knowm.dropwizard.sundial.tasks.RemoveJobTriggerTask;
import org.knowm.dropwizard.sundial.tasks.StartJobTask;
import org.knowm.dropwizard.sundial.tasks.StopJobTask;
import org.knowm.dropwizard.sundial.tasks.UnlockSundialSchedulerTask;
import org.knowm.sundial.SundialJobScheduler;
import org.knowm.sundial.ee.SundialInitializerListener;
import org.quartz.exceptions.SchedulerException;
import org.quartz.listeners.ListenerManager;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author timmolter
 */
public abstract class SundialBundle<T extends Configuration> implements ConfiguredBundle<T> {

  public abstract SundialConfiguration getSundialConfiguration(T configuration);

  @Override
  public void initialize(Bootstrap<?> bootstrap) {

  }

  @Override
  public void run(T configuration, final Environment environment) throws Exception {

    SundialConfiguration sundialConfiguration = getSundialConfiguration(configuration);

    // this sets up Sundial with all the default values
    environment.servlets().addServletListeners(new SundialInitializerListener());

    // here we can override the defaults
    if (sundialConfiguration.getThreadPoolSize() != null) {
      environment.servlets().setInitParameter("thread-pool-size", sundialConfiguration.getThreadPoolSize());
    }
    if (sundialConfiguration.getPerformShutdown() != null) {
      environment.servlets().setInitParameter("shutdown-on-unload", sundialConfiguration.getPerformShutdown());
    }
    if (sundialConfiguration.getWaitOnShutdown() != null) {
      environment.servlets().setInitParameter("wait-on-shutdown", sundialConfiguration.getWaitOnShutdown());
    }
    if (sundialConfiguration.getStartDelay() != null) {
      environment.servlets().setInitParameter("start-delay-seconds", sundialConfiguration.getStartDelay());
    }
    if (sundialConfiguration.getStartOnLoad() != null) {
      environment.servlets().setInitParameter("start-scheduler-on-load", sundialConfiguration.getStartOnLoad());
    }
    if (sundialConfiguration.getGlobalLockOnLoad() != null) {
      environment.servlets().setInitParameter("global-lock-on-load", sundialConfiguration.getGlobalLockOnLoad());
    }
    if (sundialConfiguration.getGlobalLockOnLoad() != null) {
      environment.servlets().setInitParameter("global-lock-on-load", sundialConfiguration.getGlobalLockOnLoad());
    }
    if (sundialConfiguration.getAnnotatedJobsPackageName() != null) {
      environment.servlets().setInitParameter("annotated-jobs-package-name", sundialConfiguration.getAnnotatedJobsPackageName());
    }

    environment.admin().addTask(new LockSundialSchedulerTask());
    environment.admin().addTask(new UnlockSundialSchedulerTask());
    environment.admin().addTask(new RemoveJobTriggerTask());
    environment.admin().addTask(new AddCronJobTriggerTask());
    environment.admin().addTask(new StartJobTask());
    environment.admin().addTask(new StopJobTask());
    environment.admin().addTask(new RemoveJobTask());
    environment.admin().addTask(new AddJobTask());

    environment.lifecycle().addServerLifecycleListener(new ServerLifecycleListener() {
      @Override
      public void serverStarted(Server server) {
        final ListenerManager listenerManager;
        try {
          listenerManager = SundialJobScheduler.getScheduler().getListenerManager();
        } catch (SchedulerException e) {
          throw new RuntimeException(e);
        }

        listenerManager.addTriggerListener(new MetricsReporter(environment.metrics()));
      }
    });
  }
}
