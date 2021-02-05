package com.example;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.Tesseract;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.image.BufferedImage;

public class PromoBannerTest {

    WebDriver driver;
    Tesseract tesseract;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        tesseract = new Tesseract();
        tesseract.setDatapath("/usr/local/Cellar/tesseract/4.1.1/share/tessdata/");
        tesseract.setLanguage("eng");
    }

    @Test
    public void promoBannerTest() throws Exception {
        driver.get("http://demo-store.seleniumacademy.com/");

        WebElement promoBanner = driver.findElement(By.cssSelector("ul.promos> li:nth-child(1) > a"));
        BufferedImage bannerImg = Shutterbug.shootElement(driver, promoBanner).getImage();

        String result = tesseract.doOCR(bannerImg).trim();
        Assert.assertEquals("HOME & DECOR\nFOR ALL YOUR SPACES", result);
    }

    @After()
    public void tearDown() {
        driver.quit();
    }
}
