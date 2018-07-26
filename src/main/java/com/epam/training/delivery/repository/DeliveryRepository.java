package com.epam.training.delivery.repository;


import com.epam.training.delivery.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class DeliveryRepository extends NamedParameterJdbcDaoSupport {

    private static final String SELECT_DELIVERY_BY_ID =
            "SELECT * FROM `delivery` WHERE `id` = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public DeliveryRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        setDataSource(dataSource);
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
        simpleJdbcInsert.includeSynonymsForTableColumnMetaData();
        simpleJdbcInsert.withTableName("delivery");
        simpleJdbcInsert.usingColumns(
                "stock_id",
                "quantity",
                "first_date",
                "scheduled",
                "time_interval"
        ).usingGeneratedKeyColumns("id");
    }

    private Map<String, Object> deliveryMapper(Delivery delivery) {
        Map<String, Object> params = new HashMap<>();
        params.put("stock_id", delivery.getStockId());
        params.put("quantity", delivery.getQuantity());
        params.put("first_date", delivery.getDate());
        params.put("scheduled", delivery.isScheduled());
        params.put("time_interval", delivery.getTimeInterval());
        return params;
    }

    @Transactional
    public Integer createNewScheduledDelivery(Delivery deliveryToPersist) {

        Map<String, Object> params = deliveryMapper(deliveryToPersist);

        KeyHolder key = simpleJdbcInsert.executeAndReturnKeyHolder(params);
        return Objects.requireNonNull(key.getKey()).intValue();
    }

    @Transactional
    public Integer createNewSingleDelivery(Delivery deliveryToPersist) {
        Map<String, Object> params = new HashMap<>();
        params.put("stock_id", deliveryToPersist.getStockId());
        params.put("quantity", deliveryToPersist.getQuantity());
        params.put("first_date", deliveryToPersist.getDate());
        KeyHolder key = simpleJdbcInsert.executeAndReturnKeyHolder(params);
        return Objects.requireNonNull(key.getKey()).intValue();
    }

    public Delivery getDeliveryById(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return jdbcTemplate.queryForObject(SELECT_DELIVERY_BY_ID, param, new DeliveryRowMapper());
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
