package com.joao.tqshw1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class StationServiceTest {
    /**
     * Service Level Tests - Tecnologia utilizada: Mockito
     * Testa as interações com a cache feitas pelo StationService
     */

    @Mock (lenient = true)
    private Cache cache;

    @InjectMocks
    private StationService stationService;

    /**
     * setup - Adiciona uma cidade com ID 1 à cache
     * Define as interações com o mockito
     * @throws Exception
     */
    @BeforeEach
    public void setup() throws Exception {
        Station st = new Station(1, "Barcelona");
        Mockito.when(cache.getStationByID(st.getID())).thenReturn(st);
        Mockito.when(cache.getStationByID(4200)).thenReturn(null);//Wrong ID
    }

    /**
     * whenGetStationByID_thenReturnStation
     * Verifica se o objecto devolvido corresponde ao objecto que foi guardado no setup()
     */
    @Test
    public void whenGetStationByID_thenReturnStation(){
        int id = 1;
        String city = "Barcelona";
        Station found = cache.getStationByID(id);
        assertThat(found.getID()).isEqualTo(id);
        assertThat(found.getCity()).isEqualTo("Barcelona");
    }

    /**
     * whenGetStationByWrongID_thenReturnNull
     * Verifica se, no caso de ser requisitada uma station com um ID incorrecto, o objecto retornado corresponde a null
     */
    @Test
    public void whenGetStationByWrongID_thenReturnNull(){
        int id = 4200;
        Station not_found = cache.getStationByID(id);
        assertThat(not_found).isNull();
    }

    /**
     * whenValidStation_thenDetailsAreCorrect
     * Verifica se, no caso de devolver uma station, os seus atributos estão correctos
     */
    @Test
    public void whenValidStation_thenDetailsAreCorrect() {
        int id = 1;
        String city = "Barcelona";

        assertThat(cache.getStationByID(id).getID()).isEqualTo(id);
        assertThat(cache.getStationByID(id).getCity()).isEqualTo(city);
    }

}