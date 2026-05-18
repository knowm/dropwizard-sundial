package org.knowm.dropwizard.sundial;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.knowm.dropwizard.sundial.tasks.StartJobTask;

class StartJobTaskTest {

  @Test
  void shouldStartNamedTask() throws Exception {
    List<Object[]> captured = new ArrayList<>();
    StartJobTask task = new StartJobTask((name, params) -> captured.add(new Object[] {name, params}));
    Map<String, List<String>> map = new HashMap<>();
    map.put("JOB_NAME", Arrays.asList("test"));

    task.execute(map, new PrintWriter(new ByteArrayOutputStream()));

    assertThat(captured).hasSize(1);
    assertThat(captured.get(0)[0]).isEqualTo("test");
    assertThat(captured.get(0)[1]).isEqualTo(Map.of("JOB_NAME", "test"));
  }

  @Test
  void shouldPassParameters() throws Exception {
    List<Object[]> captured = new ArrayList<>();
    StartJobTask task = new StartJobTask((name, params) -> captured.add(new Object[] {name, params}));
    Map<String, List<String>> map = new HashMap<>();
    map.put("JOB_NAME", Arrays.asList("test"));
    map.put("Param1", Arrays.asList("1"));
    map.put("Param2", Arrays.asList("2"));

    task.execute(map, new PrintWriter(new ByteArrayOutputStream()));

    assertThat(captured).hasSize(1);
    assertThat(captured.get(0)[0]).isEqualTo("test");
    assertThat(captured.get(0)[1])
        .isEqualTo(Map.of("JOB_NAME", "test", "Param1", "1", "Param2", "2"));
  }

  @Test
  void shouldGiveErrorMessageIfNoJobNameSpecified() {
    StartJobTask task = new StartJobTask((name, params) -> {});
    Map<String, List<String>> map = new HashMap<>();

    assertThatThrownBy(() -> task.execute(map, new PrintWriter(new ByteArrayOutputStream())))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("JOB_NAME");
  }
}

