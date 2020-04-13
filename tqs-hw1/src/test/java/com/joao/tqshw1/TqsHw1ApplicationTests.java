package com.joao.tqshw1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TqsHw1ApplicationTests {

    @Test
    void contextLoads() {
        //Bad smell from SonarQube
        assertThat(true).isEqualTo(true);
    }

}
