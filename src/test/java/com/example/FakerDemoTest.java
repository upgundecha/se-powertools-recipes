package com.example;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FakerDemoTest {

    WebDriver driver;
    Faker faker;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        faker = new Faker();
    }

    @Test
    public void fakerDemoTest() {
        driver.get("http://demo-store.seleniumacademy.com/customer/account/create/");

        WebElement firstNameField = driver.findElement(By.id("firstname"));
        WebElement lastNameField = driver.findElement(By.id("lastname"));
        WebElement emailAddressField = driver.findElement(By.id("email_address"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement confirmPasswordField = driver.findElement(By.id("confirmation"));
        WebElement registerButton = driver.findElement(By.cssSelector("button[title=Register]"));

//         String firstName = "Jon";
//         String lastName = "Lee";
//         String email = "jon.lee@jl.com";
//         String password = "P@ssword";
//         String greetings = String.format("Hello, %s %s!", firstName, lastName);

         //with Faker
         String firstName = faker.name().firstName();
         String lastName = faker.name().lastName();
         String email = faker.internet().emailAddress();
         String password = "P@ssword";
         String greetings = String.format("Hello, %s %s!", firstName, lastName);

        // Registration steps
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        emailAddressField.sendKeys(email);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(password);
        registerButton.click();

        // Check the new user is created
        WebElement greetingsLabel = driver.findElement(By.cssSelector("div.welcome-msg p.hello"));
        Assert.assertEquals(greetings, greetingsLabel.getText());

        Shutterbug.shootElement(driver, greetingsLabel)
                .withTitle("Greetings Label").withName("greetings_label").save();
    }

    @After()
    public void tearDown() {
        driver.quit();
    }
}