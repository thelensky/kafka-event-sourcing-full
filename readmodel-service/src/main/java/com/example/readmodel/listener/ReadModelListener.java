package com.example.readmodel.listener;

import com.example.kafkaes.avro.OrderCreatedAvro;
import com.example.readmodel.model.OrderProjection;
import com.example.readmodel.repo.OrderProjectionRepository;
import com.example.shared.model.ProcessedEvent;
import com.example.shared.repo.ProcessedEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReadModelListener {
    private final OrderProjectionRepository repo;
    private final ProcessedEventRepository processedRepo;

    public ReadModelListener(OrderProjectionRepository repo, ProcessedEventRepository processedRepo) {
        this.repo = repo;
        this.processedRepo = processedRepo;
    }

    @KafkaListener(topics = "orders", groupId = "readmodel_group")
    @Transactional
    public void onOrder(OrderCreatedAvro event) {
        String id = event.getOrderId().toString();
        if (processedRepo.existsByEventId(id)) {
            System.out.println("[ReadModel] already processed " + id);
            return;
        }
        OrderProjection p = new OrderProjection(id, event.getCustomerId().toString(), event.getAmount(), event.getCreatedAt().toEpochMilli());
        repo.save(p);
        processedRepo.save(new ProcessedEvent(id));
        System.out.println("[ReadModel] indexed " + id);
    }
}
