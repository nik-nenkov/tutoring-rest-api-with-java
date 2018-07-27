package com.epam.training.order.repository;

import com.epam.training.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class OrderRepository extends NamedParameterJdbcDaoSupport {

    private static final String INSERT_ORDER_NEW =
            "INSERT INTO `order`(stock_id, quantity, price) VALUES(:stock_id, :quantity, :price)";

    private static final String SELECT_ORDER_LAST =
            "SELECT * FROM `order` ORDER BY order_id DESC LIMIT 1";

    private static final String SELECT_ORDER_BY_ID =
            "SELECT * FROM `order` WHERE order_id = :order_id";

    private static final String SELECT_ORDERS_ALL_LIMIT_FIFTY =
            "select * from `order` LIMIT 50";

    private static final String SELECT_ORDERS_AFTER_UNIX_TIMESTAMP =
            "select * from `order` where order_timestamp>:starting_time";

    private static final String SELECT_ORDERS_BETWEEN_UNIX_TIMESTAMPS =
            "select * from `order` where order_timestamp BETWEEN :start_time AND :end_time";

    @Autowired
    public OrderRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Transactional
    public Order getOrderById(int id) {
        Map<String, Object> args = new HashMap<>();
        args.put("order_id", id);
        return Objects.requireNonNull(getNamedParameterJdbcTemplate())
                .queryForObject(SELECT_ORDER_BY_ID, args, new OrderRowMapper());
    }

    @Transactional
    public Order getLastOrder() {
        return Objects.requireNonNull(getNamedParameterJdbcTemplate())
                .queryForObject(SELECT_ORDER_LAST, new HashMap<>(), new OrderRowMapper());
    }

    @Transactional
    public List<Order> getLastOrdersLimitFifty() {
        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).query(
                SELECT_ORDERS_ALL_LIMIT_FIFTY,
                new OrderRowMapper()
        );
    }

    @Transactional
    public List<Order> ordersAfterTimestamp(Timestamp startingTime) {

        Map<String, Object> params = new HashMap<>();
        params.put("starting_time", startingTime);

        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).query(
                SELECT_ORDERS_AFTER_UNIX_TIMESTAMP,
                params, new OrderRowMapper());
    }

    @Transactional
    public List<Order> ordersBetweenTwoTimestamps(Timestamp start, Timestamp end) {
        Map<String, Object> params = new HashMap<>();
        params.put("start_time", start.toString());
        params.put("end_time", end.toString());
//
//        System.out.println(start);
//        System.out.println(end);

        //        System.out.println();

        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).query(
                SELECT_ORDERS_BETWEEN_UNIX_TIMESTAMPS,
                params, new OrderRowMapper());
    }

    @Transactional
    public void createNewOrder(int stockId, int quantity, BigDecimal orderPrice) {

        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);
        params.put("quantity", quantity);
        params.put("price", orderPrice);

        Objects.requireNonNull(getNamedParameterJdbcTemplate()).update(INSERT_ORDER_NEW, params);
    }

    private static final class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Order(
                    rs.getInt("order_id"),
                    rs.getInt("stock_id"),
                    rs.getInt("quantity"),
                    rs.getBigDecimal("price"),
                    rs.getTimestamp("order_timestamp"));
        }
    }

}
