package test.api_learning;

import driver.DriverFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwipeToOpenNotification {
    public static void main(String[] args) {
        DriverFactory.startAppiumServer();
        try {
            AndroidDriver<MobileElement> androidDriver = DriverFactory.getAndroidDriver();

            // get mobile window size
            Dimension windowSize = androidDriver.manage().window().getSize();
            int screenHeight = windowSize.getHeight();
            int screenWidth = windowSize.getWidth();

            // Calculate touch point
            int xStartPoint = 50 * screenWidth / 100;
            int xEndPoint = xStartPoint;
            int yStartPoint = 0;
            int yEndPoint = 80 * screenHeight / 100;

            // Convert to point option
            PointOption startPoint = new PointOption().withCoordinates(xStartPoint, yStartPoint);
            PointOption endPoint = new PointOption().withCoordinates(xStartPoint, yEndPoint);

            // Perform touch action
            TouchAction touchAction = new TouchAction(androidDriver);

            // Swipe down to open notification
            touchAction
                    .press(startPoint)
                    .waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1)))
                    .moveTo(endPoint)
                    .release()
                    .perform();
            Thread.sleep(2000);

            // get the information of list by getting a list android:id/notification_main_column android:id/title
            List<MobileElement> notificationElems = androidDriver.findElements(By.id("android:id/notification_main_column"));
            if (notificationElems.isEmpty())
                throw new RuntimeException("Notification list is empty");
            Map<String, String> notificationList = new HashMap<>();
            notificationElems.forEach(notification -> {
                String notificationTitle = notification.findElement(By.id("android:id/title")).getText();
                String notificationContent = notification.findElement(By.id("android:id/text")).getText();
                notificationList.put(notificationTitle, notificationContent);
            });

            // Swipe up to close notification
            touchAction
                    .press(endPoint)
                    .waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1)))
                    .moveTo(startPoint)
                    .release()
                    .perform();
            Thread.sleep(2000);
            notificationList.keySet().forEach(key ->System.out.println(key + ":" + notificationList.get(key)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DriverFactory.stopAppiumServer();
        }
    }
}

