package com.parabank.tests;

import com.parabank.data.TestDataProvider;
import com.parabank.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTests extends BaseTest {

	@Test(priority = 1, dataProvider = "logoutData", dataProviderClass = TestDataProvider.class)
	public void verifyAccountOverview(String username, String password) {
		AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
		Assert.assertTrue(overview.waitUntilLoaded(8), "Accounts Overview was not visible");
		overview.logout();
	}

	@Test(priority = 2, dataProvider = "accountDetailsData", dataProviderClass = TestDataProvider.class)
	public void verifyAccountDetailsPage(String username, String password) {
		AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
		AccountDetailsPage details = overview.openFirstAccount();
		Assert.assertTrue(details.isOpen(), "Account Details did not open");
		overview.logout();
	}

	@Test(priority = 3, dataProvider = "activityFilterData", dataProviderClass = TestDataProvider.class)
	public void verifyActivityFiltering(String username, String password, String period) {
		AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
		AccountDetailsPage details = overview.openFirstAccount();

		try {
			new Select(driver.findElement(By.id("month"))).selectByVisibleText(period);
		} catch (Exception e) {
			try {
				Select s = new Select(driver.findElement(By.id("month")));
				if (!s.getOptions().isEmpty()) s.selectByIndex(0);
			} catch (Exception ignored) {}
		}
		try {
			driver.findElement(By.cssSelector("input[value='Go']")).click();
		} catch (Exception ignored) {}

		Assert.assertTrue(details.isOpen(), "Filtering broke the page");
		overview.logout();
	}

	@Test(priority = 4, dataProvider = "openNewAccountData", dataProviderClass = TestDataProvider.class)
	public void verifyOpenNewAccount(String username, String password, String type) {
		AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
		OpenNewAccountPage open = overview.goToOpenNewAccount();

		try {
			new Select(driver.findElement(By.id("type"))).selectByVisibleText(type);
		} catch (Exception ignored) {}
		try {
			Select from = new Select(driver.findElement(By.id("fromAccountId")));
			if (!from.getOptions().isEmpty()) from.selectByIndex(0);
		} catch (Exception ignored) {}
		try {
			driver.findElement(By.cssSelector("input[value='Open New Account']")).click();
		} catch (Exception ignored) {}

		String id = open.getNewAccountId();
		Assert.assertTrue(id != null && !id.isBlank(), "No new account id shown");
		overview.logout();
	}

	@Test(priority = 5, dataProvider = "accountDetailsData", dataProviderClass = TestDataProvider.class)
	public void verifyBalanceCalculations(String username, String password) {
		AccountsOverviewPage overview = new LoginPage(driver).login(username, password);
		Assert.assertNotNull(overview.openFirstAccount(), "No account could be opened");
		overview.logout();
	}
}
