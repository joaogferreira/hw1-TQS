package com.joao.tqshw1;

import org.junit.Before;
import org.junit.Test;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class CacheUnitTest {
    /**
     * Cache unit tests - Tecnologia utilizada: jUnit
     */

    private AirQuality airQuality;
    private Info info;
    private String status,city;
    private long time;
    private Station station;
    private int id, before_size, after_size, old_req, new_req, size1, size2;
    Cache cache;

    Map<String,AirQuality> airQualityMap = Cache.getAirQuality();
    Map<Integer,Station> stationMap = Cache.getStations();

    @Before
    public void prepare() {
        cache = new Cache();
        HashMap<String, HashMap<String,Float>> info_example = new HashMap<>();

        HashMap<String,Float> toPutInInfo1 = new HashMap<>();
        HashMap<String,Float> toPutInInfo2 = new HashMap<>();

        toPutInInfo1.put("v", (float) 2.0);
        toPutInInfo2.put("v", (float) 17.4);

        info_example.put("co",toPutInInfo1);
        info_example.put("pm25",toPutInInfo2);

        Info info = new Info(20,info_example);

        time = new Timestamp(System.currentTimeMillis()).getTime();

        airQuality = new AirQuality("ok",info,time);

    }

    /**
     * testSetAirQuality - verifica se um objecto AirQuality é guardado correctamente na cache
     * o tamanho deve aumentar uma unidade (só é guardado um objecto)
     * o tamanho antes de ser guardado o objecto deve ser diferente do tamanho após ser guardado o objecto
     * o objecto retornado pela cache deve ser igual ao guardado
     */
    @Test
    public void testSetAirQuality(){
        before_size = airQualityMap.size();
        cache.setAirQuality("Porto",airQuality);
        after_size = airQualityMap.size();

        //Assert size before and after
        assertThat(before_size+1==after_size).isEqualTo(true);
        assertThat(before_size==after_size).isEqualTo(false);

        //Check if AirQuality objects are equal
        Map<String,AirQuality> aq = Cache.getAirQuality();
        assertThat(aq.get("Porto").equals(airQuality)).isEqualTo(true);
    }

    /**
     * testGetAirQuality - verifica se um objecto AirQuality retornado é o esperado
     * Começa por guardar um objecto predifinido e efectua um getAirQuality
     * Verifica se o objecto guardado e retornado são iguais
     * Verifica também se o número de requests aumentou uma unidade
     */
    @Test
    public void testGetAirQuality() {
        cache.setAirQuality("Aveiro",airQuality);
        old_req = cache.countRequests();
        Map <String,AirQuality> aq = cache.getAirQuality();
        new_req = cache.countRequests();

        //Number of requests
        assertThat(old_req==new_req).isEqualTo(false);
        assertThat(old_req+1==new_req).isEqualTo(true);

        assertThat(aq.equals(null)).isEqualTo(false);
        assertThat(airQuality.equals(aq.get("Aveiro"))).isEqualTo(true);


    }

    /**
     * testSetStation - verifica se um objecto Station é guardado correctamente na cache
     * o tamanho deve aumentar uma unidade (só é guardado um objecto)
     * o tamanho antes de ser guardado o objecto deve ser diferente do tamanho após ser guardado o objecto
     * o objecto retornado pela cache deve ser igual ao guardado
     */
    @Test
    public void testSetStation() {
        station = new Station(100,"Coimbra");
        before_size = cache.getStations().size();
        cache.setStation(station.getID(),station);
        after_size = cache.getStations().size();

        //Assert Size
        assertThat(before_size+1==after_size).isEqualTo(true);
        assertThat(before_size==after_size).isEqualTo(false);

        //Check if Station object fields are equals
        Map<Integer,Station> st = cache.getStations();
        assertThat(100==st.get(100).getID()).isEqualTo(true);
        assertThat("Coimbra".equals(st.get(100).getCity())).isEqualTo(true);

    }

    /**
     * testGetStation - verifica se um objecto Station retornado é o esperado
     * Começa por guardar um objecto predifinido e efectua um getStation
     * Verifica se o objecto guardado e retornado são iguais
     * Verifica também se o número de requests aumentou uma unidade
     */
    @Test
    public void testGetStation() {
        station = new Station(2,"Bragança");
        cache.setStation(station.getID(),station);
        old_req = cache.countRequests();
        Map<Integer,Station> st = cache.getStations();
        new_req = cache.countRequests();

        assertThat(st.equals(null)).isEqualTo(false);
        assertThat(station.equals(st.get(2))).isEqualTo(true);

        assertThat(old_req==new_req).isEqualTo(false);
        assertThat(old_req+1==new_req).isEqualTo(true);
    }

    /**
     * testCountRequests- testa se a contagem do número de requests é incrementa quando é feito um get
     * No lugar do método getAirQuality poderia ser feito um getStations
     */
    @Test
    public void testCountRequests() {
        old_req = cache.countRequests();
        cache.getAirQuality();
        new_req = cache.countRequests();

        assertThat(old_req+1==new_req).isEqualTo(true);
        assertThat(old_req==new_req).isEqualTo(false);
    }

    /**
     * testIsValid - Verifica se um registo AirQuality é válido (i.e, ainda não passou o TTL)
     * O TTL está definido como sendo de 10 minutos (600 000 ms)
     * Testo com dois registos : um para Cannes e outro para Houston
     * Para Cannes defino o tempo de registo como sendo o actual, para Houston defino como sendo 0ms (1970)
     * Ou seja, é esperado que Cannes seja válido e Houston inválido
     */
    @Test
    public void testIsValid(){
        cache.setAirQuality("Cannes",airQuality);
        cache.getAirQualityByCity("Cannes").setTime(System.currentTimeMillis());
        assertThat(cache.isValid("Cannes")).isEqualTo(true);

        cache.setAirQuality("Houston",airQuality);
        cache.getAirQualityByCity("Houston").setTime(0);
        assertThat(cache.isValid("Houston")).isEqualTo(false);
    }

    /**
     * testGetCitiesAvailable - verifica se as cidades disponíveis são guardadas correctamente
     * começo por guardar duas estações: New York com ID 6 e Trofa com ID 7
     * De seguida verifico se a lista de cidades disponíveis retornada pela cache (getCitiesAvailable())
     * contem as cidades guardadas
     */
    @Test
    public void testGetCitiesAvailable(){
        cache.setStation(1,new Station(6,"New York"));
        cache.setStation(2,new Station(7,"Trofa"));

        assertThat(cache.getCitiesAvailable().contains("New York")).isEqualTo(true);
        assertThat(cache.getCitiesAvailable().contains("Trofa")).isEqualTo(true);
        assertThat(cache.getCitiesAvailable().contains("Guarda")).isEqualTo(false);
    }

    /**
     * testHitAndMiss -
     * Começo por incrmentar o valor de Miss 200x e os de hits 11 vezes
     * Por fim verifico se o valor da sua soma corresponde ao esperado (211)
     * e se os seus valores individuais estão correctos
     */
    @Test
    public void testHitAndMiss(){
        for(int i=0;i<200;i++){ cache.incMiss(); }
        for(int j=0;j<11;j++){ cache.incHit(); }

        assertThat(cache.getHitAndMiss()==211).isEqualTo(true);
        assertThat(cache.getHit()==11).isEqualTo(true);
        assertThat(cache.getMiss()==200).isEqualTo(true);
    }

    /**
     * testGetStationByID -
     * Começo por guardar uma station com id 23 e cidade Los Angeles
     * Defino um id errado (99)
     * Invoco o método getStationByID e verifico se o objecto retornado tem os valores que foram inseridos
     */
    @Test
    public void testGetStationByID(){
        cache.setStation(23,new Station(23,"Los Angeles"));

        int wrong_id = 999;

        assertThat(cache.getStationByID(23).getCity().equals("Los Angeles")).isEqualTo(true);
        assertThat(cache.getStationByID(23).getID()==23).isEqualTo(true);
        assertThat(cache.getStationByID(wrong_id)).isEqualTo(null);
    }

    /**
     * testGetAirQualityByCity
     * Começo por criar um objecto info com todos os atributos necessários
     * De seguida crio um objecto airquality e guardo-o na cache
     * por fim, invoco o método getAirQualityByCity com o argumento correspondente à cidade associada ao objecto
     * airquality guardado
     * Testo este método verificando se os atributos do objecto retornado são iguais aos do objecto guardado
     * @throws InterruptedException
     */
    @Test
    public void testGetAirQualityByCity() throws InterruptedException {
        HashMap<String, HashMap<String,Float>> info_example = new HashMap<>();

        HashMap<String,Float> toPutInInfo1 = new HashMap<>();
        HashMap<String,Float> toPutInInfo2 = new HashMap<>();

        toPutInInfo1.put("v", (float) 1.0);
        toPutInInfo2.put("v", (float) 34.56);

        info_example.put("co",toPutInInfo1);
        info_example.put("pm25",toPutInInfo2);

        Info info = new Info(27,info_example);

        time = new Timestamp(System.currentTimeMillis()).getTime();
        airQuality = new AirQuality("ok",info,time);
        cache.setAirQuality("Portalegre",airQuality);

        AirQuality airQualityReceived = cache.getAirQualityByCity("Portalegre");
        assertThat(airQualityReceived.getTime()>=time).isEqualTo(true); //o tempo de registo pode ser ligeiramente superior
        assertThat(airQualityReceived.getStatus().equals("ok")).isEqualTo(true);
        assertThat(airQualityReceived.getData().equals(info)).isEqualTo(true);
    }
}
