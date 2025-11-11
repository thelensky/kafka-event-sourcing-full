package com.example.shared.repo;

import com.example.shared.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent,String>{
    boolean existsByEventId(String eventId);
}
