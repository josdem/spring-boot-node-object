package com.jos.dem.springboot.node.object.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
  private String batchId;
  private String eventType;
  private OffsetDateTime publishedAt;
  private Message[] messages;
}
