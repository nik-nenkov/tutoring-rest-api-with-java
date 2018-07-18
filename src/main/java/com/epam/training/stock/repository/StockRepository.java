package com.epam.training.stock.repository;

import com.epam.training.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private static final String INSERT_STOCK_NEW = "INSERT INTO stock(stock_id, price, quantity) VALUES (:stock_id, :price, :quantity)";
    private static final String SELECT_STOCK_BY_ID = "SELECT * FROM stock WHERE stock_id=:stock_id";
    private static final String UPDATE_STOCK_QUANTITY_BY_ID = "UPDATE stock SET quantity=:quantity WHERE stock_id=:stock_id";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public void insertNewStock(int stockId, BigDecimal price, int quantity) {
        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);
        params.put("price", price);
        params.put("quantity", quantity);
        jdbcTemplate.update(INSERT_STOCK_NEW, params);
    }

    @Autowired
    public StockRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setDataSource(dataSource);
    }

    @Transactional
    public Stock getStockById(final int stockId) throws NullPointerException {

        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);

        return jdbcTemplate.queryForObject(SELECT_STOCK_BY_ID, params, new StockRowMapper());
    }

    @Transactional
    public void updateQuantityById(int stockId, int quantity) throws NullPointerException {
        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);
        params.put("quantity", quantity);
        jdbcTemplate.update(UPDATE_STOCK_QUANTITY_BY_ID, params);
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
}
