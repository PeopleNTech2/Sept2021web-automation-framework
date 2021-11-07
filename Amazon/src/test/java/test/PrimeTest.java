package test;

import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PrimePage;

/**
 * Created by PIIT_NYA on 11/7/2021.
 */
public class PrimeTest extends CommonAPI{
    @Test
    public void tryPrime(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.hoverOverPrime(driver);
        homePage.clickOnTryPrime();
        PrimePage primePage = PageFactory.initElements(driver, PrimePage.class);
        primePage.scrollToChooseYourPlan(driver);
        waitFor(3);
        primePage.clickOnYearlyCheckBox();
        waitFor(3);
    }
}
