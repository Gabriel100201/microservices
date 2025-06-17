package com.ucc.orders.service;

import com.ucc.orders.exceptions.orders.InsufficientStockException;
import com.ucc.orders.exceptions.orders.OrderNotFoundException;
import com.ucc.orders.exceptions.orders.OrderValidationException;
import com.ucc.orders.exceptions.orders.ProductNotFoundException;
import com.ucc.orders.model.dto.OrderDTO;
import com.ucc.orders.model.dto.OrderResponseDTO;
import com.ucc.orders.model.dto.OrderStatusUpdateDTO;
import com.ucc.orders.model.entities.Order;
import com.ucc.orders.model.entities.OrderItem;
import com.ucc.orders.model.enums.OrderStatus;
import com.ucc.orders.model.mappers.OrderMapper;
import com.ucc.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
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
        try {
            // Llamar al servicio de productos para verificar stock
            Integer availableStock = restTemplate.getForObject(
                "http://localhost:8080/api/products/{id}/stock",
                Integer.class,
                productId
            );

            if (availableStock == null) {
                throw new InsufficientStockException(
                    String.format("No se pudo verificar el stock para el producto %d", productId)
                );
            }

            if (availableStock < quantity) {
                throw new InsufficientStockException(
                    String.format("Stock insuficiente para el producto %d. Disponible: %d, Solicitado: %d",
                        productId, availableStock, quantity)
                );
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductNotFoundException("No se encontró el producto con el ID: " + productId);
        }
    }

    private Double getProductPrice(Long productId) {
        try {
            // Llamar al servicio de productos para obtener el precio
            return restTemplate.getForObject(
                "http://localhost:8080/api/products/{id}/price",
                Double.class,
                productId
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductNotFoundException("No se encontró el producto con el ID: " + productId);
        }
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatusUpdateDTO statusUpdate) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("No se encontró la orden con el ID: " + orderId));

        if (order.getStatus() == statusUpdate.getStatus()) {
            throw new OrderValidationException("La orden ya está en el estado: " + statusUpdate.getStatus());
        }

        // Validar transición de estado
        validateStatusTransition(order.getStatus(), statusUpdate.getStatus());

        // Si se está confirmando la orden, actualizar el stock
        if (statusUpdate.getStatus() == OrderStatus.CONFIRMED) {
            updateProductStock(order, false);
        }
        // Si se está cancelando la orden y estaba confirmada, restaurar el stock
        else if (statusUpdate.getStatus() == OrderStatus.CANCELLED && order.getStatus() == OrderStatus.CONFIRMED) {
            updateProductStock(order, true);
        }

        order.setStatus(statusUpdate.getStatus());
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.orderToOrderResponseDTO(updatedOrder);
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == OrderStatus.CANCELLED || currentStatus == OrderStatus.COMPLETED) {
            throw new OrderValidationException("No se puede cambiar el estado de una orden " + currentStatus);
        }

        if (currentStatus == OrderStatus.PENDING && newStatus == OrderStatus.COMPLETED) {
            throw new OrderValidationException("Una orden PENDING no puede pasar directamente a COMPLETED");
        }

        if (currentStatus == OrderStatus.CONFIRMED && newStatus == OrderStatus.PENDING) {
            throw new OrderValidationException("Una orden CONFIRMED no puede volver a PENDING");
        }
    }

    private void updateProductStock(Order order, boolean isRestoring) {
        for (OrderItem item : order.getOrderItems()) {
            try {
                // Llamar al servicio de productos para actualizar el stock
                // Si isRestoring es true, enviamos la cantidad como negativa para sumar al stock
                Integer quantity = isRestoring ? -item.getQuantity() : item.getQuantity();
                restTemplate.put(
                    "http://localhost:8080/api/products/{id}/stock",
                    quantity,
                    item.getProductId()
                );
            } catch (HttpClientErrorException e) {
                throw new OrderValidationException(
                    "Error al actualizar el stock del producto " + item.getProductId() + ": " + e.getMessage()
                );
            }
        }
    }
} 