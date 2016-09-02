package org.knowm.dropwizard.sundial;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;

import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.knowm.dropwizard.sundial.tasks.StartJobTask;
import org.knowm.sundial.SundialJobScheduler;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SundialJobScheduler.class)
public class StartJobTaskTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldStartNamedTask() throws Exception {
        StartJobTask task = new StartJobTask();
        ImmutableMultimap<String, String> map = ImmutableMultimap.of("JOB_NAME", "test");
        OutputStream stream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(stream);

        PowerMock.mockStatic(SundialJobScheduler.class);
        SundialJobScheduler.startJob("test", Collections.singletonMap("JOB_NAME", (Object)"test"));
        EasyMock.expectLastCall();

        PowerMock.replay(SundialJobScheduler.class);

        task.execute(map, out);

        PowerMock.verify(SundialJobScheduler.class);
    }

    @Test
    public void shouldPassParameters() throws Exception {
        StartJobTask task = new StartJobTask();
        ImmutableMultimap<String, String> map = ImmutableMultimap.of("JOB_NAME", "test", "Param1", "1", "Param2", "2");
        OutputStream stream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(stream);

        PowerMock.mockStatic(SundialJobScheduler.class);
        SundialJobScheduler.startJob(
            "test", ImmutableMap.of("JOB_NAME", (Object)"test", "Param1", (Object)"1", "Param2", (Object)"2")
        );
        EasyMock.expectLastCall();

        PowerMock.replay(SundialJobScheduler.class);

        task.execute(map, out);

        PowerMock.verify(SundialJobScheduler.class);
    }

    @Test
    public void shouldGiveErrorMessageIfNoJobNameSpecified() throws Exception {
        StartJobTask task = new StartJobTask();
        ImmutableMultimap<String, String> map = ImmutableMultimap.of();
        OutputStream stream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(stream);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("JOB_NAME");
        task.execute(map, out);
    }
}
