package com.joao.tqshw1;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {
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
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assertions.fail(verificationErrorString);
        }
    }

    @Test
    public void test() throws Exception {
        driver.get("http://localhost:8080");
        new Select(driver.findElement(By.id("city"))).selectByVisibleText("London");
        driver.findElement(By.id("ok")).click();
        //driver.findElement(By.xpath("//button[@type='button']")).click();
    }

    private boolean isElementPresent(By by) {
        driver.get("http://localhost:8080");
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

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
        Assertions.assertEquals(0,count_false);
    };
}
