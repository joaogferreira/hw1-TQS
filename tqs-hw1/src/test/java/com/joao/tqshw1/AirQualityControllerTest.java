package com.joao.tqshw1;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;


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

        //ArrayList com as cidades
        //Percorrer as cidades e fazer o assert em baixo
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
    public void testStats() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/stats"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String[] split = result.split("<br>");
        String hits = split[0];
        String miss = split[1];

        String[] split2 = hits.split(" ");
        Assertions.assertTrue(isNumeric(split2[1]));
        Assertions.assertTrue(!split2[0].isEmpty());
        Assertions.assertTrue(!split2[0].equals(""));
        Assertions.assertTrue(!split2[0].equals(null));

        String[] split3 = miss.split(" ");
        Assertions.assertTrue(isNumeric(split3[1]));
        Assertions.assertTrue(!split3[0].isEmpty());
        Assertions.assertTrue(!split3[0].equals(""));
        Assertions.assertTrue(!split3[0].equals(null));
    }

    @Test
    public void testSpecificStation(){
        //FAlta fazer este
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
