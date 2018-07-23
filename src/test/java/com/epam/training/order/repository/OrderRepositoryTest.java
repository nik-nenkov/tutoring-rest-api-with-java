package com.epam.training.order.repository;

import com.epam.training.BasicRepositoryTest;

public class OrderRepositoryTest extends BasicRepositoryTest {

//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//
//
//    @Test
//    public void prepareTestDatabase() {
//        jdbcTemplate.execute("drop database if exists test_warehouse_db");
//        jdbcTemplate.execute("create database if not exists test_warehouse_db");
//        jdbcTemplate.execute("use `test_warehouse_db`");
//        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0");
//        jdbcTemplate.execute("CREATE TABLE `stock` (" +
//                "`id` int(10) unsigned NOT NULL AUTO_INCREMENT," +
//                "`stock_id` int(10) unsigned NOT NULL," +
//                "`quantity` int(10) unsigned NOT NULL," +
//                "`price` decimal(14,2) unsigned NOT NULL," +
//                "PRIMARY KEY (`id`)," +
//                "UNIQUE KEY `stock_id` (`stock_id`)" +
//                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
//        jdbcTemplate.execute("CREATE TABLE `order` (" +
//                "`order_id` int(10) unsigned NOT NULL AUTO_INCREMENT," +
//                "`stock_id` int(10) unsigned NOT NULL," +
//                "`quantity` int(10) unsigned NOT NULL," +
//                "`price` decimal(14,2) unsigned NOT NULL," +
//                "`order_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
//                "PRIMARY KEY (`order_id`)," +
//                "KEY `FK_orders_stocks` (`stock_id`)," +
//                "CONSTRAINT `FK_orders_stocks` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)" +
//                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
//        jdbcTemplate.execute("CREATE TABLE `revision` (" +
//                "`revision_id` int(10) unsigned NOT NULL AUTO_INCREMENT," +
//                "`total_quantities` int(10) unsigned NOT NULL," +
//                "`total_price` decimal(14,2) unsigned NOT NULL," +
//                "`revision_started` datetime NOT NULL," +
//                "`revision_ended` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP," +
//                "PRIMARY KEY (`revision_id`)" +
//                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;");
//        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1");
//    }
//
//    @Test
//    public void addSomeEntries() {
//        jdbcTemplate.execute("INSERT INTO `test_warehouse_db`.`stock` (`stock_id`, `quantity`, `price`) VALUES ('442', '40', '3.4');");
//        jdbcTemplate.execute("INSERT INTO `test_warehouse_db`.`stock` (`stock_id`, `quantity`, `price`) VALUES ('3232', '50', '7.8');");
//    }

//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Test
//    public void getAnyOrderById() throws ParseException {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
//        Date date = dateFormat.parse("2018-07-19 14:00:00.0");
//        long time = date.getTime();
//
//        Assertions.assertEquals(
//                new Order(8, 442, 20, 10.05F, new Timestamp(time)),
//                orderRepository.getOrderById(8));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void createNewOrder() {
//        orderRepository.createNewOrder(
//                3232,
//                10,
//                BigDecimal.valueOf(85)
//        );
//        Assertions.assertEquals(
//                new Order(9, 3232, 10, 85F, new Timestamp( (new Date().getTime()/1000)*1000      )),
//                orderRepository.getLastOrder());
//    }

}