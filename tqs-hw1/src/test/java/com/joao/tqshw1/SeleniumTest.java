package com.joao.tqshw1;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTest {
    /**
     * Teste funcionais à interface web - Tecnologia utilizada: Selenium WebDriver
     * Testes realizados no Firefox
     */

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp(){
        System.setProperty("webdriver.gecko.driver","geckodriver");
        driver = new FirefoxDriver();
        baseUrl = "https://www.katalon.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String string_error= verificationErrors.toString();
        if (!"".equals(string_error)) {
            Assertions.fail(string_error);
        }
    }

    /**
     *  testPossibleCities - Verifica se é possível consultar os dados para todas as cidades
     *  (escolhendo cada cidade disponível e carregando no botão ok ao lado direito)
     * @throws Exception
     */
    @Test
    public void testPossibleCities() throws Exception {
        driver.get("http://localhost:8080");
        ArrayList<String> city_names = new ArrayList<>();
        city_names.add("Shanghai");city_names.add("Paris");city_names.add("London");city_names.add("Lisbon");
        city_names.add("Berlin");city_names.add("Tokyo");city_names.add("Munchen");city_names.add("Denver");
        city_names.add("Helsinki");city_names.add("Stockholm");city_names.add("Moscow");city_names.add("Madrid");

        for(int i=0;i<city_names.size();i++){
            new Select(driver.findElement(By.id("city"))).selectByVisibleText(city_names.get(i));
            driver.findElement(By.id("ok")).click();
        }
    }

    /**
     * isElementPresent - Verifica se um determinado elemento está presente
     */
    private boolean isElementPresent(By by) {
        driver.get("http://localhost:8080");
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * testElementPresence - este método utiliza o método definido anteriormente e verifica se cada elemento previamente
     * definido no html está presente
     * Basta um dos elementos não estar presente para o teste falhar
     */
    @Test
    public void testElementPresence() {
        int count_false = 0;
        boolean aux;
        //Todos os supostos elementos da página
        ArrayList<String> elems = new ArrayList<>();
        elems.add("city"); elems.add("ok"); elems.add("status"); elems.add("aqi"); elems.add("no2"); elems.add("p"); elems.add("o3");
        elems.add("pm25"); elems.add("t"); elems.add("so2"); elems.add("w"); elems.add("h"); elems.add("pm10"); elems.add("co");

        for(int i=0;i<elems.size();i++){
            aux = isElementPresent(By.id(elems.get(i)));
            if (!aux) { count_false++; }
        }
        assertThat(count_false==0).isEqualTo(true);
    };
}
