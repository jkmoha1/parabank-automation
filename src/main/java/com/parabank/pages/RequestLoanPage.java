package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RequestLoanPage extends BasePage {
    @FindBy(id="amount") private WebElement amount;
    @FindBy(id="downPayment") private WebElement downPayment;
    @FindBy(id="fromAccountId") private WebElement fromAccount;
    @FindBy(css="input[value='Apply Now']") private WebElement applyBtn;
    @FindBy(id="loanStatus") private WebElement loanStatus;

    public RequestLoanPage(WebDriver driver){ super(driver); PageFactory.initElements(driver,this);}    

    public void requestLoan(String amt, String down){ type(amount,amt); type(downPayment,down); click(applyBtn);}    
    public String getStatus(){ try { return getText(loanStatus); } catch(Exception e){ return ""; } }    
}
