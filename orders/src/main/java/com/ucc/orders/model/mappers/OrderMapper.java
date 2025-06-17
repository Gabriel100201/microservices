package com.ucc.orders.model.mappers;

import com.ucc.orders.model.dto.OrderDTO;
import com.ucc.orders.model.dto.OrderResponseDTO;
import com.ucc.orders.model.entities.Order;
import com.ucc.orders.model.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public Order orderDTOToOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        Order order = new Order();
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setStatus("PENDING");
        
        // Crear los items y establecer la referencia bidireccional
        order.setOrderItems(
            orderDTO.getItems().stream()
                .map(dto -> {
                    OrderItem item = orderItemMapper.orderItemDTOToOrderItem(dto);
                    item.setOrder(order); // Establecer la referencia al order
                    return item;
                })
                .collect(Collectors.toList())
        );
        
        return order;
    }

    public OrderResponseDTO orderToOrderResponseDTO(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderResponseDTO(
            order.getId(),
            order.getOrderDate(),
            order.getStatus(),
            order.getTotalAmount(),
            order.getOrderItems().stream()
                .map(orderItemMapper::orderItemToOrderItemResponseDTO)
                .collect(Collectors.toList())
        );
    }
} 