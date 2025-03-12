package com.libra.bookshoporder.controllers;

import com.libra.bookshopmodel.dto.order.OrderCreateRequest;
import com.libra.bookshopmodel.dto.order.OrderDto;
import com.libra.bookshopmodel.dto.order.OrderUpdateRequest;
import com.libra.bookshoporder.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.findAllOrders();
    }

    // Получить заказ по ID
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    // Создать новый заказ
    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    // Обновить существующий заказ
    @PutMapping("/{id}")
    public OrderDto updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderUpdateRequest request) {
        return orderService.updateOrder(id, request);
    }

    // Удалить заказ
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
