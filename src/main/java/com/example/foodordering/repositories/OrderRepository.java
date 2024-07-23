package com.example.foodordering.repositories;

import com.example.foodordering.entities.Order;
import com.example.foodordering.entities.Table;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @EntityGraph(value = "orderWithDetailsAndItems")
    Optional<Order> findByTable_id(int tableId);


    Optional<Order> findByTable(Table table);
}
