package com.example;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


public class WiremockDemoTest {

    WebDriver driver;
    WireMockServer wireMockServer;

    @Before
    public void setup() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage()
                .timeouts()
                .implicitlyWait(30, TimeUnit.SECONDS);

        // Setup and start the Wiremock server
        wireMockServer = new WireMockServer(options()
                .port(4000)
                .notifier(new ConsoleNotifier(true)));

        wireMockServer.start();
    }

    @Test
    public void wiremockDemoTest() {
        // Define stub for the test
        wireMockServer
                .stubFor(get(urlEqualTo("/data/2.5/weather?q=Singapore&appid=undefined&units=metric"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("json/weather_data.json")));

        driver.get("http://localhost:3000/");

        WebElement addLocationButton = driver
                .findElement(By
                        .cssSelector("button[aria-label='add weather location']"));
        addLocationButton.click();

        WebElement locationField = driver.findElement(By.tagName("input"));
        locationField.sendKeys("Singapore" + Keys.ENTER);

        WebElement temparatureLabel = driver
                .findElement(By
                        .cssSelector("div.makeStyles-detailLine-8 > h6:nth-child(1)"));

        Assert.assertEquals("31°C", temparatureLabel.getText());
    }

    @After()
    public void tearDown() {
        driver.quit();
        wireMockServer.stop();
    }
}
