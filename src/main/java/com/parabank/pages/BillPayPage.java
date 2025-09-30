package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BillPayPage extends BasePage {
    @FindBy(name="payee.name") private WebElement payeeName;
    @FindBy(name="payee.address.street") private WebElement payeeAddress;
    @FindBy(name="payee.address.city") private WebElement payeeCity;
    @FindBy(name="payee.address.state") private WebElement payeeState;
    @FindBy(name="payee.address.zipCode") private WebElement payeeZip;
    @FindBy(name="payee.phoneNumber") private WebElement payeePhone;
    @FindBy(name="payee.accountNumber") private WebElement payeeAccount;
    @FindBy(name="verifyAccount") private WebElement payeeVerify;
    @FindBy(name="amount") private WebElement amount;
    @FindBy(css="input[value='Send Payment']") private WebElement sendPayment;
    @FindBy(css="#rightPanel .title") private WebElement confirmationTitle;
    @FindBy(css="#rightPanel .error") private WebElement errorPanel;

    public BillPayPage(WebDriver driver){ super(driver); PageFactory.initElements(driver,this);}    

    public void fillPayee(String n,String a,String c,String s,String z,String p,String acc,String amt){
        type(payeeName,n); type(payeeAddress,a); type(payeeCity,c); type(payeeState,s); type(payeeZip,z);
        type(payeePhone,p); type(payeeAccount,acc); type(payeeVerify,acc); type(amount,amt);
    }
    public void submit(){ click(sendPayment);}    
    public String getConfirmation(){ return getText(confirmationTitle);}    
    public boolean hasValidationError(){ try { return errorPanel.isDisplayed(); } catch(Exception e){ return false; } }
}
