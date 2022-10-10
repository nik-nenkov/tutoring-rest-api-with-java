package com.epam.training.dao;

import com.epam.training.model.Delivery;
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

  private static final String SELECT_DELIVERY_BY_ID = "SELECT * FROM `delivery` WHERE `id` = :id";

  private static final String DELIVERY_TABLE = "delivery";
  private static final String ID_COLUMN = "id";
  private static final String STOCK_ID_COLUMN = "stock_id";
  private static final String QUANTITY_COLUMN = "quantity";
  private static final String FIRST_DATE_COLUMN = "first_date";
  private static final String SCHEDULED_COLUMN = "scheduled";
  private static final String TIME_INTERVAL_COLUMN = "time_interval";

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private final SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public DeliveryRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
    setDataSource(dataSource);
    this.jdbcTemplate = jdbcTemplate;
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
    simpleJdbcInsert.includeSynonymsForTableColumnMetaData();
    simpleJdbcInsert.withTableName(DELIVERY_TABLE);
    simpleJdbcInsert
        .usingColumns(
            STOCK_ID_COLUMN,
            QUANTITY_COLUMN,
            FIRST_DATE_COLUMN,
            SCHEDULED_COLUMN,
            TIME_INTERVAL_COLUMN)
        .usingGeneratedKeyColumns("id");
  }

  private Map<String, Object> deliveryMapper(Delivery delivery) {
    Map<String, Object> params = new HashMap<>();
    params.put(STOCK_ID_COLUMN, delivery.getStockId());
    params.put(QUANTITY_COLUMN, delivery.getQuantity());
    params.put(FIRST_DATE_COLUMN, delivery.getDate());
    params.put(SCHEDULED_COLUMN, delivery.isScheduled());
    params.put(TIME_INTERVAL_COLUMN, delivery.getTimeInterval());
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
    params.put(STOCK_ID_COLUMN, deliveryToPersist.getStockId());
    params.put(QUANTITY_COLUMN, deliveryToPersist.getQuantity());
    params.put(FIRST_DATE_COLUMN, deliveryToPersist.getDate());
    KeyHolder key = simpleJdbcInsert.executeAndReturnKeyHolder(params);
    return Objects.requireNonNull(key.getKey()).intValue();
  }

  public Delivery getDeliveryById(int id) {
    Map<String, Object> param = new HashMap<>();
    param.put(ID_COLUMN, id);
    return jdbcTemplate.queryForObject(SELECT_DELIVERY_BY_ID, param, new DeliveryRowMapper());
  }

  private static class DeliveryRowMapper implements RowMapper<Delivery> {

    @Override
    public Delivery mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Delivery(
          rs.getInt(ID_COLUMN),
          rs.getInt(STOCK_ID_COLUMN),
          rs.getInt(QUANTITY_COLUMN),
          rs.getTimestamp(FIRST_DATE_COLUMN),
          rs.getBoolean(SCHEDULED_COLUMN),
          rs.getLong(TIME_INTERVAL_COLUMN));
    }
  }
}
