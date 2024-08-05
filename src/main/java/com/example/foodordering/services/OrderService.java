package com.example.foodordering.services;

import com.example.foodordering.dtos.OrderItemDTO;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.entities.Order;
import com.example.foodordering.entities.OrderDetail;
import com.example.foodordering.entities.Table;
import com.example.foodordering.exceptions.DataNotFoundException;
import com.example.foodordering.repositories.OrderDetailRepository;
import com.example.foodordering.repositories.OrderRepository;
import com.example.foodordering.repositories.TableRepository;
import com.example.foodordering.response.PaymentResponse;
import com.example.foodordering.response.orders.OrderDetailResponse;
import com.example.foodordering.response.orders.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final ModelMapper modelMapper;
    private final TableRepository tableRepository;
    private final TableService tableService;
    private final OrderRepository orderRepository;
    private final MenuItemService menuItemService;

    @Transactional
    public OrderResponse createOrUpdateOrder(int tableId, List<OrderItemDTO> orderItems) throws Exception {
        Table table = tableService.getTableById(tableId)
                .orElseThrow(() -> new DataNotFoundException("TableID not existed"));

        Order order;

        if (table.getStatus().equals("OCCUPIED")) {
            // Find the existing order for the occupied table
            order = orderRepository.findByTable(table)
                    .orElseThrow(() -> new DataNotFoundException("Order not found for the occupied table"));

            // Update the existing order details
            Set<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderItemDTO orderItemDTO : orderItems) {
                MenuItem menuItem = menuItemService.getMenuItemById(orderItemDTO.getItemId());

                OrderDetail existingOrderDetail = orderDetails.parallelStream()
                        .filter(detail -> detail.getItem().equals(menuItem))
                        .findFirst()
                        .orElse(null);

                if (existingOrderDetail != null) {
                    // Update quantity if the item already exists in the order
                    existingOrderDetail.setQuantity(existingOrderDetail.getQuantity() + orderItemDTO.getQuantity());
                } else {
                    // Add new order detail if the item is not in the order
                    OrderDetail newOrderDetail = OrderDetail.builder()
                            .item(menuItem)
                            .quantity(orderItemDTO.getQuantity())
                            .order(order)
                            .build();
                    orderDetails.add(newOrderDetail);
                }
            }
            order.setOrderDetails(orderDetails);
        } else {
            // Create a new order
            order = Order.builder()
                    .table(table)
                    .createdTime(Instant.now())
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

            // Update table status
            table.setStatus("OCCUPIED");
        }

        // Save the order
        orderRepository.save(order);
        // Save the table if a new order is created
        if (table.getStatus().equals("OCCUPIED") && order.getOrderDetails().size() == orderItems.size()) {
            tableRepository.save(table);
        }

        // Map the order to OrderResponse
        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails()
                .stream()
                .map(orderDetail -> {
                    OrderDetailResponse response = modelMapper.map(orderDetail, OrderDetailResponse.class);
                    response.setPrice(orderDetail.getItem().getPrice());
                    return response;
                })
                .collect(Collectors.toList());

        OrderResponse orderResponse = OrderResponse.builder()
                .orderDetails(orderDetailResponses)
                .tableId(tableId)
                .totalMoney(getTotalMoneyByOrder(order))
                .build();

        return orderResponse;
    }


    @Transactional
    public PaymentResponse paymentOrder(int tableId) throws DataNotFoundException {
        Order orderToPay = orderRepository.findByTable_id(tableId).orElseThrow(() -> new DataNotFoundException("TableID not existed"));


        Table table = orderToPay.getTable();
        table.setStatus("FREE");

        orderToPay.setTable(null);


        tableRepository.save(table);

        orderRepository.save(orderToPay);

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .orderId(orderToPay.getId())
                .totalMoney(getTotalMoneyByOrder(orderToPay))
                .build();

        return paymentResponse;

    }

    @Transactional
    public PaymentResponse getPaymentInfo(int tableId) throws DataNotFoundException {
        Order order = orderRepository.findByTable_id(tableId)
                .orElseThrow(() -> new DataNotFoundException(String.format("TableID %d not existed", tableId)));

        return PaymentResponse.builder()
                .orderId(order.getId())
                .totalMoney(getTotalMoneyByOrder(order))
                .build();
    }

    @Transactional
    public OrderResponse getAllOrderByTableId(int tableId) throws DataNotFoundException {
        Order order = orderRepository.findByTable_id(tableId)
                .orElseThrow(() -> new DataNotFoundException(String.format("TableId %d not have any order", tableId)));


        List<OrderDetailResponse> orderDetailResponse = order.getOrderDetails()
                .stream()
                .map(orderDetail -> {
                    OrderDetailResponse response = modelMapper.map(orderDetail, OrderDetailResponse.class);
                    response.setPrice(orderDetail.getItem().getPrice()); // Set the price
                    return response;
                })
                .toList();

        OrderResponse orderResponse = OrderResponse.builder()
                .orderDetails(orderDetailResponse)
                .tableId(tableId)
                .totalMoney(getTotalMoneyByOrder(order))
                .build();

        return orderResponse;
    }


    @Transactional
    public BigDecimal getTotalMoneyByOrder(Order order) {
        return order.getOrderDetails().stream()
                .map(orderDetail -> BigDecimal.valueOf(orderDetail.getQuantity()).multiply(orderDetail.getItem().getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
