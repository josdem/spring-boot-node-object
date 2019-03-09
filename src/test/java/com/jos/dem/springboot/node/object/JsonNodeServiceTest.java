package com.jos.dem.springboot.node.object;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.jos.dem.springboot.node.object.model.Event;
import com.jos.dem.springboot.node.object.model.Message;
import com.jos.dem.springboot.node.object.service.JsonNodeReaderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JsonNodeServiceTest {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private JsonNodeReaderService service;

  private Event event;

  @BeforeEach
  void init() throws Exception {
    log.info("Getting Event from ClockIn json file");
    File jsonFile = new File("src/main/resources/ClockIn.json");
    event = service.read(jsonFile);
  }

  @Test
  @DisplayName("Validate Event values from ClockIn Json file")
  void shouldGetEventFromClockInFile() throws Exception {
    log.info("Running: Validate Event values from ClockIn Json file at " + new Date());

    assertAll("event",
      () -> assertEquals("fbe07c89-ffa7-4c86-9832-5f75cf765737", event.getBatchId(), "Should get batch id"),
      () -> assertEquals("EmployeeClockIn", event.getEventType(), "Should get event type"),
      () -> assertEquals(OffsetDateTime.parse("2019-03-09T07:36:43-05:00"), event.getPublishedAt(), "Should get published at time")
    );

  }

  @Test
  @DisplayName("Validate Message values from Event")
  void shouldGetMessageFromEvent() throws Exception {
    log.info("Running: Validate Message values from event at " + new Date());

    Message[] messages = event.getMessages();
    Message message = messages[0];

    assertEquals("1", messages.length, "Should contain one message");

    assertAll("message",
      () -> assertEquals("4aeaa175-e46d-42eb-83d3-cd02865d4863", message.getMessageId(), "Should get message id")
    );

  }

  @AfterEach
  void finish() throws Exception {
    log.info("Test execution finished");
  }

}
