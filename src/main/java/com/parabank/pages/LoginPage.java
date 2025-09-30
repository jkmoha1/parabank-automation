package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

	@FindBy(name = "username")
	private WebElement usernameField;

	@FindBy(name = "password")
	private WebElement passwordField;

	@FindBy(css = "input[value='Log In']")
	private WebElement loginButton;

	@FindBy(css = ".error")
	private WebElement errorMessage;

	@FindBy(linkText = "Register")
	private WebElement registerLink;

	@FindBy(linkText = "Forgot login info?")
	private WebElement forgotLoginLink;

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/** Basic login flow; caller decides how to verify success/failure. */
	public AccountsOverviewPage login(String user, String pass) {
		type(usernameField, user);
		type(passwordField, pass);
		click(loginButton);
		return new AccountsOverviewPage(driver);
	}

	/** Returns the visible error message text (waits until it’s visible). */
	public String getErrorMessage() {
		waitForVisible(errorMessage);
		return errorMessage.getText();
	}

	public RegisterPage clickRegister() {
		click(registerLink);
		return new RegisterPage(driver);
	}

	public ForgotLoginInfoPage clickForgotLogin() {
		click(forgotLoginLink);
		return new ForgotLoginInfoPage(driver);
	}

	public boolean isStillOnLoginPage() {
		return !driver.findElements(By.cssSelector("input[value='Log In']")).isEmpty();
	}

	public String waitForErrorMessage(int seconds) {
		try {
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			// ParaBank usually shows errors inside #rightPanel .error
			return w.until(
					ExpectedConditions.visibilityOfElementLocated(
							By.cssSelector("#rightPanel .error, .error")
							)
					).getText();
		} catch (TimeoutException te) {
			return "";
		}
	}

	public boolean waitForAccountsOverview(int seconds) {
		try {
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			w.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//a[normalize-space()='Accounts Overview']")));
			return true;
		} catch (TimeoutException te) {
			return false;
		}
	}
}
