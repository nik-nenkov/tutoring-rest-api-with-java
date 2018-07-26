package com.epam.training.stock.repository;

import com.epam.training.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
@ComponentScan("com.epam.training.*")
public class StockRepository extends NamedParameterJdbcDaoSupport {

    private static final String SELECT_STOCK_BY_ID =
            "SELECT * FROM stock WHERE id=:id";
    private static final String SELECT_STOCK_BY_STOCK_ID =
            "SELECT * FROM stock WHERE stock_id=:stock_id";
    private static final String SELECT_LAST_INSERTED_STOCK =
            "SELECT * FROM stock ORDER BY id DESC LIMIT 1";
    private static final String UPDATE_STOCK_QUANTITY_BY_ID =
            "UPDATE stock SET quantity=:quantity WHERE stock_id=:stock_id";
    private final static String GET_ID_FROM_STOCK_ID =
            "SELECT id FROM stock WHERE stock_id=:stock_id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public StockRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        setDataSource(dataSource);
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("stock")
                .usingColumns("stock_id", "price", "quantity")
                .usingGeneratedKeyColumns("id");
    }

    @Transactional
    public Stock getLastInsertedStock() {
        return jdbcTemplate.queryForObject(SELECT_LAST_INSERTED_STOCK, new HashMap<>(), new StockRowMapper());
    }

    @Transactional
    public int insertNewStock(int stockId, BigDecimal price, int quantity) {
        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);
        params.put("price", price);
        params.put("quantity", quantity);
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Transactional
    public void createStockIfNotExists(int stockId) {
        if (getStockByStockId(stockId) == null)
            insertNewStock(stockId, BigDecimal.ZERO, 0);
    }

    private final class StockRowMapper implements RowMapper<Stock> {
        @Override
        public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Stock(
                    rs.getInt("stock_id"),
                    rs.getBigDecimal("price"),
                    rs.getInt("quantity"));
        }
    }

    @Transactional
    public Stock getStockByStockId(final int stockId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);
        try {
            return jdbcTemplate.queryForObject(SELECT_STOCK_BY_STOCK_ID, params, new StockRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Stock getStockById(final int id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return jdbcTemplate.queryForObject(SELECT_STOCK_BY_ID, params, new StockRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Integer updateQuantityById(int stockId, int quantity) throws NullPointerException {
        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);
        params.put("quantity", quantity);
        jdbcTemplate.update(UPDATE_STOCK_QUANTITY_BY_ID, params);
        return jdbcTemplate.queryForObject(GET_ID_FROM_STOCK_ID, params, Integer.class);
    }

}
