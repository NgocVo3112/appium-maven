package test;

import driver.DriverFactoryEx;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BaseTest {
    private final static List<DriverFactoryEx> driverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactoryEx> driverThread;
    private String udid;
    private String port;
    private String systemPort;

    @BeforeTest(alwaysRun = true)
    @Parameters({"udid", "port", "systemPort"})
    public void beforeTest(String udid, String port, String systemPort){
        System.out.println(udid + "|" + port + "|" +systemPort);
        this.udid = udid;
        this.port = port;
        this.systemPort= systemPort;
        // () -> {} anonymous function
        driverThread = ThreadLocal.withInitial(()-> {
            DriverFactoryEx driverThread = new DriverFactoryEx();
            driverThreadPool.add(driverThread);
            return driverThread;
        });
    }
    @AfterTest(alwaysRun = true)
    public void afterTest(){
        driverThread.get().quitAppiumSection();
//        for (DriverFactoryEx webDriverThread : driverThreadPool){
//            webDriverThread.quitAppiumSection();
//        }
    }
    public AppiumDriver<MobileElement> getDriver(){
        return driverThread.get().getAppiumDriver(udid, port, systemPort);
    }
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // 1. Get the test Method name
            String testMethodName = result.getName();

            // 2. Declare the file location
            Calendar calendar = new GregorianCalendar();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DATE);
            int hr = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            String dateTaken = y + "-" + (m + 1) + "-" + d + "-" + hr + "-" + min + "-" + sec;
            String fileLocation = System.getProperty("user.dir") + "/screenshot/" + testMethodName + "_"+ dateTaken + ".png";

            // 3. Declare the file name

            // 4. Save the screenshot to the system
            File screenShot = driverThread.get().getAppiumDriver().getScreenshotAs(OutputType.FILE);

            try {
                FileUtils.copyFile(screenShot, new File(fileLocation));
                Path content = Paths.get(fileLocation);
                try (InputStream is = Files.newInputStream(content)) {
                    Allure.addAttachment(testMethodName, is);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
