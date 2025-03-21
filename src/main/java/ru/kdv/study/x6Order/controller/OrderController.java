package ru.kdv.study.x6Order.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kdv.study.x6Order.model.Order;
import ru.kdv.study.x6Order.service.OrderService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Метод создания заказа")
    public void createOrder(@RequestBody final Order order) {
        orderService.createOrder(order);
    }
}