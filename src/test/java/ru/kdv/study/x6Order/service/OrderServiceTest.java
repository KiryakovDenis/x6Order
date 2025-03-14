package ru.kdv.study.x6Order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kdv.study.x6Order.Exception.BadRequestException;
import ru.kdv.study.x6Order.model.Order;
import ru.kdv.study.x6Order.model.OrderProduct;
import ru.kdv.study.x6Order.model.ProductExist;
import ru.kdv.study.x6Order.repository.OrderProductRepository;
import ru.kdv.study.x6Order.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private UserService userService;
    @Mock
    private OrderProductRepository orderProductRepository;

    @InjectMocks
    private OrderService orderService;

    List<OrderProduct> validOrderPositionList = Arrays.asList(
            OrderProduct.builder()
                    .orderId(1L)
                    .amountProduct(1L)
                    .productId(2L)
                    .createDate(LocalDateTime.now())
                    .build(),
            OrderProduct.builder()
                    .orderId(1L)
                    .amountProduct(2L)
                    .productId(4L)
                    .createDate(LocalDateTime.now())
                    .build()
    );

    private Order validOrder = Order.builder()
            .id(1L)
            .orderNumber("001")
            .orderDate(LocalDate.now())
            .createDate(LocalDateTime.now())
            .userId(1L)
            .orderPositionList(validOrderPositionList)
            .build();

    @Test
    @DisplayName("Успешное создание заказа")
    public void createOrderSuccess() {
        Mockito.when(orderRepository.insert(validOrder)).thenReturn(validOrder);
        Mockito.when(userService.existUser(validOrder.getUserId())).thenReturn(true);
        Mockito.when(productService.productExist(
                validOrder.getOrderPositionList().stream()
                        .map(OrderProduct::getProductId)
                        .toList())
        ).thenReturn(validOrder.getOrderPositionList().stream()
                .map(orderProduct -> {return new ProductExist(orderProduct.getId(), true);})
                .toList()
        );

        Order result = orderService.createOrder(validOrder);
        assertThat(result).isEqualTo(validOrder);

        verify(orderRepository).insert((validOrder));
        verify(userService).existUser(validOrder.getUserId());
        verify(productService).productExist(
                validOrder.getOrderPositionList().stream()
                        .map(OrderProduct::getProductId)
                        .toList()
        );
    }

    private Order emptyNumberOrder = Order.builder()
            .id(1L)
            .orderNumber("")
            .orderDate(LocalDate.now())
            .createDate(LocalDateTime.now())
            .userId(1L)
            .orderPositionList(validOrderPositionList)
            .build();

    @Test
    @DisplayName("Валидация пустого номера заказа")
    public void validateEmptyNumberOrder_checkBadRequestException() {
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
            orderService.createOrder(emptyNumberOrder);
        });
    }

    private Order emptyOrderDateOrder = Order.builder()
            .id(1L)
            .orderNumber("0001")
            .orderDate(null)
            .createDate(LocalDateTime.now())
            .userId(1L)
            .orderPositionList(validOrderPositionList)
            .build();

    @Test
    @DisplayName("Валидация пустой даты заказа")
    public void validateЕmptyOrderDateOrder_checkBadRequestException() {
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
            orderService.createOrder(emptyOrderDateOrder);
        });
    }

    private Order emptyUserId = Order.builder()
            .id(1L)
            .orderNumber("0001")
            .orderDate(LocalDate.now())
            .createDate(LocalDateTime.now())
            .userId(null)
            .orderPositionList(validOrderPositionList)
            .build();

    @Test
    @DisplayName("Валидация пустого значения userId")
    public void validateEmptyUserId_checkBadRequestException() {
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
            orderService.createOrder(emptyUserId);
        });
    }

    private Order emptyOrderPostionList = Order.builder()
            .id(1L)
            .orderNumber("001")
            .orderDate(LocalDate.now())
            .createDate(LocalDateTime.now())
            .userId(1L)
            .orderPositionList(null)
            .build();

    @Test
    @DisplayName("Валидация пустого списка товаров")
    public void validateEmptyOrderPositionList_checkBadRequestException() {
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
            orderService.createOrder(emptyOrderPostionList);
        });
    }
}