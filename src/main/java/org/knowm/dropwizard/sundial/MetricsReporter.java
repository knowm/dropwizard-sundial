package org.knowm.dropwizard.sundial;

import java.util.concurrent.TimeUnit;

import org.quartz.core.JobExecutionContext;
import org.quartz.listeners.TriggerListener;
import org.quartz.triggers.Trigger;

import com.codahale.metrics.MetricRegistry;

class MetricsReporter implements TriggerListener {
    private final MetricRegistry metrics;

    MetricsReporter(MetricRegistry metrics) {
        this.metrics = metrics;
    }

    @Override
    public String getName() {
        return getClass().getCanonicalName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
        Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        metrics.timer(triggerMetricName(trigger.getName())).update(
            context.getJobRunTime(), TimeUnit.MILLISECONDS
        );

        metrics.timer(jobMetricName(context.getJobDetail().getName())).update(
            context.getJobRunTime(), TimeUnit.MILLISECONDS
        );

    }

    private static String triggerMetricName(String trigger) {
        return String.format("%s.trigger.%s", MetricsReporter.class.getCanonicalName(), trigger);
    }

    private static String jobMetricName(String trigger) {
        return String.format("%s.job.%s", MetricsReporter.class.getCanonicalName(), trigger);
    }
}
