package ru.kdv.study.x6Order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@AllArgsConstructor
@Builder
@Getter
public class Order implements Serializable {
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

    @Override
    public String toString() {

        String result = String.format("Order: {id = %d, orderNumber = %s, orderDate = %s, userId = %d, createDate = %s, orderPosition[${OrderPositionList}]}",
                this.id,
                this.orderDate,
                this.orderDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                this.userId,
                createDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        );



        if (this.orderPositionList != null && !this.orderPositionList.isEmpty()) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            orderPositionList.stream()
                    .map(OrderProduct::toString)
                    .toList()
                    .forEach(stringJoiner::add);

            result = result.replace("${OrderPositionList}", stringJoiner.toString());
        } else {
            result = result.replace("${OrderPositionList}", "");
        };
        return result;
    }
}