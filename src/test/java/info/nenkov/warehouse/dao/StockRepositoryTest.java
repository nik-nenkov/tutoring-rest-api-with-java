package info.nenkov.warehouse.dao;

import info.nenkov.warehouse.DemoApplication;
import info.nenkov.warehouse.model.Stock;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = DemoApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class StockRepositoryTest {

  private final List<Stock> stocks = new ArrayList<>();
  @Autowired private StockRepository stockRepository;

  @Before
  public void createStocksForTesting() {
    stockRepository.insertNewStock(43, BigDecimal.valueOf(14.30), 156);
    stocks.add(new Stock(43, BigDecimal.valueOf(14.30), 156));
    stocks.add(new Stock(22, BigDecimal.valueOf(15.60), 133));
    stocks.add(new Stock(43, BigDecimal.valueOf(14.3), 956));
    stocks.add(new Stock(650650650, BigDecimal.valueOf(0), 0));
  }

  @Test
  public void getStockById() {
    Stock s1 = stockRepository.getStockByStockId(43);
    assertThat(s1).isEqualTo(stocks.get(0));
  }

  @Test
  public void insertNewStock() {
    stockRepository.insertNewStock(22, BigDecimal.valueOf(15.6), 133);
    assertThat(stockRepository.getLastInsertedStock()).isEqualTo(stocks.get(1));
  }

  @Test
  public void updateQuantityById() {
    stockRepository.updateQuantityById(43, 956);
    Stock s2 = stockRepository.getStockByStockId(43);
    assertThat(s2).isEqualTo(stocks.get(2));
  }

  @Test
  public void createStockIfNotExists() {
    Assertions.assertThat(stockRepository.getStockByStockId(650650650)).isNull();
    stockRepository.createStockIfNotExists(650650650);
    Assertions.assertThat(stockRepository.getStockByStockId(650650650)).isEqualTo(stocks.get(3));
  }

  @Test
  public void getStockById_invalidId_returnNull() {
    assertThat(stockRepository.getStockByStockId(434343434)).isNull();
  }

  @Test
  public void createStock_whereAlreadyExists() {
    Assertions.assertThat(stockRepository.getStockByStockId(650650650)).isNull();
    stockRepository.createStockIfNotExists(650650650);
    Assertions.assertThat(stockRepository.getStockByStockId(650650650)).isEqualTo(stocks.get(3));
  }

  @Test
  public void getStock_nonExisting_returnNull() {
    assertThat(stockRepository.getStockById(23121399)).isNull();
  }
}
