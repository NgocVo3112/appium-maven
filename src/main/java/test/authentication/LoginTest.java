package test.authentication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import models.components.global.BottomNavComponent;
import models.pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.BaseTest;
import test_data.DataObjectBuilder;
import test_data.LoginCreds;
import test_flows.authentication.LoginFlow;

public class LoginTest extends BaseTest {

    @Description("Test login with incorrect Creds")
    @Test(dataProvider = "invalidLoginCredsData", description = "Login test", priority = 1)
    public void loginWithInCorrectCreds(LoginCreds loginCreds) {
        // init driver
        AppiumDriver<MobileElement> androidDriver = getDriver();
        LoginFlow loginFlow = new LoginFlow(androidDriver);
        loginFlow
                .navigateToLoginForm()
                .login(loginCreds)
                .verifyLoginWithIncorrectCreds();
    }

    @Description("Test login with correct Creds")
    @Test(description = "Login test", priority = 2)
    public void loginWithCorrectCreds() {
        String jsonDataFileLocation = "/src/main/resources/test-data/authentication/ValidLoginCreds.json";
        LoginCreds loginCreds = DataObjectBuilder.buildDataObject(jsonDataFileLocation, LoginCreds[].class)[0];
        // init driver
        AppiumDriver<MobileElement> androidDriver = getDriver();
        LoginFlow loginFlow = new LoginFlow(androidDriver);
        loginFlow
                .navigateToLoginForm()
                .login(loginCreds)
                .verifyLoginSuccess();
    }

    @DataProvider
    public LoginCreds[] invalidLoginCredsData() {
        String jsonDataFileLocation = "/src/main/resources/test-data/authentication/InValidLoginCreds.json";
        return DataObjectBuilder.buildDataObject(jsonDataFileLocation, LoginCreds[].class);

    }
}
