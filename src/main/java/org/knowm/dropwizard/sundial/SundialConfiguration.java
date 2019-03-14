/**
 * Copyright 2015 Knowm Inc. (http://knowm.org) and contributors. Copyright 2014-2015 Xeiam LLC
 * (http://xeiam.com) and contributors.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.knowm.dropwizard.sundial;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

/** @author timmolter */
public class SundialConfiguration {

  @JsonProperty("thread-pool-size")
  private String threadPoolSize;

  @JsonProperty("shutdown-on-unload")
  private String performShutdown;

  @JsonProperty("start-delay-seconds")
  private String startDelay;

  @JsonProperty("start-scheduler-on-load")
  private String startOnLoad;

  @JsonProperty("global-lock-on-load")
  private String globalLockOnLoad;

  @JsonProperty("annotated-jobs-package-name")
  private String annotatedJobsPackageName;

  @JsonProperty("tasks")
  private Set<String> tasks;

  public String getThreadPoolSize() {

    return threadPoolSize;
  }

  public void setThreadPoolSize(String threadPoolSize) {

    this.threadPoolSize = threadPoolSize;
  }

  public String getPerformShutdown() {

    return performShutdown;
  }

  public void setPerformShutdown(String performShutdown) {

    this.performShutdown = performShutdown;
  }

  public String getStartDelay() {

    return startDelay;
  }

  public void setStartDelay(String startDelay) {

    this.startDelay = startDelay;
  }

  public String getStartOnLoad() {

    return startOnLoad;
  }

  public void setStartOnLoad(String startOnLoad) {

    this.startOnLoad = startOnLoad;
  }

  public String getGlobalLockOnLoad() {

    return globalLockOnLoad;
  }

  public void setGlobalLockOnLoad(String globalLockOnLoad) {

    this.globalLockOnLoad = globalLockOnLoad;
  }

  public String getAnnotatedJobsPackageName() {
    return annotatedJobsPackageName;
  }

  public void setAnnotatedJobsPackageName(String annotatedJobsPackageName) {
    this.annotatedJobsPackageName = annotatedJobsPackageName;
  }

  public Set<String> getTasks() {
    return tasks;
  }
}
