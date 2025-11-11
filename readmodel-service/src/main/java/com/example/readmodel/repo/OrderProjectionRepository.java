package com.example.readmodel.repo;

import com.example.readmodel.model.OrderProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProjectionRepository extends JpaRepository<OrderProjection,String> {}
