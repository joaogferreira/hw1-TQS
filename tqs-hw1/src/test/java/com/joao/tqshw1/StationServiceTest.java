package com.joao.tqshw1;

import com.sun.xml.bind.v2.model.core.ID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class StationServiceTest {

    @Mock (lenient = true)
    private Cache cache;

    @InjectMocks
    private StationService stationService;

    @BeforeEach
    public void setup() throws Exception {
        Station st = new Station(1, "Barcelona");
        Mockito.when(cache.getStationByID(st.getID())).thenReturn(st);
        Mockito.when(cache.getStationByID(4200)).thenReturn(null);//Wrong ID
    }

    @Test
    public void whenGetStationByID_thenReturnStation(){
        int id = 1;
        String city = "Barcelona";
        Station found = cache.getStationByID(id);
        assertThat(found.getID()).isEqualTo(id);
        assertThat(found.getCity()).isEqualTo("Barcelona");
    }

    @Test
    public void whenGetStationByWrongID_thenReturnNull(){
        int id = 4200;
        Station not_found = cache.getStationByID(id);
        assertThat(not_found).isNull();
    }

    @Test
    public void whenValidStation_thenDetailsAreCorrect() {
        int id = 1;
        String city = "Barcelona";

        assertThat(cache.getStationByID(id).getID()).isEqualTo(id);
        assertThat(cache.getStationByID(id).getCity()).isEqualTo(city);
    }

}