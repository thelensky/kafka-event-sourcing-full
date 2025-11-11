package com.example.readmodel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderProjection {
    @Id private String orderId;
    private String customerId;
    private double amount;
    private long createdAt;
    public OrderProjection() {}
    public OrderProjection(String orderId,String customerId,double amount,long createdAt){this.orderId=orderId;this.customerId=customerId;this.amount=amount;this.createdAt=createdAt;}
    public String getOrderId(){return orderId;} public void setOrderId(String o){this.orderId=o;}
    public String getCustomerId(){return customerId;} public void setCustomerId(String c){this.customerId=c;}
    public double getAmount(){return amount;} public void setAmount(double a){this.amount=a;}
    public long getCreatedAt(){return createdAt;} public void setCreatedAt(long t){this.createdAt=t;}
}
