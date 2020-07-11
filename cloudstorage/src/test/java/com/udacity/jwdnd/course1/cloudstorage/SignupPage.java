package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

	@FindBy(id = "inputFirstName")
	private WebElement firstName;
	
	@FindBy(id = "inputLastName")
	private WebElement lastName;
	
	@FindBy(id = "inputUsername")
	private WebElement username;
	
	@FindBy(id = "inputPassword")
	private WebElement password;
	
	@FindBy(id ="signup-btn")
	private WebElement signupBtn;
	
	public SignupPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void signup(String fname, String lname, String uname, String pwd) {
		firstName.clear();
		lastName.clear();
		username.clear();
		password.clear();
		
		firstName.sendKeys(fname);
		lastName.sendKeys(lname);
		username.sendKeys(uname);
		password.sendKeys(pwd);
		
		signupBtn.submit();
	}
}
