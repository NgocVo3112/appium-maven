package test_flows.authentication;

import com.sun.net.httpserver.Authenticator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import models.components.global.BottomNavComponent;
import models.pages.LoginPage;
import org.testng.Assert;
import test_data.LoginCreds;

public class LoginFlow {
    private AppiumDriver<MobileElement> appiumDriver;
    private LoginPage loginPage;

    public LoginFlow(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void initLoginPage(){
        loginPage = new LoginPage(appiumDriver);
    }
    public LoginFlow navigateToLoginForm(){
        if(loginPage ==null)
            initLoginPage();
        // Bottom Nav Comp
        BottomNavComponent bottomNavComponent = loginPage.BottomNavComp();
        bottomNavComponent.clickOnLoginLabel();
        return this;
    }
    public LoginFlow login (LoginCreds loginCreds){
        if(loginPage == null)
            throw new RuntimeException("Please run navigateToLoginForm first");
        // Login page
        LoginPage loginPage = new LoginPage(appiumDriver);

        // Fill Login Form
        loginPage
                .inputUsername(loginCreds.getUsername())
                .inputPassword(loginCreds.getPassword())
                .clickOnLoginBtn();
        return this;
    }
    public void verifyLoginSuccess(){
            String actualLoginMsg = loginPage.LoginDialogComponent().msgTitle();
            boolean isTitleCorrect = actualLoginMsg.equals("Success");
            String customERRMes = "ERR - Login with title incorrect";
        Assert.assertTrue(isTitleCorrect, customERRMes);
            System.out.println(actualLoginMsg);

    }
    public void verifyLoginWithIncorrectCreds(){

    }
}
