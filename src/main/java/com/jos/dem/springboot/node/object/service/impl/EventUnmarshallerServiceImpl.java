package com.jos.dem.springboot.node.object.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.stereotype.Service;

import com.jos.dem.springboot.node.object.model.Event;
import com.jos.dem.springboot.node.object.service.JsonNodeReaderService;

@Service
public class EventUnmarshallerServiceImpl implements JsonNodeReaderService {

  private ObjectMapper mapper = new ObjectMapper();

  public Event read(File jsonFile) throws IOException {
    InputStream inputStream = new FileInputStream(jsonFile);
    JsonNode jsonNode = mapper.readTree(inputStream);
    Event event = mapper.treeToValue(jsonNode, Event.class);
    return event;
  }

}