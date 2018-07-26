package com.epam.training.delivery.controller;

import com.epam.training.delivery.Delivery;
import com.epam.training.delivery.repository.DeliveryRepository;
import com.epam.training.delivery.service.DeliveryService;
import com.epam.training.stock.repository.StockRepository;
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryRestControllerMockTest {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private MockMvc mvc;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryRestController deliveryRestController;

    private JacksonTester<Delivery> jsonDelivery = null;

    @Before
    public void setup() {
        deliveryService = new DeliveryService(deliveryRepository, stockRepository);
        deliveryRestController = new DeliveryRestController(deliveryService);
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders
                .standaloneSetup(deliveryRestController)
                .build();
    }

    @Test
    public void addNewDeliveryScheduled() throws Exception {
        Delivery testDelivery1 = new Delivery(22, 345, 200,
                new Timestamp(sdf.parse("2018-10-03 17:45:00").getTime()), true, 5L);
        //given
        given(deliveryRepository.getDeliveryById(22)).willReturn(testDelivery1);
        given(deliveryRepository.createNewScheduledDelivery(new Delivery(345, 250, 5))).willReturn(22);
        //when
        MockHttpServletResponse response = mvc.perform(
                post("/delivery/new" +
                        "?delivery_time=2018-10-03 17:45:00" +
                        "&quantity=250" +
                        "&scheduled=true" +
                        "&stock_id=345" +
                        "&time_interval=5").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonDelivery.write(testDelivery1).getJson());
    }

    @Test
    public void addNewDeliverySingleTime() throws Exception {
        final Timestamp ts = new Timestamp(sdf.parse("2018-11-13 07:25:30").getTime());
        final Delivery testDelivery2 = new Delivery(23, 111, 10, ts, false, 0L);
        //given
        given(deliveryRepository.getDeliveryById(23)).willReturn(testDelivery2);
        given(deliveryRepository.createNewSingleDelivery(new Delivery(111, 10, ts))).willReturn(23);
        //when
        MockHttpServletResponse response = mvc.perform(
                post("/delivery/new" +
                        "?delivery_time=2018-11-13 07:25:30" +
                        "&quantity=10" +
                        "&scheduled=false" +
                        "&stock_id=111" +
                        "&time_interval=0").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonDelivery.write(testDelivery2).getJson());
    }
}