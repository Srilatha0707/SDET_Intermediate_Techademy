package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwagLabs_Pages {
	WebDriver driver;
	public SwagLabs_Pages(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "user-name")
	WebElement userName;
	public WebElement getUsername()
	{
		return userName;
	}
	@FindBy(id = "password")
	WebElement password;
	public WebElement getPassword()
	{
		return password;
	}
}
