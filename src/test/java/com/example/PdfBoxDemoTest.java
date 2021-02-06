package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

public class PdfBoxDemoTest {

    WebDriver driver;
    String downloadFolderPath;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> preferences = new Hashtable<String, Object>();

        chromeOptions.setExperimentalOption("prefs", preferences);

        downloadFolderPath = new File("target").getAbsolutePath() + "/downloads";

        preferences.put("download.default_directory", downloadFolderPath);
        preferences.put("download.prompt_for_download", false);
        preferences.put("download.directory_upgrade", true);
        preferences.put("plugins.always_open_pdf_externally", true);

        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    public void pdfBoxDemoTest() throws Exception {

        PDDocument doc;

        // Option 1
        // URL pdfUrl = new URL("http://www.orimi.com/pdf-test.pdf");
        // InputStream in = pdfUrl.openStream();
        // BufferedInputStream bf = new BufferedInputStream(in);
        // doc = PDDocument.load(in);

        // Option 2
        driver.get("http://www.orimi.com/pdf-test.pdf");
        Thread.sleep(1000);
        File file = new File(downloadFolderPath + "/pdf-test.pdf");
        doc = PDDocument.load(file);

        Assert.assertEquals(1, doc.getNumberOfPages());
        String content = new PDFTextStripper().getText(doc);

        Assert.assertTrue(content.contains("PDF Test File"));
    }

    @After()
    public void tearDown() {
        driver.quit();
    }
}
