package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManagerDemoTest {

    WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void wdmTest() {
        driver.get("http://demo-store.seleniumacademy.com/");
        Assert.assertEquals("Madison Island", driver.getTitle());
    }

    @After()
    public void tearDown() {
        driver.quit();
    }
}
