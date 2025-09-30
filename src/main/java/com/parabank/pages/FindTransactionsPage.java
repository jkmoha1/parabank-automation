package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FindTransactionsPage extends BasePage {
    @FindBy(id="amount") private WebElement amount;
    @FindBy(css="input[value='Find Transactions']") private WebElement findBtn;

    public FindTransactionsPage(WebDriver driver){ super(driver); PageFactory.initElements(driver,this);}    

    public void searchByAmount(String amt){ type(this.amount, amt); click(findBtn);}    
    public boolean hasResults(){ return driver.findElements(By.cssSelector("#transactionTable tbody tr")).size() > 0; }
}
