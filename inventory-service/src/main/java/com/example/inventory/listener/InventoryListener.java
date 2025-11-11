package com.example.inventory.listener;

import com.example.kafkaes.avro.OrderCreatedAvro;
import com.example.shared.model.ProcessedEvent;
import com.example.shared.repo.ProcessedEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InventoryListener {
    private final ProcessedEventRepository repo;
    public InventoryListener(ProcessedEventRepository repo){ this.repo = repo; }

    @KafkaListener(topics = "orders", groupId = "inventory_group")
    @Transactional
    public void onOrder(OrderCreatedAvro event){
        String id = event.getOrderId().toString();
        if(repo.existsByEventId(id)){ System.out.println("[Inventory] already processed " + id); return; }
        System.out.println("[Inventory] reserving for order " + id + " amount=" + event.getAmount());
        repo.save(new ProcessedEvent(id));
    }
}
