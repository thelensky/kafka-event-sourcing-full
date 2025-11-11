package com.example.orderapp.web;

import com.example.kafkaes.avro.OrderCreatedAvro;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Controller
public class OrderController {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    public OrderController(KafkaTemplate<String,Object> kafkaTemplate){ this.kafkaTemplate = kafkaTemplate; }
    @GetMapping("/") public String index(){ return "index"; }
    @PostMapping("/create") @ResponseBody
    public ResponseEntity<String> create(@RequestParam(defaultValue = "cust-1") String customerId,
                                         @RequestParam(defaultValue = "100.0") double amount){
        String id = UUID.randomUUID().toString();
        OrderCreatedAvro event = OrderCreatedAvro.newBuilder()
                .setOrderId(id)
                .setCustomerId(customerId)
                .setAmount(amount)
                .setCreatedAt(Instant.now().toEpochMilli())
                .build();
        kafkaTemplate.send("orders", id, event);
        return ResponseEntity.ok(id);
    }
}
