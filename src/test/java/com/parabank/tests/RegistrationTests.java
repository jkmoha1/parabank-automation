package com.parabank.tests;

import com.parabank.data.TestDataProvider;
import com.parabank.pages.AccountsOverviewPage;
import com.parabank.pages.LoginPage;
import com.parabank.utils.TestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTest {

    @Test(priority = 1, dataProvider = "registrationData", dataProviderClass = TestDataProvider.class)
    public void testRegistration(String first, String last, String addr, String city, String state, String zip,
                                 String phone, String ssn, String username, String password) {

        String baseUser = (username == null || username.isBlank()) ? "user" : username;
        String u1 = baseUser.contains("{uniq}") ? TestUtil.expand(baseUser) : baseUser + TestUtil.uniq();

        AccountsOverviewPage overview = new LoginPage(driver)
                .clickRegister()
                .register(first, last, addr, city, state, zip, phone, ssn, u1, password);

        boolean ok = overview.waitUntilLoaded(8);

        if (!ok) {
            String u2 = "user" + TestUtil.uniq();
            overview = new LoginPage(driver)
                    .clickRegister()
                    .register(first, last, addr, city, state, zip, phone, ssn, u2, password);
            ok = overview.waitUntilLoaded(8);
        }

        Assert.assertTrue(ok, "Did not land on Accounts Overview after registration.");
        overview.logout();
    }
}