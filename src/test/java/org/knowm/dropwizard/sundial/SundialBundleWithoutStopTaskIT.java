package org.knowm.dropwizard.sundial;

import static org.assertj.core.api.Assertions.assertThat;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import jakarta.ws.rs.client.Client;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class SundialBundleWithoutStopTaskIT extends SundialBundleITBase {
  public static DropwizardAppExtension<TestConfiguration> app =
      buildApp(
          "test.yml",
          new Before() {
            public void before(Environment environment) {
              client = new JerseyClientBuilder(environment).build("test");
            }
          });

  @BeforeAll
  public static void beforeClass() throws Exception {
    app.before();
  }

  @AfterAll
  public static void afterClass() {
    app.after();
  }

  static Client client;

  @Test
  public void shouldRegisterTasks() throws InterruptedException {
    final int start;

    synchronized (TestJob.lock) {
      start = TestJob.runs;
      startJob(app, client, TestApp.JOB_NAME);
      while (TestJob.runs == start) {
        TestJob.lock.wait(1000);
        assert TestJob.runs != start : "Timed out";
      }
    }

    assertThat(TestJob.runs).isEqualTo(start + 1);

    assertThat(stopJob(app, client, TestApp.JOB_NAME)).isFalse();
  }
}
