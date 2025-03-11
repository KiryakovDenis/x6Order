package ru.kdv.study.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.kdv.study.model.Order;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(rs.getLong("id"))
                .orderNumber(rs.getString("order_number"))
                .orderDate(rs.getObject("order_date", LocalDate.class))
                .userId(rs.getLong("user_id"))
                .createDate(rs.getObject("create_date", LocalDateTime.class))
                .build();
    }
}
