package test.authentication;

import driver.DriverFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import models.components.global.BottomNavComponent;
import models.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Authentication {
    public static void main(String[] args) {
        DriverFactory.startAppiumServer();

        try{
            // init driver
            AndroidDriver<MobileElement> androidDriver = DriverFactory.getAndroidDriver();

            // Login page
            LoginPage loginPage = new LoginPage(androidDriver);

            // Bottom Nav Comp
            BottomNavComponent bottomNavComponent = loginPage.BottomNavComp();
            bottomNavComponent.clickOnLoginLabel();

            // Fill Login Form
            loginPage
                    .inputUsername("Teo@sth.com")
                    .inputPassword("12345678")
                    .clickOnLoginBtn();

            String loginMsg = loginPage.LoginDialogComponent().msgTitle();
            System.out.println(loginMsg);

        }catch (Exception ignored){

        }finally {
            DriverFactory.stopAppiumServer();
        }

    }
}
