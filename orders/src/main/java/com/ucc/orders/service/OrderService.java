package com.ucc.orders.service;

import com.ucc.orders.exceptions.orders.InsufficientStockException;
import com.ucc.orders.exceptions.orders.OrderNotFoundException;
import com.ucc.orders.exceptions.orders.OrderValidationException;
import com.ucc.orders.model.dto.OrderDTO;
import com.ucc.orders.model.dto.OrderResponseDTO;
import com.ucc.orders.model.entities.Order;
import com.ucc.orders.model.entities.OrderItem;
import com.ucc.orders.model.mappers.OrderMapper;
import com.ucc.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;

    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No hay órdenes disponibles");
        }
        return orders.stream()
                .map(orderMapper::orderToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(Long id) {
        if (id == null || id <= 0) {
            throw new OrderValidationException("El ID de la orden debe ser un número positivo");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("No se encontró la orden con el ID: " + id));
        return orderMapper.orderToOrderResponseDTO(order);
    }

    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        validateOrderDTO(orderDTO);
        
        // Verificar stock y existencia de productos
        for (var item : orderDTO.getItems()) {
            verifyProductStock(item.getProductId(), item.getQuantity());
        }

        Order order = orderMapper.orderDTOToOrder(orderDTO);
        
        // Calcular totales
        double totalAmount = 0.0;
        for (OrderItem item : order.getOrderItems()) {
            // Obtener precio del producto desde el servicio de productos
            Double productPrice = getProductPrice(item.getProductId());
            item.setUnitPrice(productPrice);
            item.setSubtotal(productPrice * item.getQuantity());
            totalAmount += item.getSubtotal();
        }
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.orderToOrderResponseDTO(savedOrder);
    }

    private void validateOrderDTO(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new OrderValidationException("La orden no puede ser nula");
        }
        if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty()) {
            throw new OrderValidationException("La orden debe contener al menos un item");
        }
        for (var item : orderDTO.getItems()) {
            if (item.getProductId() == null || item.getProductId() <= 0) {
                throw new OrderValidationException("El ID del producto debe ser un número positivo");
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new OrderValidationException("La cantidad debe ser un número positivo");
            }
        }
    }

    private void verifyProductStock(Long productId, Integer quantity) {
        // Llamar al servicio de productos para verificar stock
        Integer availableStock = restTemplate.getForObject(
            "http://localhost:8080/api/products/{id}/stock",
            Integer.class,
            productId
        );

        if (availableStock == null || availableStock < quantity) {
            throw new InsufficientStockException(
                String.format("Stock insuficiente para el producto %d. Disponible: %d, Solicitado: %d",
                    productId, availableStock, quantity)
            );
        }
    }

    private Double getProductPrice(Long productId) {
        // Llamar al servicio de productos para obtener el precio
        return restTemplate.getForObject(
            "http://localhost:8080/api/products/{id}/price",
            Double.class,
            productId
        );
    }
} 