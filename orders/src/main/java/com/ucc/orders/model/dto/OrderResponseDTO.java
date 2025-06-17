package com.ucc.orders.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO implements Serializable {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
    private List<OrderItemResponseDTO> items;
} 