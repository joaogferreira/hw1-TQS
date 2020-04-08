package com.joao.tqshw1;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;



import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.print.attribute.standard.Media;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.jupiter.api.Assertions.*;

public class AirQualityControllerTest {
    private MockMvc mockMvc;

    @Autowired
    AirQualityController controller = new AirQualityController();

    @Before
    public void setup() throws Exception { this.mockMvc = standaloneSetup(this.controller).build(); }

    @Test
    public void testAir() throws Exception {
        //api/air/london
        /** mockMvc.perform(get("/api/air/london").content(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$",hasSize(3)))
                //.andExpect(jsonPath("$.*.local",hasItem("London"))); */
    }

    @Test
    public void testStations() throws Exception {
         mockMvc.perform(get("http://localhost:8080/api/stations").content(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

         //Falta ver isto
            //Ver content Lenght
        //Ver se continua tudo a correr direito
        //Ver media type , se dá para retornar td o conteudo 
    }
}