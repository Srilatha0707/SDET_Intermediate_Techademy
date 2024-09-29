package com.qa.testScript;


import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Login_SauceDemo {
    private WebDriver driver;
    WebDriverWait wait;
    private String baseUrl = "https://www.saucedemo.com/";

    @BeforeSuite
    public void setUp() {
    	WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable notifications");
		DesiredCapabilities cp = new DesiredCapabilities();
		cp.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(cp);
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }

    @Test(priority =  1)
    @Parameters({"username", "password"})
    public void testValidLogin(String username, String password) throws InterruptedException {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        String title = driver.getTitle();
        Assert.assertEquals(title, "Swag Labs");

        captureScreenshot("valid_login.png");
        
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("logout_sidebar_link")).click();
    }

    @Test(priority =  1, dependsOnMethods = "testValidLogin")
    public void testInvalidLogin() {
    	
        driver.findElement(By.id("user-name")).sendKeys("standard_Invaliduser");
        driver.findElement(By.id("password")).sendKeys("secret_Invalidpwd");
        driver.findElement(By.id("login-button")).click();

        driver.getPageSource().contains("Epic sadface: Username and password do not match any user in this service");
        captureScreenshot("invalid_login.png");
    }

    @AfterSuite
    public void tearDown() {
        //driver.quit();
    }

    private void captureScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("screenshots/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
