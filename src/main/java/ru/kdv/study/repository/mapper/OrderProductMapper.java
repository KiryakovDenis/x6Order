package ru.kdv.study.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.kdv.study.model.OrderProduct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OrderProductMapper implements RowMapper<OrderProduct> {

    @Override
    public OrderProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OrderProduct.builder()
                .id(rs.getLong("id"))
                .productId(rs.getLong("product_id"))
                .amountProduct(rs.getLong("amount_product"))
                .createDate(rs.getObject("create_date", LocalDateTime.class))
                .orderId(rs.getLong("order_id"))
                .build();
    }
}
