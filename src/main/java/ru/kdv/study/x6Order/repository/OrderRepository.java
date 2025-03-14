package ru.kdv.study.x6Order.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kdv.study.x6Order.model.Order;
import ru.kdv.study.x6Order.repository.mapper.OrderMapper;

@Repository
@AllArgsConstructor
public class OrderRepository {

    private static String INSERT = """
            INSERT
              INTO x6order."order" (order_number, order_date, user_id) 
            VALUES (:order_number, :order_date, :user_id)
            RETURNING *
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderMapper orderMapper;

    public Order insert(final Order order) {
        return jdbcTemplate.queryForObject(INSERT, orderToSql(order), orderMapper);
    }

    private MapSqlParameterSource orderToSql(final Order order) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", order.getId());
        params.addValue("order_number", order.getOrderNumber());
        params.addValue("order_date", order.getOrderDate());
        params.addValue("user_id", order.getUserId());

        return params;
    }
}