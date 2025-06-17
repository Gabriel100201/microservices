package com.ucc.orders.model.mappers;

import com.ucc.orders.model.dto.OrderItemDTO;
import com.ucc.orders.model.dto.OrderItemResponseDTO;
import com.ucc.orders.model.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    
    public OrderItem orderItemDTOToOrderItem(OrderItemDTO dto) {
        if (dto == null) {
            return null;
        }
        
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(dto.getProductId());
        orderItem.setQuantity(dto.getQuantity());
        return orderItem;
    }
    
    public OrderItemResponseDTO orderItemToOrderItemResponseDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        
        return new OrderItemResponseDTO(
            orderItem.getProductId(),
            orderItem.getQuantity(),
            orderItem.getUnitPrice(),
            orderItem.getSubtotal()
        );
    }
} 