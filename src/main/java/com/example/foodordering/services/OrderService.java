package com.example.foodordering.services;

import com.example.foodordering.dtos.OrderItemDTO;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.entities.Order;
import com.example.foodordering.entities.OrderDetail;
import com.example.foodordering.entities.Table;
import com.example.foodordering.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final TableService tableService;
    private final OrderRepository orderRepository;
    private final MenuItemService menuItemService;

    @Transactional
    public void createOrder(int tableId, List<OrderItemDTO> orderItems) {
        Table table = tableService.getTableById(tableId);

        if (table.getStatus().equals("OCCUPIED")) {
            throw new IllegalStateException("Table is already occupied");
        }
        // Create the order
        Order order = Order.builder()
                .table(table)
                .createdTime(java.time.Instant.now())
                .build();

        // Create order details and set the relationship with the order
        Set<OrderDetail> orderDetails = orderItems.stream()
                .map(orderItem -> {
                    MenuItem menuItem = menuItemService.getMenuItemById(orderItem.getItemId());
                    return OrderDetail.builder()
                            .item(menuItem)
                            .quantity(orderItem.getQuantity())
                            .order(order)
                            .build();
                })
                .collect(Collectors.toSet());

        // Set order details to the order
        order.setOrderDetails(orderDetails);

        // Save the order
        orderRepository.save(order);

        // Update table status
        tableService.updateTableStatus(tableId, false);
    }
}
