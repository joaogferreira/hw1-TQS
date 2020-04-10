package com.joao.tqshw1;

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
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class StationServiceTest {

    @Mock (lenient = true)
    private Cache cache;

    @InjectMocks
    private StationService stationService;

    @BeforeEach
    public void setup() throws Exception {
        Station st = new Station(1, "Barcelona");
        Mockito.when(cache.getStationByID(st.getID())).thenReturn(st);
        Mockito.when(cache.getStationByID(4200)).thenReturn(null); //Wrong ID
    }


}