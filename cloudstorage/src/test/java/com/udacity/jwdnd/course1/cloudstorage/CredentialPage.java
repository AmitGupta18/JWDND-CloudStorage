package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {
	@FindBy(id = "add-creds-btn")
	private WebElement addNewCredentialBtn;

	@FindBy(id = "credential-url")
	private WebElement credurl;

	@FindBy(id = "credential-username")
	private WebElement userName;
	

	@FindBy(id = "credential-password")
	private WebElement password;

	@FindBy(id = "credentialSubmit")
	private WebElement credentialSubmitBtn;
	
	@FindBy(xpath = "//*[@id='credentialTable']/tbody/tr/td[1]/button")
	private WebElement editCredentialBtn;

	public CredentialPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void addNewCredential(String url, String uname, String pwd, WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(addNewCredentialBtn));
		addNewCredentialBtn.click();

		wait.until(ExpectedConditions.visibilityOf(credurl));
		credurl.sendKeys(url);
		userName.sendKeys(uname);
		password.sendKeys(pwd);
		credentialSubmitBtn.submit();
	}
	
	public void updateCredential(String url, String uname, String pwd, WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(editCredentialBtn));
		editCredentialBtn.click();
		
		wait.until(ExpectedConditions.visibilityOf(credurl));
		credurl.clear();
		userName.clear();
		password.clear();
		
		credurl.sendKeys(url);
		userName.sendKeys(uname);
		password.sendKeys(pwd);
		credentialSubmitBtn.submit();
	}
}
