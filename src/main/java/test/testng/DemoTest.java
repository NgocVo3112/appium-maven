package test.testng;
import org.testng.annotations.Test;

public class DemoTest {
    @Test
    public void loginDemoTest(){
        System.out.println("loginCorrectCreds");
    }

    @Test
    public void loginInCorrectDemoTest(){
        System.out.println("loginInCorrectCreds");
    }
}
