package test;

import driver.DriverFactoryEx;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BaseTest {
    private final static List<DriverFactoryEx> driverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactoryEx> driverThread;

    public static void beforeSuite(){
        driverThread = ThreadLocal.withInitial(()-> {
            DriverFactoryEx driverThread = new DriverFactoryEx();
            driverThreadPool.add(driverThread);
            return driverThread;
        });
    }
    public static void afterSuite(){
        for (DriverFactoryEx webDriverThread : driverThreadPool){
            webDriverThread.quitAppiumSection();
        }
    }
    public static AppiumDriver<MobileElement> getDriver(){
        return driverThread.get().getAppiumDriver();
    }
}
