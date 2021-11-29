package test.authentication;

import driver.DriverFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import models.components.global.BottomNavComponent;
import models.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Login {
    private SoftAssert softAssert;

    @BeforeTest
    public void beforeClass(){
        softAssert = new SoftAssert();
    }

    @AfterClass
    public void afterClass(){
        softAssert.assertAll();
    }


    @Test
    public void loginWithCorrectCreds() {
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

            // Verification
            String actualLoginMsg = loginPage.LoginDialogComponent().msgTitle();
            softAssert.assertEquals(actualLoginMsg,"success", "ERR - Login msg title incorrect");
            System.out.println(actualLoginMsg);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DriverFactory.stopAppiumServer();
        }

    }
}
