package com.epam.training.controller;

import com.epam.training.dao.StockRepository;
import com.epam.training.model.Stock;
import com.epam.training.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StockRestControllerMockTest {

  private final JacksonTester<Stock> jsonStock = null;
  private MockMvc mvc;
  @Mock private StockRepository stockRepository;
  @InjectMocks private StockService stockService;
  @InjectMocks private StockRestController stockRestController;

  @Before
  public void setup() {
    stockRestController = new StockRestController(stockService);
    JacksonTester.initFields(this, new ObjectMapper());
    mvc = MockMvcBuilders.standaloneSetup(stockRestController).build();
  }

  @Test
  public void canShowExistingStock() throws Exception {
    Stock testStock2 = new Stock(33, BigDecimal.valueOf(5.15), 20);
    // given
    given(stockRepository.getStockByStockId(33)).willReturn(testStock2);
    // when
    MockHttpServletResponse response =
        mvc.perform(get("/stock/show/33").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(jsonStock.write(testStock2).getJson());
  }

  @Test
  public void canCreateNewStock() throws Exception {
    Stock testStock3 = new Stock(45, BigDecimal.valueOf(45.55), 55);
    // given
    given(stockRepository.getStockByStockId(45)).willReturn(null);
    given(stockRepository.insertNewStock(45, BigDecimal.valueOf(45.55), 55)).willReturn(1);
    given(stockRepository.getStockById(1)).willReturn(testStock3);
    // when
    MockHttpServletResponse response =
        mvc.perform(
                post("/stock/new")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonStock.write(testStock3).getJson())
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(jsonStock.write(testStock3).getJson());
  }

  @Test
  public void canCreateNewStock_existingId_throwException() throws Exception {
    Stock testStock3 = new Stock(45, BigDecimal.valueOf(45.55), 55);
    // given
    given(stockRepository.getStockByStockId(45)).willReturn(testStock3);
    // when
    mvc.perform(post("/stock/new")).andExpect(status().is4xxClientError());
  }

  @Test
  public void canAddQuantityToExistingStock() throws Exception {
    Stock testStock4 = new Stock(45, BigDecimal.valueOf(45.55), 65);

    Stock testStock5 = new Stock(45, BigDecimal.valueOf(45.55), 75);
    // given
    given(stockRepository.getStockByStockId(45)).willReturn(testStock4);
    given(stockRepository.updateQuantityById(45, 75)).willReturn(1);
    given(stockRepository.getStockById(1)).willReturn(testStock5);
    // when
    MockHttpServletResponse response =
        mvc.perform(
                put("/stock/increase?quantity=10&stock_id=45").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(jsonStock.write(testStock5).getJson());
  }
}
