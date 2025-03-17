package ru.kdv.study.x6Order.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kdv.study.x6Order.Exception.DataBaseException;
import ru.kdv.study.x6Order.model.OrderProduct;

import java.util.List;
import java.util.StringJoiner;

@Repository
@AllArgsConstructor
public class OrderProductRepository {

    private static final String INSERT = """
            INSERT INTO x6order.order_product (order_id, product_id, amount_product)
            VALUES(:order_id, :product_id, :amount_product)
            RETURNING *
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void insertAll(final List<OrderProduct> orderProductList) {
        try {
            jdbcTemplate.batchUpdate(
                    INSERT,
                    orderProductList.stream()
                            .map(this::orderProductToSql)
                            .toArray(MapSqlParameterSource[]::new));

        } catch (Exception e) {
            throw handleExceptionOrderProduct(e);
        }
    }

    private MapSqlParameterSource orderProductToSql(final OrderProduct orderProduct) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", orderProduct.getId());
        params.addValue("order_id", orderProduct.getOrderId());
        params.addValue("product_id", orderProduct.getProductId());
        params.addValue("amount_product", orderProduct.getAmountProduct());

        return params;
    }

    private RuntimeException handleExceptionOrderProduct(Exception e) {
        return DataBaseException.create(new StringJoiner("\n")
                .add(e.getMessage())
                .add(e.getCause().getMessage())
                .toString()
        );
    }
}