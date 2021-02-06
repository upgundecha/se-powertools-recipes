package com.example;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.util.Date;

public class ShutterBugDemoTest {

    WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void shutterBugDemoTest() throws Exception {
        driver.get("http://demo-store.seleniumacademy.com/");
        Assert.assertEquals("Madison Island", driver.getTitle());

        // capture entire page
        Shutterbug.shootPage(driver, Capture.FULL, true).save();

        WebElement storeLogo = driver.findElement(By.className("logo"));
        WebElement searchBox = driver.findElement(By.name("q"));

        searchBox.sendKeys("Testing");

        // blur and annotate
        Shutterbug.shootPage(driver)
                .blur(searchBox)
                .monochrome(storeLogo)
                .highlightWithText(storeLogo, Color.blue, 3, "Monochromed logo", Color.blue, new Font("SansSerif", Font.BOLD, 20))
                .highlightWithText(searchBox, "Blurred secret words")
                .withTitle("Store home page - " + new Date())
                .withName("home_page")
                .withThumbnail(0.7).save();

        WebElement firstPromoBanner =
                driver.findElement(By.cssSelector("ul.promos> li:nth-child(1) > a"));

        // image comparison
        String baselineFilePath = new File("src/test/resources/baseline_img/first_promo_banner.png").getAbsolutePath();

        Assert.assertTrue(Shutterbug
                .shootElement(driver, firstPromoBanner)
                .equalsWithDiff(baselineFilePath,"diff",0.1));

    }

    @After()
    public void tearDown() {
        driver.quit();
    }
}
