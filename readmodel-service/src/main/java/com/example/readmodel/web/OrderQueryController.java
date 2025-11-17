package com.example.readmodel.web;

import com.example.readmodel.model.OrderProjection;
import com.example.readmodel.repo.OrderProjectionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderQueryController {

    private final OrderProjectionRepository repo;

    public OrderQueryController(OrderProjectionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<OrderProjection> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public OrderProjection get(@PathVariable String id) {

        return repo.findById(id).orElse(null);
    }
}
