package com.epam.training.order.repository;

import com.epam.training.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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

@ComponentScan("com.epam.training.*")
public class OrderRepository extends NamedParameterJdbcDaoSupport {

    private static final String INSERT_ORDER_NEW = "INSERT INTO `order`(stock_id, quantity, price) VALUES(:stock_id, :quantity, :price)";
    private static final String SELECT_LAST_ORDER = "SELECT * FROM `order` ORDER BY order_id DESC LIMIT 1";
    private static final String SELECT_ORDERS_AFTER_UNIX_TIMESTAMP = "select * from `order` where order_timestamp>unix_timestamp(:starting_time)";


    @Autowired
    public OrderRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Transactional
    public Order getLastOrder() {
        return Objects.requireNonNull(getNamedParameterJdbcTemplate())
                .queryForObject(SELECT_LAST_ORDER, new HashMap<>(), new OrderRowMapper());
    }

    @Transactional
    public List<Order> ordersAfterTimestamp(Timestamp startingTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("starting_time", startingTime);

        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).queryForList(
                SELECT_ORDERS_AFTER_UNIX_TIMESTAMP,
                params, Order.class);
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
                    rs.getFloat("price"),
                    rs.getTimestamp("order_timestamp"));
        }
    }

}
