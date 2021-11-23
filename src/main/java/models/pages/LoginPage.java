package models.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import models.components.authentication.loginDialogComponent;
import models.components.global.BottomNavComponent;
import org.openqa.selenium.By;

public class LoginPage {
    private final AppiumDriver<MobileElement> appiumDriver;
    private final By usernameSel = MobileBy.AccessibilityId("input-email");
    private final By passwordSel = MobileBy.AccessibilityId("input-password");
    private final By loginBtnSel = MobileBy.AccessibilityId("button-LOGIN");
    private BottomNavComponent bottomNavComponent;
    private loginDialogComponent loginDialogComponent;

    public LoginPage(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public LoginPage inputUsername(String username){
        appiumDriver.findElement(usernameSel).sendKeys(username);
        return this;
    }

    public LoginPage inputPassword(String password){
        appiumDriver.findElement(passwordSel).sendKeys(password);
        return this;
    }

    public LoginPage clickOnLoginBtn(){
        appiumDriver.findElement(loginBtnSel).click();
        return this;
    }
    public BottomNavComponent BottomNavComp() {
        return new BottomNavComponent(appiumDriver);
    }
    public loginDialogComponent LoginDialogComponent(){
        return new loginDialogComponent(appiumDriver);
    }
}
