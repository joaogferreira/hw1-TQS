package com.joao.tqshw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AirQualityControllerTest {
    /**
     * Testes de Integração - Tecnologia utilizada: MockMvc
     */

    ArrayList<String> cities_available = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AirQualityController controller;

    @BeforeEach
    public void setup() throws Exception {
        //this.mockMvc = standaloneSetup(this.controller).build();
    }

    /**
     * testGetAirQuality - Testa todas as cidades disponíveis e o valor de AirQuality associado
     * Espera um Status "ok" para cada station
     * URL : localhost:8080/api/air/{city}
     */
    @Test
    public void testGetAirQuality() throws Exception {
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

    /**
     * testGetStations - Verifica se todas as stations estão registadas
     */
    @Test
    public void testGetStations() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/stations"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //ArrayList com as cidades
        //Percorrer as cidades e fazer o assert em baixo
        for(int i=0;i<cities_available.size();i++){
            assertThat(result.contains(cities_available.get(i))).isEqualTo(true);
        }
    }

    /**
     * testGetStats - Retorna todas as estatisticas e verifica se o valor associado é numério (isNumeric)
     * e se a string associada ("hit" e "miss") não está vazia e que não corresponde a null
     */
    @Test
    public void testGetStats() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/stats"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String[] split = result.split("<br>");
        String hits = split[0];
        String miss = split[1];

        String[] split2 = hits.split(" ");
        assertThat(isNumeric(split2[1])).isEqualTo(true); //true
        assertThat(!split2[0].isEmpty()).isEqualTo(true); //true
        assertThat(!split2[0].equals("")).isEqualTo(true); //true
        assertThat(!split2[0].equals(null)).isEqualTo(true); //true

        String[] split3 = miss.split(" ");
        assertThat(isNumeric(split3[1])).isEqualTo(true);
        assertThat(!split3[0].isEmpty()).isEqualTo(true);
        assertThat(!split3[0].equals("")).isEqualTo(true);
        assertThat(!split3[0].equals(null)).isEqualTo(true);
    }

    /**
     * testGetSpecificStation - verifica se existe uma station para as cidades disponíveis
     */
    @Test
    public void testGetSpecificStation() throws Exception {
        String result,city;
        String[] split;

        cities_available.add("shanghai");cities_available.add("paris");cities_available.add("london");
        cities_available.add("lisbon");cities_available.add("berlin");cities_available.add("tokyo");
        cities_available.add("munchen");cities_available.add("denver");cities_available.add("helsinki");
        cities_available.add("stockholm");cities_available.add("moscow");cities_available.add("madrid");
        for (int i=0;i<cities_available.size();i++){

            result = mockMvc.perform(MockMvcRequestBuilders.get("/api/station/"+cities_available.get(i)))
                    .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

            split = result.split("<br>");
            city = split[1].split(" ")[1].trim().toLowerCase();

            assertThat(cities_available.contains(city)).isEqualTo(true);
        }
    }

    /**
     * testSpecificStatinonWrongCity - Verifica que não é encontrada nenhuma station para uma cidade não definida
     * @throws Exception
     */
    @Test
    public void testSpecificStationWrongCity() throws Exception{
        String result,city;
        String[] split;

        ArrayList<String> wrong_cities = new ArrayList<>();
        wrong_cities.add("Gondomar");wrong_cities.add("Gaia");wrong_cities.add("Matosinhos");

        for (int i=0;i<wrong_cities.size();i++){
            String res = mockMvc.perform(MockMvcRequestBuilders.get("/api/station/"+wrong_cities.get(i)))
                    .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
            assertThat(res.equals("Station not found.")).isEqualTo(true);
        }
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
