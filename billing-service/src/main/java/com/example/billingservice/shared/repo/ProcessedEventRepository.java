package com.example.billingservice.shared.repo;

import com.example.billingservice.shared.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent,String>{
    boolean existsByEventId(String eventId);
}
