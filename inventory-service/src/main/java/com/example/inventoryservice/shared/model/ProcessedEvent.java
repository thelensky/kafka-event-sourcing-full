package com.example.inventoryservice.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "processed_event")
public class ProcessedEvent {
    @Id
    private String eventId;
    public ProcessedEvent() {}
    public ProcessedEvent(String eventId){ this.eventId = eventId; }
    public String getEventId(){ return eventId; }
    public void setEventId(String eventId){ this.eventId = eventId; }
}
