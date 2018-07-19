package com.epam.training.stock.controller;

import com.epam.training.stock.Stock;
import com.epam.training.stock.repository.StockRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class StockRestControllerTest {

    private MockMvc mvc;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockRestController stockRestController;

    @MockBean
    private JacksonTester<Stock> jsonStock;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(stockRestController).build();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        Stock testStock = new Stock(1992, BigDecimal.valueOf(4.14), 35);
        //given
        given(stockRepository.getStockById(1992))
                .willReturn(testStock);
        //when
        MockHttpServletResponse response =
                mvc.perform(
                        get("/stock?id=1992")
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();
        //then
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonStock.write(testStock).getJson()
                );
    }

    @Test
    public void canDoSomething() {

    }
}