package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends BasePage {
    @FindBy(id="customer.firstName") private WebElement firstName;
    @FindBy(id="customer.lastName") private WebElement lastName;
    @FindBy(id="customer.address.street") private WebElement address;
    @FindBy(id="customer.address.city") private WebElement city;
    @FindBy(id="customer.address.state") private WebElement state;
    @FindBy(id="customer.address.zipCode") private WebElement zip;
    @FindBy(id="customer.phoneNumber") private WebElement phone;
    @FindBy(id="customer.ssn") private WebElement ssn;
    @FindBy(id="customer.username") private WebElement username;
    @FindBy(id="customer.password") private WebElement password;
    @FindBy(id="repeatedPassword") private WebElement repeatedPassword;
    @FindBy(css="input[value='Register']") private WebElement registerBtn;

    public RegisterPage(WebDriver driver){ super(driver); PageFactory.initElements(driver,this); }

    public AccountsOverviewPage register(String f, String l, String addr, String c, String st, String z, String ph, String s, String u, String p){
        type(firstName,f); type(lastName,l); type(address,addr); type(city,c); type(state,st); type(zip,z);
        type(phone,ph); type(ssn,s); type(username,u); type(password,p); type(repeatedPassword,p);
        click(registerBtn);
        return new AccountsOverviewPage(driver);
    }
}
