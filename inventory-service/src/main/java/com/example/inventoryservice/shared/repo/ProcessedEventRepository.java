package com.example.inventoryservice.shared.repo;

import com.example.inventoryservice.shared.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent,String>{
    boolean existsByEventId(String eventId);
}
