package jdbc.crud.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
public class MainController {

    private final
    JdbcTemplate jdbcTemplate;
    private final
    DataSource dataSource;

    @Autowired
    public MainController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @RequestMapping("/home")
    public String homeMessage(){
        return "test123";
    }

    @RequestMapping(
            value = "/new_stock",
            method = RequestMethod.POST,
            produces = "application/json")
    public Stock newStock(
            @RequestParam("stock_id") int stockId,
            @RequestParam("price") float price,
            @RequestParam("quantity") int quantity) throws SQLException {
        Connection connection = dataSource.getConnection();
        Stock stockToAdd = new Stock(stockId,price,quantity);
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO stock(stock_id, price, quantity) VALUES (?,?,?)");
        statement.setInt(1, stockToAdd.getSockId());
        statement.setFloat(2,stockToAdd.getPrice());
        statement.setInt(3,stockToAdd.getQuantity());
        statement.execute();
        return stockToAdd;
    }

    @RequestMapping(
            value = "/change_stock_quantity",
            method = RequestMethod.POST,
            produces = "application/json")
    public Stock changeStock(
            @RequestParam("stock_id") int stockId,
            @RequestParam("quantity") int quantity) throws InvalidParameterException {

        Stock stockToUpdate = jdbcTemplate.queryForObject("SELECT * FROM stock WHERE stock_id="+stockId+" LIMIT 1", (rs, rowNum) -> new Stock(
                rs.getInt("stock_id"),
                rs.getFloat("price"),
                rs.getInt("quantity"))
        );
        assert stockToUpdate != null;
        int newQuantity = stockToUpdate.getQuantity()+quantity;
        if(newQuantity>=0){
            stockToUpdate.setQuantity(newQuantity);
            jdbcTemplate.update("UPDATE stock " +
                    "SET quantity="+stockToUpdate.getQuantity()+" " +
                    "WHERE stock_id="+stockToUpdate.getSockId());
            return stockToUpdate;
        }
        throw new InvalidParameterException(){
            @Override
            public String getMessage() {
                return "Stock quantity can not be negative!";
            }
        };
    }


}
