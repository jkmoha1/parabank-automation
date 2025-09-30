package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object for the "Transfer Funds" screen in ParaBank.
 * Keeps it simple: type amount, choose accounts, click transfer, read confirmation.
 */
public class TransferFundsPage extends BasePage {

	@FindBy(id = "amount")private WebElement amount;

	@FindBy(id = "fromAccountId")
	private WebElement fromAccount;

	@FindBy(id = "toAccountId")
	private WebElement toAccount;

	@FindBy(css = "input[value='Transfer']")
	private WebElement transferBtn;

	// The page shows a title like "Transfer Complete!" after success
	@FindBy(css = "#rightPanel .title")
	private WebElement confirmationTitle;

	public TransferFundsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/** Basic sanity check that we are on the page (amount field exists). */
	public boolean onPage() {
		return !driver.findElements(By.id("amount")).isEmpty();
	}

	/** Type transfer amount. */
	public void enterAmount(String amt) {
		type(amount, amt);
	}

	/**
     * Choose From/To accounts by visible text.
     * If either input is blank/null or not found, it falls back to the first option (index 0).
     * This avoids flakiness during demo if only one account exists.
     */
	public void chooseAccounts(String fromVisibleText, String toVisibleText) {
		Select fromSel = new Select(fromAccount);
		Select toSel = new Select(toAccount);

		// FROM
		if (fromVisibleText == null || fromVisibleText.isBlank()) {
			fromSel.selectByIndex(0);
		} else {
			try {
				fromSel.selectByVisibleText(fromVisibleText);
			} catch (Exception e) {
				// fallback if the exact text doesn't exist
				fromSel.selectByIndex(0);
			}
		}

		// TO
		if (toVisibleText == null || toVisibleText.isBlank()) {
			toSel.selectByIndex(0);
		} else {
			try {
				toSel.selectByVisibleText(toVisibleText);
			} catch (Exception e) {
				toSel.selectByIndex(0);
			}
		}
	}

	/** Clicks the Transfer button. */
	public void submit() {
		click(transferBtn);
	}

	/** Convenience: do the whole flow in one call. */
	public void transfer(String amt, String fromVisibleText, String toVisibleText) {
		enterAmount(amt);
		chooseAccounts(fromVisibleText, toVisibleText);
		submit();
	}

	/** Returns the success title text after a transfer (e.g., "Transfer Complete!"). */
	public String getConfirmation() {
		return getText(confirmationTitle);
	}
}
