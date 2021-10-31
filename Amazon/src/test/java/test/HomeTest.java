package test;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends CommonAPI {
    @Test
    public void test1() throws InterruptedException {
        String title = driver.getTitle();
        Assert.assertEquals(title, "Amazon.com. Spend less. Smile more.", title);
    }
    @Test
    public void test2() throws InterruptedException {
        boolean checkLogo = driver.findElement(By.id("nav-logo-sprites")).isDisplayed();
        Assert.assertTrue(checkLogo);
    }

}
