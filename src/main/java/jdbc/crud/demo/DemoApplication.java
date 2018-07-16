package jdbc.crud.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DemoApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {

        jdbcTemplate.execute("DROP TABLE IF EXISTS `order`");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `order` (\n" +
                "  `id` int(10) unsigned NOT NULL,\n" +
                "  `order_id` int(10) unsigned NOT NULL,\n" +
                "  `stock_id` int(10) unsigned NOT NULL,\n" +
                "  `quantity` int(10) unsigned NOT NULL,\n" +
                "  `price` decimal(14,2) unsigned NOT NULL,\n" +
                "  `order_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `order_id` (`order_id`),\n" +
                "  KEY `FK_orders_stocks` (`stock_id`),\n" +
                "  CONSTRAINT `FK_orders_stocks` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

        jdbcTemplate.execute("DROP TABLE IF EXISTS `revision`");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `revision` (\n" +
                "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `revision_id` int(10) unsigned NOT NULL,\n" +
                "  `total_quantities` int(10) unsigned NOT NULL,\n" +
                "  `total_price` decimal(14,2) unsigned NOT NULL,\n" +
                "  `revision_started` datetime NOT NULL,\n" +
                "  `revision_ended` datetime NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `revision_id` (`revision_id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

        jdbcTemplate.execute("DROP TABLE IF EXISTS `stock`");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `stock` (\n" +
                "  `id` int(10) unsigned NOT NULL,\n" +
                "  `stock_id` int(10) unsigned NOT NULL,\n" +
                "  `quantity` int(10) unsigned NOT NULL,\n" +
                "  `price` decimal(14,2) unsigned NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `stock_id` (`stock_id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

//
//        jdbcTemplate.execute("insert into new_table(id, name) values(3,'Nik')");
//        jdbcTemplate.execute("insert into new_table(id, name) values(5,'Doe')");
//        jdbcTemplate.execute("insert into new_table(id, name) values(2,'Joe')");
//        jdbcTemplate.execute("insert into new_table(id, name) values(8,'Rik')");
//
//
//        String query = "select * from new_table where name=?";
//        Object[] parameter = new Object[]{"Nik"};
//        RowMapper<Customer> rowMapper = (rs, id) -> new Customer(rs.getInt("id"), rs.getString("name"));
//
//        jdbcTemplate.query(query, parameter, rowMapper)
//                .forEach(System.out::println);

    }
}
