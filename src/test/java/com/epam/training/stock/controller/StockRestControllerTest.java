package com.epam.training.stock.controller;

import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@WebMvcTest(StockRestController.class)
public class StockRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockRepository stockRepository;

    private JacksonTester<Stock> jsonStock;

    @BeforeAll
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void newStock() throws Exception {
        //given
        given(stockRepository.getStockById(1992))
                .willReturn(new Stock(
                        1992,
                        BigDecimal.valueOf(4.14),
                        35));
        //when
        MockHttpServletResponse response = mockMvc.perform(
                get("")
        ).andReturn().getResponse();
    }
}