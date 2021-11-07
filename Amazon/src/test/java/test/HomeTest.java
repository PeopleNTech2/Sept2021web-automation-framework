package test;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomeTest extends CommonAPI {
    @Test
    public void titleValidation() throws InterruptedException {
        String title = driver.getTitle();
        Assert.assertEquals(title, "Amazon.com. Spend less. Smile more.", title);
    }
    @Test
    public void checkedIfLogoIsDisplayed() throws InterruptedException {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        Assert.assertTrue(homePage.isLogoDisplaed());
    }

}
