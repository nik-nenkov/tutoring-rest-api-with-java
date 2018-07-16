package jdbc.crud.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
public class MainController {

    private final
    JdbcTemplate jdbcTemplate;

    @Autowired
    public MainController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping("/home")
    public String homeMessage() {
        return "test123";
    }

    @RequestMapping(
            value = "/new_stock",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Stock newStock(@RequestBody Stock stockToAdd) {
        jdbcTemplate.update("INSERT INTO stock(stock_id, price, quantity) VALUES (?, ?, ?)",
                stockToAdd.getSockId(), stockToAdd.getPrice(), stockToAdd.getQuantity());
        return stockToAdd;
    }

    @RequestMapping(
            value = "/change_stock_quantity",
            method = RequestMethod.PUT,
            produces = "application/json")
    public Stock changeStock(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) throws InvalidParameterException {

        Stock stockToUpdate = jdbcTemplate.queryForObject("SELECT * FROM stock WHERE stock_id=" + stockId + " LIMIT 1",
                (rs, rowNum) -> new Stock(
                        rs.getInt("stock_id"),
                        rs.getFloat("price"),
                        rs.getInt("quantity"))
        );
        assert stockToUpdate != null;
        int newQuantity = stockToUpdate.getQuantity() + quantity;
        if (newQuantity >= 0) {
            stockToUpdate.setQuantity(newQuantity);
            jdbcTemplate.update("UPDATE stock " +
                    "SET quantity=" + stockToUpdate.getQuantity() + " " +
                    "WHERE stock_id=" + stockToUpdate.getSockId());
            return stockToUpdate;
        }
        throw new InvalidParameterException() {
            @Override
            public String getMessage() {
                return "Stock quantity can not be negative!";
            }
        };
    }


    @RequestMapping("/new_order")
    public Order createOrder(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) {
        Float stockPrice = jdbcTemplate.queryForObject("select price from stock where stock_id=" + stockId, Float.class);
        if (stockPrice == null) throw new InvalidParameterException() {
            @Override
            public String getMessage() {
                return "We could not find s stock with id=" + stockId;
            }
        };
        float orderPrice = stockPrice * quantity;
        Order currentOrder = new Order(stockId, quantity, orderPrice);
        jdbcTemplate.update("INSERT INTO `order`(stock_id, quantity, price) VALUES(?,?,?)",
                currentOrder.getStockId(),
                currentOrder.getQuantity(),
                currentOrder.getPrice());
        currentOrder = jdbcTemplate.queryForObject(
                "SELECT * FROM `order` ORDER BY order_id DESC LIMIT 1",
                /*RowMapper*/
                (rs, rowNum) -> new Order(
                        rs.getInt("order_id"),
                        rs.getInt("stock_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"),
                        rs.getTimestamp("order_timestamp")));
        return currentOrder;
    }
}
