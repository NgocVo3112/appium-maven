package test.testng;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {

    @BeforeTest
    public void beforeTest(){
        System.out.println("beforeTest");
    }
    @BeforeClass
    public void beforeClass(){
        System.out.println("beforeClass");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("beforeMethod");
    }

    @Test
    public void loginCorrectCreds(){
        System.out.println("loginCorrectCreds");
    }

    @Test
    public void loginInCorrectCreds(){
        System.out.println("loginInCorrectCreds");
    }
}
