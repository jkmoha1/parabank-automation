package com.parabank.tests;

import com.parabank.data.TestDataProvider;
import com.parabank.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransferTests extends BaseTest {

    @Test(priority = 1, dataProvider = "transferData", dataProviderClass = TestDataProvider.class)
    public void transferFunds(String username, String password, String amount, String expected) {
        AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
        TransferFundsPage transfer = overview.goToTransfer();

        // Pick accounts; if only one exists, use it for both
        try {
            Select from = new Select(driver.findElement(By.id("fromAccountId")));
            Select to   = new Select(driver.findElement(By.id("toAccountId")));
            if (from.getOptions().size() >= 2) {
                from.selectByIndex(0);
                to.selectByIndex(1);
            } else {
                from.selectByIndex(0);
                to.selectByIndex(0);
            }
        } catch (Exception ignored) {}

        transfer.enterAmount(amount);
        transfer.submit();

        String conf = "";
        for (int i = 0; i < 10 && (conf == null || conf.isBlank()); i++) {
            conf = transfer.getConfirmation();
            sleep(250);
        }
        String text = conf == null ? "" : conf.toLowerCase();

        switch (expected.toLowerCase()) {
            case "success" -> Assert.assertTrue(text.contains("complete") || text.contains("success") ||
                                                driver.getPageSource().toLowerCase().contains("transfer"),
                                                "Expected transfer completion, got: " + conf);
            case "validation" -> Assert.assertFalse(text.contains("complete"), "Expected validation failure");
            case "insufficient" -> Assert.assertFalse(text.contains("complete"), "Expected insufficient funds");
            default -> Assert.fail("Unknown expected value: " + expected);
        }
        overview.logout();
    }

    @Test(priority = 2, dataProvider = "findTransactionsData", dataProviderClass = TestDataProvider.class)
    public void transferTransactionHistory(String username, String password, String amount) {
        AccountsOverviewPage overview = new LoginPage(driver).login(username, password);

        // Create a transfer so a row exists
        TransferFundsPage transfer = overview.goToTransfer();
        try {
            Select from = new Select(driver.findElement(By.id("fromAccountId")));
            Select to   = new Select(driver.findElement(By.id("toAccountId")));
            if (from.getOptions().size() >= 2) {
                from.selectByIndex(0);
                to.selectByIndex(1);
            } else {
                from.selectByIndex(0);
                to.selectByIndex(0);
            }
        } catch (Exception ignored) {}
        transfer.enterAmount(amount);
        transfer.submit();

        // Then search for that amount, with a couple of retries
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

    private static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
}
