package ru.kdv.study.x6Order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@Builder
public class OrderProduct implements Serializable {
    private Long id;
    private Long productId;
    private Long amountProduct;
    private LocalDateTime createDate;
    private Long orderId;

    @Override
    public String toString() {
        return String.format("OrderProduct {id = %d, productId = %d, amountProduct = %d, createDate = %s, orderId = %d}",
                this.id,
                this.productId,
                this.amountProduct,
                this.createDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                this.orderId
        );
    }
}
