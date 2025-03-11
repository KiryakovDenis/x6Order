package ru.kdv.study.repository;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kdv.study.Exception.DataBaseException;
import ru.kdv.study.Exception.NoDataFoundException;
import ru.kdv.study.model.OrderProduct;
import ru.kdv.study.repository.mapper.OrderProductMapper;

import java.util.List;
import java.util.StringJoiner;

@Repository
@AllArgsConstructor
public class OrderProductRepository {

    private final static String INSERT = """
            INSERT INTO x6order.order_product (order_id, product_id, amount_product)
            VALUES(:order_id, :product_id, :amount_product)
            RETURNING *
            """;
    private final static String GET_PRODUCT_BY_ORDER_ID = """
            SELECT *
              FROM x6order.order_product op
             WHERE op.order_id = :order_id
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderProductMapper orderProductMapper;

    public OrderProduct insert(final OrderProduct orderProduct) {
        try {
            return jdbcTemplate.queryForObject(INSERT, orderProductToSql(orderProduct), orderProductMapper);
        } catch (Exception e) {
            throw handleExceptionOrderProduct(e, orderProduct);
        }
    }

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

    public List<OrderProduct> getOrderProductByOrderId(final Long orderId) {
        try {
            return jdbcTemplate.query(GET_PRODUCT_BY_ORDER_ID, new MapSqlParameterSource("order_id", orderId), orderProductMapper);
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

    private RuntimeException handleExceptionOrderProduct(Exception e, final OrderProduct orderProduct) {
        if (e instanceof EmptyResultDataAccessException) {
            return NoDataFoundException.create(String.format("Позиция заказа не найден [order_id = %d; id = %d]", orderProduct.getOrderId(), orderProduct.getId()));
        } else {
            return DataBaseException.create(new StringJoiner("\n")
                    .add(e.getMessage())
                    .add(e.getCause().getMessage())
                    .toString()
            );
        }
    }

    private RuntimeException handleExceptionOrderProduct(Exception e) {
        return DataBaseException.create(new StringJoiner("\n")
                .add(e.getMessage())
                .add(e.getCause().getMessage())
                .toString()
        );
    }
}
