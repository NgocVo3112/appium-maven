package models.components.global;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class BottomNavComponent {
    private final AppiumDriver<MobileElement> appiumDriver;
    private static final By loginLabelSel = MobileBy.AccessibilityId("Login");
    private static final By formsLabelSel = MobileBy.AccessibilityId("Forms");
    private static final By swipeLabelSel = MobileBy.AccessibilityId("Swipe");

    public BottomNavComponent(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public MobileElement loginLabelElem(){
        return appiumDriver.findElement(loginLabelSel);
    }

    public MobileElement formsLabelElem(){
        return appiumDriver.findElement(formsLabelSel);
    }

    public MobileElement swipeLabelElem(){
        return appiumDriver.findElement(swipeLabelSel);
    }
    public void clickOnLoginLabel(){
        appiumDriver.findElement(loginLabelSel).click();
    }
}
