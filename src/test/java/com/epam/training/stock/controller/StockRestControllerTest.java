package com.epam.training.stock.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(StockRestController.class)
public class StockRestControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void newStock() {
    }

    @Test
    public void increaseStock() {
    }
}