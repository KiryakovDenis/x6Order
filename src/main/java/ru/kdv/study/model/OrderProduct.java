package ru.kdv.study.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class OrderProduct {
    private Long id;
    private Long productId;
    private Long amountProduct;
    private LocalDateTime createDate;
    private Long orderId;
}
