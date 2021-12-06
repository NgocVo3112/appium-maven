package test.authentication;

import driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import models.components.global.BottomNavComponent;
import models.pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import test.BaseTest;
import test_data.DataObjectBuilder;
import test_data.LoginCreds;

public class LoginTestEx extends BaseTest  {
    private SoftAssert softAssert;

    @Test()
    public void loginWithCorrectCreds() {
        DriverFactory.startAppiumServer();
            // init driver
            AppiumDriver<MobileElement> androidDriver = getDriver();

            // Login page
            LoginPage loginPage = new LoginPage(androidDriver);

            // Bottom Nav Comp
            BottomNavComponent bottomNavComponent = loginPage.BottomNavComp();
            bottomNavComponent.clickOnLoginLabel();

            // Fill Login Form
            loginPage
                    .inputUsername("teo@sth.com")
                    .inputPassword("123244345")
                    .clickOnLoginBtn();

            // Verification
            String actualLoginMsg = loginPage.LoginDialogComponent().msgTitle();
            softAssert.assertEquals(actualLoginMsg,"success", "ERR - Login msg title incorrect");
            System.out.println(actualLoginMsg);

    }
}
