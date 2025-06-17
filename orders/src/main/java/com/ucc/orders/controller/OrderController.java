package com.ucc.orders.controller;

import com.ucc.orders.model.dto.OrderDTO;
import com.ucc.orders.model.dto.OrderResponseDTO;
import com.ucc.orders.model.dto.OrderStatusUpdateDTO;
import com.ucc.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Órdenes", description = "API para la gestión de órdenes y pedidos")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Obtener todas las órdenes", description = "Retorna una lista de todas las órdenes disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "No se encontraron órdenes")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Obtener orden por ID", description = "Retorna una orden específica basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden encontrada"),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO getOrderById(@Parameter(description = "ID de la orden") @PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Crear nueva orden", description = "Crea una nueva orden en el sistema. Verifica el stock disponible de los productos antes de crear la orden.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de la orden inválidos"),
        @ApiResponse(responseCode = "400", description = "Stock insuficiente para uno o más productos"),
        @ApiResponse(responseCode = "404", description = "Uno o más productos no encontrados")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder(@Parameter(description = "Datos de la orden a crear") @RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @Operation(summary = "Actualizar estado de la orden", description = "Actualiza el estado de una orden existente. Al confirmar la orden, se actualiza el stock de los productos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de la orden actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Transición de estado no válida"),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO updateOrderStatus(
            @Parameter(description = "ID de la orden") @PathVariable Long id,
            @Parameter(description = "Nuevo estado de la orden") @RequestBody OrderStatusUpdateDTO statusUpdate) {
        return orderService.updateOrderStatus(id, statusUpdate);
    }
} 