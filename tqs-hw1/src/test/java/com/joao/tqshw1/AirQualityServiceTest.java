package com.joao.tqshw1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AirQualityServiceTest {
    /**
     * Service Level Tests - Tecnologia utilizada: Mockito
     * Testas as interações com a cache feitas pelo AirQualityService
     */
    @Mock (lenient = true)
    private Cache cache;

    @InjectMocks
    private AirQualityService airQualityService;

    ArrayList<String> citiesAvailable = new ArrayList<>();
    AirQuality airquality;
    int miss,hit;

    @BeforeEach
    public void setup() throws Exception {

        //cidades para testar - vão funcionar como cidades disponíveis
        String[] cities = {"Las Vegas","Ibiza","Miami","Amsterdam","Bangkok"};
        for (String city : cities)
            citiesAvailable.add(city);

        //stats
        miss = 75;
        hit = 200;

        //Preparação do objecto Info para o AirQuality
        HashMap<String,HashMap<String,Float>> info_example = new HashMap<>();

        HashMap<String,Float> toPutInInfo1 = new HashMap<>();
        HashMap<String,Float> toPutInInfo2 = new HashMap<>();

        toPutInInfo1.put("v", (float) 2.0);
        toPutInInfo2.put("v", (float) 17.4);

        info_example.put("co",toPutInInfo1);
        info_example.put("pm25",toPutInInfo2);

        Info info = new Info(20,info_example);

        //Tempo
        long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

        //Objecto AirQuality
        airquality = new AirQuality("ok",info,timestamp);

        //Mockito
        Mockito.when(cache.getCitiesAvailable()).thenReturn(citiesAvailable);
        Mockito.when(cache.getHitAndMiss()).thenReturn(hit+miss);

        for(int i=0;i<citiesAvailable.size();i++){
            Mockito.when(cache.isValid(citiesAvailable.get(i))).thenReturn(true);
            Mockito.when(cache.getAirQualityByCity(citiesAvailable.get(i))).thenReturn(airquality);
        }

        Mockito.when(cache.getAirQualityByCity("Vigo")).thenReturn(null);
        Mockito.when(cache.isValid("Esgueira")).thenReturn(false);

    }

    /**
     * valid City - Verifica a validade dos dados registados
     */
    @Test
    public void validCity(){
        for(int i=0;i<citiesAvailable.size();i++){
            assertThat(cache.isValid(citiesAvailable.get(i))).isEqualTo(true);
        }
    }

    /**
     * invalidCity - Verifica a não validade de os dados registados para a cidade Esgueira
     */
    @Test
    public void invalidCity(){
        assertThat(cache.isValid("Esgueira")).isEqualTo(false);
    }

    /**
     * whenValidCity_thenAirQualityCorrect - Verifica que, dada uma das cidades disponíveis, os dados retornados
     * são os esperados e correctos
     */
    @Test
    public void whenValidCity_thenAirQualityCorrect(){
        for(int i=0;i<citiesAvailable.size();i++){
            assertThat(cache.getAirQualityByCity(citiesAvailable.get(i))).isEqualTo(airquality);
        }
    }

    /**
     * whenInvalidCity_thenAirQualityNull - Verifica que, dada uma cidade não disponível, os dados retornados
     * correspondem a NULL
     */
    @Test
    public void whenInvalidCity_thenAirQualityNull(){
        assertThat(cache.getAirQualityByCity("Vigo")).isEqualTo(null);
    }

    /**
     * given5cities_whenGetAll_Return5Records
     * Verifica se as cidades "Las Vegas","Ibiza","Miami","Amsterdam","Bangkok" estão todas registadas na cache
     */
    @Test
    public void given5Cities_whenGetAll_Return5Records(){
        for(int i=0;i<citiesAvailable.size();i++){
            assertThat(cache.getCitiesAvailable().contains(citiesAvailable.get(i))).isEqualTo(true);
        }
        assertThat(cache.getCitiesAvailable().size()).isEqualTo(5);
    }

    /**
     * givenStats - verifica se os valores de hit e miss estão correctos, através da soma de ambos
     */
    @Test
    public void givenStats(){
        assertThat(cache.getHitAndMiss()).isEqualTo(hit+miss);
    }
}
