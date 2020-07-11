package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {
	@FindBy(id = "add-note-btn")
	private WebElement addNewNoteBtn;

	@FindBy(id = "note-title")
	private WebElement noteTitle;

	@FindBy(id = "note-description")
	private WebElement noteDescription;

	@FindBy(id = "noteSubmit")
	private WebElement noteSubmitBtn;
	
	@FindBy(xpath = "//*[@id='userTable']/tbody/tr/td[1]/button")
	private WebElement editNoteBtn;

	public NotePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void addNewNote(String title, String description, WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(addNewNoteBtn));
		addNewNoteBtn.click();

		wait.until(ExpectedConditions.visibilityOf(noteTitle));
		noteTitle.sendKeys(title);
		noteDescription.sendKeys(description);
		noteSubmitBtn.submit();
	}
	
	public void updateNote(String title, String description, WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(editNoteBtn));
		editNoteBtn.click();
		
		wait.until(ExpectedConditions.visibilityOf(noteTitle));
		noteTitle.clear();
		noteDescription.clear();
		
		noteTitle.sendKeys(title);
		noteDescription.sendKeys(description);
		noteSubmitBtn.submit();
	}
}
