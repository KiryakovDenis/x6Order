package ru.kdv.study.x6Order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.kdv.study.x6Order.Exception.BadRequestException;
import ru.kdv.study.x6Order.model.Order;
import ru.kdv.study.x6Order.model.OrderProduct;
import ru.kdv.study.x6Order.model.ProductExist;
import ru.kdv.study.x6Order.repository.OrderProductRepository;
import ru.kdv.study.x6Order.repository.OrderRepository;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderProductRepository orderProductRepository;

    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(final Order order) {
        validate(order);

        Order resultOrder = orderRepository.insert(order);

        order.getOrderPositionList().forEach(orderProduct -> orderProduct.setOrderId(resultOrder.getId()));

        orderProductRepository.insertAll(order.getOrderPositionList());

        return resultOrder;
    }

    private void validate(final Order order) {
        if (!StringUtils.hasText(order.getOrderNumber())) {
            throw BadRequestException.create("Номер заказа должен быть заполнен");
        }

        if (order.getOrderDate() == null) {
            throw BadRequestException.create("Дата заказа должна быть заполнена");
        }

        if (order.getUserId() == null) {
            throw BadRequestException.create("Id пользователя должен быть заполнен");
        }

        if (order.getOrderPositionList() == null) {
            throw BadRequestException.create("Список товаров должен быть заполнен");
        }

        if (order.getOrderPositionList().isEmpty()) {
            throw BadRequestException.create("Список товаров должен быть заполнен");
        }

        if (Boolean.FALSE.equals(userService.existUser(order.getUserId()))) {
            throw BadRequestException.create("Неверно указан пользователь");
        }

        checkExistProductId(order.getOrderPositionList());
    }

    private void checkExistProductId(final List<OrderProduct> orderPositionList) {
        List<Long> list = orderPositionList.stream()
                .map(OrderProduct::getProductId).toList();

        String message =
                productService.productExist(list
                        ).stream()
                        .filter(productExist -> !productExist.getIsExist())
                        .map(ProductExist::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));

        if (!message.isEmpty()) {
            throw BadRequestException.create(new StringJoiner("\n")
                    .add("Товары с указанными идентификаторами - не существуют: ")
                    .add(message)
                    .toString());
        }
    }
}