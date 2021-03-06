package driver;

import caps.MobileCapabilityTypeEx;
import flags.AndroidServerFlagEx;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactoryEx {
    private static AppiumDriverLocalService appiumServer;
    private static AppiumDriver<MobileElement> appiumDriver;

    public AppiumDriver<MobileElement> getAppiumDriver() {
        if (appiumDriver == null)
            appiumDriver = initAppiumDriver();
        return appiumDriver;
    }

    public AppiumDriver<MobileElement> getAppiumDriver(String udid, String port, String systemPort) {
        if (appiumDriver == null)
            appiumDriver = initAppiumDriver(udid, port, systemPort);
        return appiumDriver;
    }

    private AppiumDriver<MobileElement> initAppiumDriver() {
        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.withArgument(AndroidServerFlagEx.ALLOW_INSECURE, "chromedriver_autodownload");
        appiumServiceBuilder.withIPAddress("127.0.0.1").usingAnyFreePort();
        appiumServer = AppiumDriverLocalService.buildService(appiumServiceBuilder);
        appiumServer.start();

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.AUTOMATION_NAME, "uiautomator2");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.UDID, "NFDIVKMV99999999");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.APP_PACKAGE, "com.wdiodemoapp");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.APP_ACTIVITY, "com.wdiodemoapp.MainActivity");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.NEW_COMMAND_TIMEOUT, 120);
        appiumDriver = new AndroidDriver<>(appiumServer.getUrl(), desiredCapabilities);
        appiumDriver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
        return appiumDriver;
    }

    private AppiumDriver<MobileElement> initAppiumDriver(String udid, String port, String systemPort) {
//        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
//        appiumServiceBuilder.withArgument(AndroidServerFlagEx.ALLOW_INSECURE, "chromedriver_autodownload");
//        appiumServiceBuilder.withIPAddress("127.0.0.1").usingAnyFreePort();
//        appiumServer = AppiumDriverLocalService.buildService(appiumServiceBuilder);
//        appiumServer.start();

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.AUTOMATION_NAME, "uiautomator2");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.UDID, udid);
        desiredCapabilities.setCapability("systemPort", Integer.parseInt(systemPort));
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.APP_PACKAGE, "com.wdiodemoapp");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.APP_ACTIVITY, "com.wdiodemoapp.MainActivity");
        desiredCapabilities.setCapability(MobileCapabilityTypeEx.NEW_COMMAND_TIMEOUT, 120);
        String localAppium = System.getenv("localAppium");
        String hub = System.getProperty("hub");
        String targetServer = null;
        if(localAppium!=null){
            targetServer = localAppium + "/wd/hub";
        }
        else if (hub != null){
            targetServer = hub + ":4444/wd/hub";
        }
        else
            throw new IllegalArgumentException("You need provide localAppium/hub");
        try {
            URL appiumServerPath = new URL(targetServer);
            // URL appiumServerPath = new URL("http://192.168.1.3:4444/wd/hub");
            appiumDriver = new AndroidDriver<>(appiumServerPath, desiredCapabilities);
            // appiumDriver = new AndroidDriver<>(appiumServer.getUrl(), desiredCapabilities);
            appiumDriver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appiumDriver;
    }

    public static void startAppiumServer() {
        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.withArgument(AndroidServerFlagEx.ALLOW_INSECURE, "chromedriver_autodownload");
        appiumServiceBuilder.withIPAddress("127.0.0.1").usingAnyFreePort();
        appiumServer = AppiumDriverLocalService.buildService(appiumServiceBuilder);
        appiumServer.start();

    }

    public void quitAppiumSection() {
        if (appiumDriver != null) {
            appiumDriver.quit();
            appiumDriver = null;

            // stopAppiumServer();
        }
    }

    public static void stopAppiumServer() {
        String killNodeWindowsCmd = "taskkill /F /IM node.exe";
        String killNodeLinuxCmd = "killall node";
        String currentOS = System.getProperty("os.name").toLowerCase();
        String killNodeCmd = currentOS.startsWith("windows") ? killNodeWindowsCmd : killNodeLinuxCmd;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(killNodeCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
