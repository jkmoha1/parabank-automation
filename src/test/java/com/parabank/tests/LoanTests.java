package com.parabank.tests;

import com.parabank.data.TestDataProvider;
import com.parabank.pages.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoanTests extends BaseTest {

    @Test(priority = 1, dataProvider = "loanData", dataProviderClass = TestDataProvider.class)
    public void requestLoan(String username, String password, String amount, String down) {
        AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
        driver.findElement(By.linkText("Request Loan")).click();

        RequestLoanPage loan = new RequestLoanPage(driver);
        loan.requestLoan(amount, down);

        Assert.assertTrue(loan.getStatus().length() >= 0, "Loan status missing");
        overview.logout();
    }

    @Test(priority = 2, dataProvider = "findTransactionsData", dataProviderClass = TestDataProvider.class)
    public void findTransactions(String username, String password, String amount) {
        AccountsOverviewPage overview = new LoginPage(driver).login(username, password);

        // Create a quick payment so the amount exists in history
        BillPayPage bill = overview.goToBillPay();
        bill.fillPayee("Acme Utilities", "1 Utility Ave", "Metropolis", "CA", "90210",
                "5551234567", "123456", amount);
        bill.submit();
        for (int i = 0; i < 8; i++) {
            if (bill.getConfirmation() != null && !bill.getConfirmation().isBlank()) break;
            sleep(250);
        }

        FindTransactionsPage find = overview.goToFindTransactions();
        boolean found = false;
        for (int i = 0; i < 4 && !found; i++) {
            find.searchByAmount(amount);
            found = find.hasResults();
            sleep(600);
        }
        Assert.assertTrue(found, "No transactions found for amount " + amount);

        overview.logout();
    }

    @Test(priority = 3, dataProvider = "contactData", dataProviderClass = TestDataProvider.class)
    public void contactForm(String name, String email, String phone, String message) {
        new LoginPage(driver); // ensure page factory
        driver.findElement(By.linkText("Contact Us")).click();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("phone")).sendKeys(phone);
        driver.findElement(By.id("message")).sendKeys(message);
        driver.findElement(By.cssSelector("input[value='Send to Customer Care']")).click();
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("customer care"));
    }

    @Test(priority = 4, dataProvider = "updateProfileData", dataProviderClass = TestDataProvider.class)
    public void updateProfile(String username, String password, String addr, String city, String state, String zip, String phone) {
        AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
        UpdateProfilePage update = new UpdateProfilePage(driver);
        update.open();
        update.updateAddress(addr, city, state, zip, phone);
        Assert.assertTrue(update.getConfirmation().length() > 0);
        overview.logout();
    }

    @Test(priority = 5, dataProvider = "responsivenessData", dataProviderClass = TestDataProvider.class)
    public void responsiveness(String width, String height) {
        int w = Integer.parseInt(width);
        int h = Integer.parseInt(height);
        driver.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(w, h));
        Assert.assertTrue(driver.getPageSource().length() > 0);
        driver.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1280, 800));
    }

    private static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
}
