package jdbc.crud.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final JdbcTemplate jdbcTemplate;

//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

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
            value = "/add_stock",
            method = RequestMethod.PUT,
            produces = "application/json")
    public Stock increaseStock(
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
        //Търсим в базата стока с посоченият ID номер:
        Stock currentStock = jdbcTemplate
                .queryForObject("select * from stock where stock_id=" + stockId,
                        /*RowMapper*/
                        (rs, rowNum) -> new Stock(
                                rs.getInt("stock_id"),
                                rs.getFloat("price"),
                                rs.getInt("quantity"))
                );
        //В случай че не е намерена такава стока приключваме с подходящо съобщение:
        if (currentStock == null) throw new InvalidParameterException() {
            @Override
            public String getMessage() {
                return "Could not find s stock with id=" + stockId;
            }
        };
        //Правим изчисления са цена на поръчката и промяна на количеството в склада:
        float orderPrice = currentStock.getPrice() * quantity;
        int newQuantity = currentStock.getQuantity() - quantity;
        //Променяме количеството на стоката или извеждаме съобщение за грешка ако то е отрицателно:
        if (newQuantity >= 0) {
            currentStock.setQuantity(newQuantity);
        } else {
            throw new InvalidParameterException() {
                @Override
                public String getMessage() {
                    return "Requested quantity should not exceed total stock quantity!";
                }
            };
        }
        //След проверки и изчисления, можем да създадем новата поръчка:
        Order currentOrder = new Order(stockId, quantity, orderPrice);
        //Съхраняваме поръчката в базата данни:
        jdbcTemplate.update("INSERT INTO `order`(stock_id, quantity, price) VALUES(?,?,?)",
                currentOrder.getStockId(),
                currentOrder.getQuantity(),
                currentOrder.getPrice());
        //Променяме количеството на стоката в базата данни:
        jdbcTemplate.update("UPDATE `stock` SET quantity=? WHERE stock_id=?", newQuantity, stockId);
        //Ако искаме да изведем цялата информация за новата поръчка правим последно търсене в базата
        //където обекта е полъчил уникален ID номер и TIMESTAMP на въвеждането си:
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

    @RequestMapping("/revise")
    public Revision reviseLastThirtyMinutes() {
        java.sql.Timestamp startingTime = new java.sql.Timestamp(System.currentTimeMillis() - 1800000);

        Float sumOfPrice = 0F;
        Integer sumQuantity = 0;

        List<Order> ordersFromLastThirtyMinutes = jdbcTemplate.query(
                "select * from `order` where order_timestamp>unix_timestamp(?)",
                new Object[]{startingTime},
                (rs, rowNum) -> new Order(
                        rs.getInt("stock_id"),
                        rs.getInt("quantity"),
                        rs.getFloat("price")));


        for (Order order : ordersFromLastThirtyMinutes) {
            sumQuantity += order.getQuantity();
            sumOfPrice += order.getPrice();
        }

        jdbcTemplate.update(
                "INSERT into revision(total_quantities, total_price, revision_started) " +
                        "values(?,?,?)", sumQuantity, sumOfPrice, startingTime);

        return jdbcTemplate.queryForObject("select * from `revision` order by revision_id desc limit 1",
                (rs, rowNum) -> new Revision(
                        rs.getInt("revision_id"),
                        rs.getInt("total_quantities"),
                        rs.getFloat("total_price"),
                        rs.getTimestamp("revision_started"),
                        rs.getTimestamp("revision_ended")
                ));
    }

    @Scheduled(fixedRate = 60000)   //  1 minute == 60 000 milliseconds
    private void addSomeStocks() {
        //Някъде ще трябва да задаваме кои стоки и с колко да бъдат увеличавани периодично
        increaseStock(111, 20);
        log.info("Stock with id=111 was increased by quantity=50");
        increaseStock(345, 80);
        log.info("Stock with id=345 was increased by quantity=80");
    }

    @Scheduled(fixedRate = 1800000) // 30 minutes == 1 800 000 milliseconds
    private void makeComputationAndLogResult() {
        Revision currentRevision = reviseLastThirtyMinutes();
        log.info("Revision:\n id=" + currentRevision.getRevisionId() +
                ", quantity=" + currentRevision.getTotalQuantities() +
                ", price=" + currentRevision.getTotalPrice());
    }
}
