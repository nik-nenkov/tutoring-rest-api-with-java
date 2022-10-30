package info.nenkov.warehouse.dao;

import info.nenkov.warehouse.DemoApplication;
import info.nenkov.warehouse.RepositoryIntegrationTest;
import info.nenkov.warehouse.model.Delivery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = {DemoApplication.class, DeliveryRepository.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class DeliveryRepositoryIntegrationTest implements RepositoryIntegrationTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private JdbcTemplate jdbcTemplate;

  @Before
  public void startWithNewEmptyDatabase() {
    clearTestDatabase(jdbcTemplate);
  }

  @Test
  @Transactional
  @Rollback
  public void createNewScheduledDelivery() {
    Delivery testDelivery = new Delivery(1, 345, 800, 18500L);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("stock_id", "345");
    map.add("quantity", "800");
    map.add("scheduled", "true");
    map.add("time_interval", "18500");
    map.add("delivery_time", "2018-11-25 11:45:00");

    assertThat(
            this.restTemplate.postForObject(
                "http://localhost:" + port + "/delivery/new", map, Delivery.class))
        .isEqualTo(testDelivery);
  }

  @Test
  @Transactional
  @Rollback
  public void createNewSingleDelivery() throws ParseException {
    Timestamp testingTime =
        new Timestamp(
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2020-12-21 13:45:21").getTime());
    Delivery testDelivery = new Delivery(1, 111, 50, testingTime);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("stock_id", "111");
    map.add("quantity", "50");
    map.add("scheduled", "false");
    map.add("time_interval", "0");
    map.add("delivery_time", "2020-12-21 13:45:21");

    assertThat(
            this.restTemplate.postForObject(
                "http://localhost:" + port + "/delivery/new", map, Delivery.class))
        .isEqualTo(testDelivery);
  }
}
