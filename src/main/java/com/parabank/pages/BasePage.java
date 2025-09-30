package com.parabank.pages;

import com.parabank.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@SuppressWarnings("unused")
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage(WebDriver driver){
        this.driver = driver;
        long explicit = com.parabank.utils.ConfigReader.getInt("explicit.wait", 20);
        this.wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(explicit));
    }

    protected void click(WebElement e){ wait.until(ExpectedConditions.elementToBeClickable(e)).click(); }
    protected void type(WebElement e, String text){ wait.until(ExpectedConditions.visibilityOf(e)).clear(); e.sendKeys(text); }
    protected void waitForVisible(WebElement e){ wait.until(ExpectedConditions.visibilityOf(e)); }
    protected String getText(WebElement e){ return wait.until(ExpectedConditions.visibilityOf(e)).getText(); }

    protected boolean appearsInTime(org.openqa.selenium.By locator, int seconds) {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(seconds))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (org.openqa.selenium.TimeoutException te) {
            return false;
        }
    }

    protected boolean visibleInTime(org.openqa.selenium.By locator, int seconds) {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(seconds))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (org.openqa.selenium.TimeoutException te) {
            return false;
        }
    }
    protected void closePopupsIfAny() {
        try {
            driver.switchTo().alert().accept();
            System.out.println("Closed an alert popup");
        } catch (Exception ignored) {
        }

        try {
            var buttons = driver.findElements(
                    org.openqa.selenium.By.cssSelector("button[aria-label='Close'], .close, [data-dismiss='modal']"));
            if (!buttons.isEmpty()) {
                buttons.get(0).click();
                System.out.println("Closed a popup/modal");
            }
        } catch (Exception ignored) {
        }
    }


}
