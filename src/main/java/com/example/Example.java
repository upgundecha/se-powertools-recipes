package com.example;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;

public class Example {

    public static void main(String... args) throws Exception {
        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("http://demo-store.seleniumacademy.com/");

        new WebDriverWait(driver, 30)
                .until(ExpectedConditions
                        .titleIs("Madison Island"));

        Shutterbug.shootPage(driver, Capture.FULL, true).save();

        WebElement storeLogo = driver.findElement(By.className("logo"));
        WebElement searchBox = driver.findElement(By.name("q"));

        searchBox.sendKeys("Testing");

        Shutterbug.shootPage(driver)
                .blur(searchBox)
                .monochrome(storeLogo)
                .highlightWithText(storeLogo, Color.blue, 3, "Monochromed logo", Color.blue, new Font("SansSerif", Font.BOLD, 20))
                .highlightWithText(searchBox, "Blurred secret words")
                .withTitle("Store home page - " + new Date())
                .withName("home_page")
                .withThumbnail(0.7).save();

        WebElement promoBanner = driver.findElement(By.cssSelector("ul.promos> li:nth-child(2) > a"));
        Shutterbug.shootElement(driver, promoBanner).withName("first_promo_banner").save();
        BufferedImage bannerImg = Shutterbug.shootElement(driver, promoBanner).getImage();

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/usr/local/Cellar/tesseract/4.1.1/share/tessdata/");
        tesseract.setLanguage("eng");

        String result = tesseract.doOCR(bannerImg);
        System.out.println(result);

        driver.quit();
    }
}
