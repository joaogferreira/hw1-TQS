package com.joao.tqshw1;

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
        boolean bool = isElementPresent(By.id("ok"));
        Assertions.assertEquals(true,bool);
    };
}
