package com.joao.tqshw1;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
public class AirQualityControllerTest {
    ArrayList<String> cities_available = new ArrayList<>();

    private MockMvc mockMvc;

    @Autowired
    AirQualityController controller = new AirQualityController();

    @Before
    public void setup() throws Exception { this.mockMvc = standaloneSetup(this.controller).build(); }

    @Test
    public void testAir() throws Exception {
        //Add all the cities available
        cities_available.add("shanghai");cities_available.add("paris");cities_available.add("london");
        cities_available.add("lisbon");cities_available.add("berlin");cities_available.add("tokyo");
        cities_available.add("munchen");cities_available.add("denver");cities_available.add("helsinki");
        cities_available.add("stockholm");cities_available.add("moscow");cities_available.add("madrid");


        for (int i=0;i<cities_available.size();i++){
            //System.out.println("Testing for "+cities_available.get(i));
            mockMvc.perform(MockMvcRequestBuilders.get("/api/air/"+cities_available.get(i)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("ok"));
        }
    }

    @Test
    public void testStations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.0").value("{\"city\":\"Shanghai\",\"id\":0}"));

        //Falta ver isto
        //Ver content Lenght
        //Ver se continua tudo a correr direito
        //Ver media type , se dá para retornar td o conteudo
    }
}