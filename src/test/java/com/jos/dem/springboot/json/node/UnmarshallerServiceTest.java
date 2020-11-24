package com.jos.dem.springboot.json.node;

import com.fasterxml.jackson.databind.JsonNode;
import com.jos.dem.springboot.json.node.model.Event;
import com.jos.dem.springboot.json.node.model.Message;
import com.jos.dem.springboot.json.node.service.UnmarshallerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UnmarshallerServiceTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnmarshallerService service;

    private Event event;
    private Message message;
    private Message[] messages;

    @BeforeEach
    void init() throws Exception {
        log.info("Getting Event from ClockIn json file");
        File jsonFile = new File("src/main/resources/ClockIn.json");
        event = service.read(jsonFile);
        Message[] messages = event.getMessages();
        message = messages[0];
    }

    @Test
    @DisplayName("Validate Event values from ClockIn Json file")
    void shouldGetEventFromClockInFile() throws Exception {
        log.info("Running: Validate Event values from ClockIn Json file at {}", new Date());

        assertAll("event",
                () -> assertEquals("fbe07c89-ffa7-4c86-9832-5f75cf765737", event.getBatchId(), "Should get batch id"),
                () -> assertEquals("EmployeeClockIn", event.getEventType(), "Should get event type"),
                () -> assertEquals(OffsetDateTime.parse("2019-03-09T12:36:43Z"), event.getPublishedAt(), "Should get published at time")
        );

    }

    @Test
    @DisplayName("Validate Message values from Event")
    void shouldGetMessageFromEvent() throws Exception {
        log.info("Running: Validate Message values from event at {}", new Date());

        assertAll("message",
                () -> assertEquals("4aeaa175-e46d-42eb-83d3-cd02865d4863", message.getMessageId(), "Should get message id"),
                () -> assertEquals(OffsetDateTime.parse("2019-03-09T12:36:43Z"), event.getPublishedAt(), "Should get published at time")
        );

    }

    @Test
    @DisplayName("Validate Data values from message")
    void shouldGetDataFromMessage() throws Exception {
        log.info("Running: Validate Data values from message at {}", new Date());

        JsonNode data = message.getData();
        assertAll("data",
                () -> assertEquals("Jose", data.get("firstname").textValue(), "Should get Firstname"),
                () -> assertEquals("Morales", data.get("lastname").textValue(), "Should get Lastname"),
                () -> assertEquals("josdem", data.get("nickname").textValue(), "Should get Nickname"),
                () -> assertEquals(1196, data.get("employeeNumber").intValue(), "Should get Employee Number"),
                () -> assertEquals("joseluis.delacruz@gmail.com", data.get("email").textValue(), "Should get Email"),
                () -> assertEquals("2019-03-09T07:36:43-05:00", data.get("clockInDateTime").textValue(), "Should get clockIn time")
        );

    }

    @AfterEach
    void finish() throws Exception {
        log.info("Test execution finished");
    }

}
