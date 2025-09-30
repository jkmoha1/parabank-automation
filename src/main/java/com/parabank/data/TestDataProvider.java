package com.parabank.data;

import com.parabank.utils.ExcelReader;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
    // Now that testdata.xlsx is in src/test/resources, just use the classpath name:
    private static final String PATH = "testdata.xlsx";

    @DataProvider(name = "loginData")
    public Object[][] loginData(){ return new ExcelReader(PATH).getTestData("login"); }

    @DataProvider(name = "registrationData")
    public Object[][] registrationData(){ return new ExcelReader(PATH).getTestData("registration"); }

    @DataProvider(name = "logoutData")
    public Object[][] logoutData(){ return new ExcelReader(PATH).getTestData("logout"); }

    @DataProvider(name = "forgotLoginData")
    public Object[][] forgotLoginData(){ return new ExcelReader(PATH).getTestData("forgotLogin"); }

    @DataProvider(name = "accountDetailsData")
    public Object[][] accountDetailsData(){ return new ExcelReader(PATH).getTestData("accountDetails"); }

    @DataProvider(name = "activityFilterData")
    public Object[][] activityFilterData(){ return new ExcelReader(PATH).getTestData("activityFilter"); }

    @DataProvider(name = "openNewAccountData")
    public Object[][] openNewAccountData(){ return new ExcelReader(PATH).getTestData("openNewAccount"); }

    @DataProvider(name = "transferData")
    public Object[][] transferData(){ return new ExcelReader(PATH).getTestData("transfer"); }

    @DataProvider(name = "billPayData")
    public Object[][] billPayData(){ return new ExcelReader(PATH).getTestData("billpay"); }

    @DataProvider(name = "findTransactionsData")
    public Object[][] findTransactionsData(){ return new ExcelReader(PATH).getTestData("findTransactions"); }

    @DataProvider(name = "contactData")
    public Object[][] contactData(){ return new ExcelReader(PATH).getTestData("contact"); }

    @DataProvider(name = "loanData")
    public Object[][] loanData(){ return new ExcelReader(PATH).getTestData("loan"); }

    @DataProvider(name = "updateProfileData")
    public Object[][] updateProfileData(){ return new ExcelReader(PATH).getTestData("updateProfile"); }

    @DataProvider(name = "responsivenessData")
    public Object[][] responsivenessData(){ return new ExcelReader(PATH).getTestData("responsiveness"); }
}
