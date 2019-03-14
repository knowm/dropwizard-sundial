package org.knowm.dropwizard.sundial;

import static org.assertj.core.api.Assertions.assertThat;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Server;
import org.knowm.sundial.Job;
import org.knowm.sundial.SundialJobScheduler;
import org.knowm.sundial.exceptions.JobInterruptException;

import com.fasterxml.jackson.databind.JsonNode;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class SundialBundleITBase {
    public interface Before {
        void before(Environment environment);
    }

    public static DropwizardAppRule<TestConfiguration> buildApp(
        String configFile, final Before before
    ) {
        return new DropwizardAppRule<TestConfiguration>(
            new DropwizardTestSupport<TestConfiguration>(
                TestApp.class, ResourceHelpers.resourceFilePath(configFile)
            ) {
                @Override
                public void before() throws Exception {
                    super.before();
                    before.before(getEnvironment());
                }
            }
        );
    }

    public static class TestApp extends Application<TestConfiguration> {
        public static String JOB_NAME = "TestJob";

        @Override
        public void initialize(Bootstrap<TestConfiguration> bootstrap) {
            super.initialize(bootstrap);
            bootstrap.addBundle(new SundialBundle<TestConfiguration>() {
                @Override
                public SundialConfiguration getSundialConfiguration(TestConfiguration configuration) {
                    return configuration.sundial;
                }
            });
        }

        @Override
        public void run(TestConfiguration testConfiguration, Environment environment) throws Exception {
            environment.lifecycle().addServerLifecycleListener(new ServerLifecycleListener() {
                @Override
                public void serverStarted(Server server) {
                    SundialJobScheduler.addJob(JOB_NAME, TestJob.class);
                }
            });
        }
    }

    public static class TestConfiguration extends Configuration {
        public SundialConfiguration sundial;
    }

    public static class TestJob extends Job {
        static int runs = 0;
        public static final Object lock = new Object();

        @Override
        public void doRun() throws JobInterruptException {
            synchronized (lock) {
                runs++;
                lock.notifyAll();
            }
        }

    }

    public static void startJob(DropwizardAppRule<TestConfiguration> app, Client client, String jobName) {
        Response response = client.target(
            String.format(
                "http://localhost:%d/%s/tasks/startjob?JOB_NAME=%s",
                app.getAdminPort(), app.getEnvironment().getAdminContext().getContextPath(), jobName
            ))
            .request()
            .post(Entity.text(""));

        assertThat(response.getStatus()).isEqualTo(200);
    }

    public static boolean stopJob(DropwizardAppRule<TestConfiguration> app, Client client, String jobName) {
        Response response = client.target(
            String.format(
                "http://localhost:%d/%s/tasks/stopjob?JOB_NAME=%s",
                app.getAdminPort(), app.getEnvironment().getAdminContext().getContextPath(), jobName
            ))
            .request()
            .post(Entity.text(""));

        return response.getStatus() == 200;
    }

    public static int readTimerCount(DropwizardAppRule<TestConfiguration> app, Client client, String timerName) {
        Response response = client.target(
            String.format(
                "http://localhost:%d/%s/metrics",
                app.getAdminPort(), app.getEnvironment().getAdminContext().getContextPath()
            ))
            .request()
            .get();

        assertThat(response.getStatus()).isEqualTo(200);

        JsonNode body = response.readEntity(JsonNode.class);

        if (!body.get("timers").has(timerName)) {
            return 0;
        }
        return body.get("timers").get(timerName).get("count").intValue();
    }
}
