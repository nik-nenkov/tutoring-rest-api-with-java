package com.epam.training.stock.repository;

import com.epam.training.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository

@ComponentScan("com.epam.training.*")
public class StockRepository extends NamedParameterJdbcDaoSupport {

    private static final String SELECT_STOCK_BY_ID = "SELECT * FROM stock WHERE stock_id=:stock_id";
    private static final String UPDATE_STOCK_QUANTITY__BY_ID = "UPDATE stock SET quantity=:quantity WHERE stock_id=:stock_id";
    private final DataSource dataSource;

    @Transactional
    public Stock getStockById(final int stockId) throws NullPointerException {

        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);

        return Objects.requireNonNull(getNamedParameterJdbcTemplate())
                .queryForObject(SELECT_STOCK_BY_ID, params, new StockRowMapper());
    }

    @Autowired
    public StockRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Transactional
    public void updateQuantityById(int stockId, int quantity) throws NullPointerException {
        final Map<String, Object> params = new HashMap<>();
        params.put("stock_id", stockId);
        params.put("quantity", quantity);
        Objects.requireNonNull(getNamedParameterJdbcTemplate()).update(UPDATE_STOCK_QUANTITY__BY_ID, params);
    }

    private static class StockRowMapper implements RowMapper<Stock> {
        @Override
        public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Stock(
                    rs.getInt("stock_id"),
                    rs.getFloat("price"),
                    rs.getInt("quantity"));
        }
    }

}
