package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class CommonAPI {
    public WebDriver driver;
    @BeforeMethod
    public void before(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\PeopleNTech NY Class\\IdeaProjects\\SeleniumProject1\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.amazon.com/");
        driver.manage().window().maximize();
    }
    @AfterMethod
    public void after(){
        driver.close();
    }

    public void typeAndEnter(){

    }
}
