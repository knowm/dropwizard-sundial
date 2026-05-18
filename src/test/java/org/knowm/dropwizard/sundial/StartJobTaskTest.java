package org.knowm.dropwizard.sundial;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.knowm.dropwizard.sundial.tasks.StartJobTask;
import org.knowm.sundial.SundialJobScheduler;
import org.mockito.MockedStatic;

class StartJobTaskTest {

  @Test
  void shouldStartNamedTask() throws Exception {
    StartJobTask task = new StartJobTask();
    Map<String, List<String>> map = new HashMap<>();
    map.put("JOB_NAME", Arrays.asList("test"));
    OutputStream stream = new ByteArrayOutputStream();
    PrintWriter out = new PrintWriter(stream);

    try (MockedStatic<SundialJobScheduler> mockedStatic = mockStatic(SundialJobScheduler.class)) {
      task.execute(map, out);
      mockedStatic.verify(
          () ->
              SundialJobScheduler.startJob(
                  "test", Collections.singletonMap("JOB_NAME", (Object) "test")));
    }
  }

  @Test
  void shouldPassParameters() throws Exception {
    StartJobTask task = new StartJobTask();
    Map<String, List<String>> map = new HashMap<>();
    map.put("JOB_NAME", Arrays.asList("test"));
    map.put("Param1", Arrays.asList("1"));
    map.put("Param2", Arrays.asList("2"));
    OutputStream stream = new ByteArrayOutputStream();
    PrintWriter out = new PrintWriter(stream);

    Map<String, Object> expectedParams = new HashMap<>();
    expectedParams.put("JOB_NAME", "test");
    expectedParams.put("Param1", "1");
    expectedParams.put("Param2", "2");

    try (MockedStatic<SundialJobScheduler> mockedStatic = mockStatic(SundialJobScheduler.class)) {
      task.execute(map, out);
      mockedStatic.verify(() -> SundialJobScheduler.startJob("test", expectedParams));
    }
  }

  @Test
  void shouldGiveErrorMessageIfNoJobNameSpecified() throws Exception {
    StartJobTask task = new StartJobTask();
    Map<String, List<String>> map = new HashMap<>();
    OutputStream stream = new ByteArrayOutputStream();
    PrintWriter out = new PrintWriter(stream);

    assertThatThrownBy(() -> task.execute(map, out))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("JOB_NAME");
  }
}
