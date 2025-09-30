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
import java.util.ArrayList;
import java.util.List;

public class AccountsOverviewPage extends BasePage {

    @FindBy(xpath = "//a[normalize-space()='Accounts Overview']")
    private WebElement header;

    @FindBy(linkText = "Log Out")
    private WebElement logoutLink;

    @FindBy(linkText = "Transfer Funds")
    private WebElement transferLink;

    @FindBy(linkText = "Bill Pay")
    private WebElement billPayLink;

    @FindBy(linkText = "Open New Account")
    private WebElement openNewAccountLink;

    @FindBy(linkText = "Find Transactions")
    private WebElement findTransLink;

    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean waitUntilLoaded(int seconds) {
        try {
            WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            w.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[normalize-space()='Accounts Overview']")));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    public boolean isOnOverviewNow() {
        return !driver.findElements(By.xpath("//h1[text()='Accounts Overview']")).isEmpty();
    }

    public boolean isAccountsOverviewDisplayed() {
        try {
            waitForVisible(header);
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public LoginPage logout() {
        click(logoutLink);
        return new LoginPage(driver);
    }

    public TransferFundsPage goToTransfer() {
        click(transferLink);
        return new TransferFundsPage(driver);
    }

    public BillPayPage goToBillPay() {
        click(billPayLink);
        return new BillPayPage(driver);
    }

    public OpenNewAccountPage goToOpenNewAccount() {
        click(openNewAccountLink);
        return new OpenNewAccountPage(driver);
    }

    public FindTransactionsPage goToFindTransactions() {
        click(findTransLink);
        return new FindTransactionsPage(driver);
    }

    public AccountDetailsPage openFirstAccount() {
        waitForVisible(header);
        List<WebElement> links = getAccountLinks();
        if (links.isEmpty()) {
            throw new IllegalStateException("No account links found on Accounts Overview page.");
        }
        links.get(0).click();
        return new AccountDetailsPage(driver);
    }

    public List<String> getVisibleAccountIds() {
        List<String> ids = new ArrayList<>();
        for (WebElement l : getAccountLinks()) {
            ids.add(l.getText().trim());
        }
        return ids;
    }

    private List<WebElement> getAccountLinks() {
        return driver.findElements(By.xpath("//a[contains(@href,'activity.htm?id=')]"));
    }
}
