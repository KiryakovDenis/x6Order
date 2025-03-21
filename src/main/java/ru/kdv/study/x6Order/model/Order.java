package ru.kdv.study.x6Order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Builder
@Getter
public class Order {
    private Long id;
    private String orderNumber;
    private LocalDate orderDate;
    private Long userId;
    private LocalDateTime createDate;
    @Setter
    private List<OrderProduct> orderPositionList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(orderNumber, order.orderNumber) && Objects.equals(orderDate, order.orderDate) && Objects.equals(userId, order.userId) && Objects.equals(createDate, order.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, orderDate, userId, createDate);
    }
}