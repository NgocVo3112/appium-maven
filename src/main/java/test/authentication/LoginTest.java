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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import test_data.DataObjectBuilder;
import test_data.LoginCreds;

public class LoginTest {
    private SoftAssert softAssert;

//    @BeforeTest
//    public void beforeClass(){
//        softAssert = new SoftAssert();
//    }
//
//    @AfterClass
//    public void afterClass(){
//        softAssert.assertAll();
//    }


    @Test(dataProvider = "loginCredsData")
    public void loginWithCorrectCreds(LoginCreds loginCreds) {
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
                    .inputUsername(loginCreds.getUsername())
                    .inputPassword(loginCreds.getPassword())
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
    @DataProvider
    public LoginCreds[] loginCredsData(){
        String jsonDataFileLocation = "/src/main/resources/test-data/loginCreds.json";
        return DataObjectBuilder.buildCredObject(jsonDataFileLocation);

    }
}
