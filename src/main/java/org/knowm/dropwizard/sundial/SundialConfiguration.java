package org.knowm.dropwizard.sundial;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */

public class SundialConfiguration {

  @JsonProperty("thread-pool-size")
  private String threadPoolSize;

  @JsonProperty("shutdown-on-unload")
  private String performShutdown;

  @JsonProperty("wait-on-shutdown")
  private String waitOnShutdown;

  @JsonProperty("start-delay-seconds")
  private String startDelay;

  @JsonProperty("start-scheduler-on-load")
  private String startOnLoad;

  @JsonProperty("global-lock-on-load")
  private String globalLockOnLoad;

  @JsonProperty("annotated-jobs-package-name")
  private String annotatedJobsPackageName;

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

  public String getWaitOnShutdown() {

    return waitOnShutdown;
  }

  public void setWaitOnShutdown(String waitOnShutdown) {

    this.waitOnShutdown = waitOnShutdown;
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

}
