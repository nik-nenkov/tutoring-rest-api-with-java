package com.epam.training.delivery.repository;


import com.epam.training.delivery.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DeliveryRepository extends NamedParameterJdbcDaoSupport {

    private static final String GET_LAST_ENTERED_DELIVERY =
            "SELECT * FROM `delivery` ORDER BY `id` DESC LIMIT 1";
    private final static String INSERT_SCHEDULED_DELIVERY =
            "INSERT INTO `delivery`(stock_id,quantity,time_interval) VALUES(:stock_id,:quantity,:time_interval)";
    private final static String INSERT_SINGLE_DELIVERY =
            "INSERT INTO `delivery`(stock_id,quantity,first_date) VALUES(:stock_id,:quantity,:first_date)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DeliveryRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setDataSource(dataSource);
    }

    public void createNewScheduledDelivery(Delivery deliveryToPersist) {
        Map<String, Object> params = new HashMap<>();
        params.put("stock_id", deliveryToPersist.getStockId());
        params.put("quantity", deliveryToPersist.getQuantity());
        params.put("time_interval", deliveryToPersist.getTimeInterval());
        jdbcTemplate.update(INSERT_SCHEDULED_DELIVERY, params);
    }

    public void createNewSingleDelivery(Delivery deliveryToPersist) {
        Map<String, Object> params = new HashMap<>();
        params.put("stock_id", deliveryToPersist.getStockId());
        params.put("quantity", deliveryToPersist.getQuantity());
        params.put("first_date", deliveryToPersist.getDate());
        jdbcTemplate.update(INSERT_SINGLE_DELIVERY, params);
    }

    public Delivery lastEnteredDelivery() {
        return jdbcTemplate.queryForObject(GET_LAST_ENTERED_DELIVERY, new HashMap<>(), new DeliveryRowMapper());
    }

    private static class DeliveryRowMapper implements RowMapper<Delivery> {
        @Override
        public Delivery mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Delivery(
                    rs.getInt("id"),
                    rs.getInt("stock_id"),
                    rs.getInt("quantity"),
                    rs.getTimestamp("first_date"),
                    rs.getBoolean("scheduled"),
                    rs.getLong("time_interval"));
        }
    }

}
