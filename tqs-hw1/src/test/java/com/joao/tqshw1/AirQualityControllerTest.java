package com.joao.tqshw1;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AirQualityControllerTest {
    ArrayList<String> cities_available = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AirQualityController controller;

    @BeforeEach
    public void setup() throws Exception {
        //this.mockMvc = standaloneSetup(this.controller).build();
    }

    @Test
    public void testAir() throws Exception {
        //Add all the cities available
        cities_available.add("shanghai");cities_available.add("paris");cities_available.add("london");
        cities_available.add("lisbon");cities_available.add("berlin");cities_available.add("tokyo");
        cities_available.add("munchen");cities_available.add("denver");cities_available.add("helsinki");
        cities_available.add("stockholm");cities_available.add("moscow");cities_available.add("madrid");

        for (int i=0;i<cities_available.size();i++){
            mockMvc.perform(MockMvcRequestBuilders.get("/api/air/"+cities_available.get(i)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("ok"));
        }
    }

    @Test
    public void testStations() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/stations"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(result.contains("Shanghai"));
        Assertions.assertTrue(result.contains("Paris"));
        Assertions.assertTrue(result.contains("London"));
        Assertions.assertTrue(result.contains("Lisbon"));
        Assertions.assertTrue(result.contains("Berlin"));
        Assertions.assertTrue(result.contains("Tokyo"));
        Assertions.assertTrue(result.contains("Munchen"));
        Assertions.assertTrue(result.contains("Denver"));
        Assertions.assertTrue(result.contains("Helsinki"));
        Assertions.assertTrue(result.contains("Stockholm"));
        Assertions.assertTrue(result.contains("Moscow"));
        Assertions.assertTrue(result.contains("Madrid"));
    }

    @Test
    public void testStats() throws Exception{
        
    }
}
