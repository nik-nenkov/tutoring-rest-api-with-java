package com.epam.training;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class StockRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void homeMessage() throws Exception {
        mockMvc
                .perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test123")));

    }

    @Test
    public void reviseLastThirtyMinutes() throws Exception {
        mockMvc
                .perform(post("/revise"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2018")));

    }


}