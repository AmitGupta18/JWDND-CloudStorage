package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {	
	@FindBy(id = "inputUsername")
	private WebElement username;
	
	@FindBy(id = "inputPassword")
	private WebElement password;
	
	@FindBy(id ="login-btn")
	private WebElement loginBtn;
	
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void login(String uname, String pwd) {
		username.sendKeys(uname);
		password.sendKeys(pwd);
		
		loginBtn.submit();
	}
}
