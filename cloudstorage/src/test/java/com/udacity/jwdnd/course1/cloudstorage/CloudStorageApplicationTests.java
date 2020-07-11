package com.udacity.jwdnd.course1.cloudstorage;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class CloudStorageApplicationTests {

	private static final String fname = "DummyFirstName";
	private static final String lname = "DummyLastName";
	private static final String uname = "DummyUserName";
	private static final String pwd = "DummyPwd";

	@LocalServerPort
	private int port;

	private String baseURL;

	private WebDriver driver;

	private WebDriverWait wait;
	
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(driver, 1000);
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
	
	private void login() {
		driver.get(baseURL + "/login");

		wait.until(webDriver -> webDriver.findElement(By.id("login-btn")));
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(uname, pwd);
	}

	@Test
	@Order(1)
	public void testSignUpAndLogin() {

		driver.get(baseURL + "/signup");

		wait.until(webDriver -> webDriver.findElement(By.id("signup-btn")));
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(fname, lname, uname, pwd);

		login();
		Assertions.assertEquals("Home", driver.getTitle());

		wait.until(webDriver -> webDriver.findElement(By.id("logout-btn")));
		driver.findElement(By.id("logout-btn")).submit();
		wait.until(webDriver -> webDriver.findElement(By.id("login-btn")));

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	@Order(2)
	public void testCreateNoteFlow() {
		String noteTitle = "New Note Title";
		String noteDesc = "New Note Description";

		login();
		Assertions.assertEquals("Home", driver.getTitle());

		// Open Notes tab
		driver.get(baseURL + "/home");
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		// add note
		NotePage notePage = new NotePage(driver);
		notePage.addNewNote(noteTitle, noteDesc, wait);
		Assertions.assertEquals("Result", driver.getTitle());

		wait.until(ExpectedConditions.elementToBeClickable(By.id("redirectToHome")));
		driver.findElement(By.id("redirectToHome")).click();
		
		wait.until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertDoesNotThrow(() -> {
			driver.findElement(By.xpath("//th[text()='New Note Title']"));
		});

	}

	@Test
	@Order(3)
	public void testUpdateNoteFlow() {
		String noteTitle = "Updated Note Title";
		String noteDesc = "Updated Note Description";

		login();
		Assertions.assertEquals("Home", driver.getTitle());

		// Open notes tab
		driver.get(baseURL + "/home");
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		// Update Note
		NotePage notePage = new NotePage(driver);
		notePage.updateNote(noteTitle, noteDesc, wait);
		Assertions.assertEquals("Result", driver.getTitle());

		wait.until(ExpectedConditions.elementToBeClickable(By.id("redirectToHome")));
		driver.findElement(By.id("redirectToHome")).click();
		
		wait.until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertDoesNotThrow(() -> {
			driver.findElement(By.xpath("//th[text()='Updated Note Title']"));
		});
	}

	@Test
	@Order(4)
	public void testDeleteNote() {
		login();
		Assertions.assertEquals("Home", driver.getTitle());

		driver.get(baseURL + "/home");

		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		WebElement deleteBtn = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[1]/a"));
		wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
		deleteBtn.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("redirectToHome")));
		driver.findElement(By.id("redirectToHome")).click();
		
		wait.until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.xpath("//th[text()='Updated Note Title']"));
		});
	}
	
	
	@Test
	@Order(5)
	public void testCreateCredentialeFlow() {
		String url = "www.abc.com";
		String uname = "New Uname";
		String pwd = "New Pwd";

		login();
		Assertions.assertEquals("Home", driver.getTitle());

		// Open Credentials tab
		driver.get(baseURL + "/home");
		WebElement credsTab = driver.findElement(By.id("nav-credentials-tab"));
		credsTab.click();

		// add Credentials
		CredentialPage credsPage = new CredentialPage(driver);
		credsPage.addNewCredential(url, uname, pwd, wait);
		Assertions.assertEquals("Result", driver.getTitle());

		wait.until(ExpectedConditions.elementToBeClickable(By.id("redirectToHome")));
		driver.findElement(By.id("redirectToHome")).click();
		
		wait.until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("nav-credentials-tab")).click();

		Assertions.assertDoesNotThrow(() -> {
			driver.findElement(By.xpath("//th[text()='www.abc.com']"));
		});

	}

	@Test
	@Order(6)
	public void testUpdateCredentialFlow() {
		String url = "www.xyz.com";
		String uname = "Updated Uname";
		String pwd = "Updated Pwd";

		login();
		Assertions.assertEquals("Home", driver.getTitle());

		// Open Credentials tab
		driver.get(baseURL + "/home");
		WebElement credsTab = driver.findElement(By.id("nav-credentials-tab"));
		credsTab.click();

		// Update Note
		CredentialPage credsPage = new CredentialPage(driver);
		credsPage.updateCredential(url, uname, pwd, wait);
		Assertions.assertEquals("Result", driver.getTitle());

		wait.until(ExpectedConditions.elementToBeClickable(By.id("redirectToHome")));
		driver.findElement(By.id("redirectToHome")).click();
		
		wait.until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		
		driver.findElement(By.id("nav-credentials-tab")).click();

		Assertions.assertDoesNotThrow(() -> {
			driver.findElement(By.xpath("//th[text()='www.xyz.com']"));
		});
	}

	@Test
	@Order(7)
	public void testDeleteCredential() {
		login();
		Assertions.assertEquals("Home", driver.getTitle());

		driver.get(baseURL + "/home");

		WebElement credsTab = driver.findElement(By.id("nav-credentials-tab"));
		credsTab.click();

		WebElement deleteBtn = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a"));
		wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
		deleteBtn.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("redirectToHome")));
		driver.findElement(By.id("redirectToHome")).click();
		
		wait.until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("nav-credentials-tab")).click();

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.xpath("//th[text()='www.xyz.com']"));
		});
	}

}
