package jdbc.crud.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

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
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `stock` (\n" +
                "\t`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "\t`stock_id` INT UNSIGNED NOT NULL,\n" +
                "\t`price` DECIMAL(14,2) UNSIGNED NULL DEFAULT NULL,\n" +
                "\t`quantity` INT NOT NULL DEFAULT '0',\n" +
                "\tPRIMARY KEY (`id`),\n" +
                "\tUNIQUE INDEX `stock_id` (`stock_id`)\n" +
                ")\n" +
                "COLLATE='utf8_bin'\n" +
                ";\n");
    }
}
