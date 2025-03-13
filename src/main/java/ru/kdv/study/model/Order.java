package ru.kdv.study.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class Order {
    private Long id;
    private String orderNumber;
    private LocalDate orderDate;
    private Long userId;
    private LocalDateTime createDate;
    private List<OrderProduct> orderPositionList;
}