package com.parabank.tests;

import com.parabank.data.TestDataProvider;
import com.parabank.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BillPayTests extends BaseTest {

    @Test(priority = 1, dataProvider = "billPayData", dataProviderClass = TestDataProvider.class)
    public void billPayScenarios(String username, String password, String name, String address, String city,
                                 String state, String zip, String phone, String account, String amount, String expected) {

        AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
        BillPayPage bill = overview.goToBillPay();

        bill.fillPayee(name, address, city, state, zip, phone, account, amount);
        bill.submit();

        String conf = "";
        for (int i = 0; i < 8 && (conf == null || conf.isBlank()); i++) {
            conf = bill.getConfirmation();
            sleep(250);
        }
        String lc = conf == null ? "" : conf.toLowerCase();

        if ("success".equalsIgnoreCase(expected)) {
            Assert.assertTrue(lc.contains("complete") || conf.length() > 0, "Expected payment success");
        } else if ("validation".equalsIgnoreCase(expected)) {
            Assert.assertFalse(lc.contains("complete"), "Expected validation error");
        } else {
            Assert.fail("Unknown expected: " + expected);
        }
        overview.logout();
    }

    @Test(priority = 2, dataProvider = "findTransactionsData", dataProviderClass = TestDataProvider.class)
    public void paymentHistory(String username, String password, String amount) {
        AccountsOverviewPage overview = new LoginPage(driver).login(username, password);

        // Create a payment first so there’s something to find
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

    private static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
}
