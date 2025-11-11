package com.example.billing.listener;

import com.example.kafkaes.avro.OrderCreatedAvro;
import com.example.shared.model.ProcessedEvent;
import com.example.shared.repo.ProcessedEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BillingListener {
    private final ProcessedEventRepository repo;
    public BillingListener(ProcessedEventRepository repo){ this.repo = repo; }

    @KafkaListener(topics = "orders", groupId = "billing_group")
    @Transactional
    public void onOrder(OrderCreatedAvro event){
        String id = event.getOrderId().toString();
        if(repo.existsByEventId(id)){ System.out.println("[Billing] already processed " + id); return; }
        System.out.println("[Billing] charging for order " + id + " amount=" + event.getAmount());
        repo.save(new ProcessedEvent(id));
    }
}
