package pages;

import base.CommonAPI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by PIIT_NYA on 11/7/2021.
 */
public class PrimePage extends CommonAPI{
    @FindBy(xpath = "//h1[contains(text(),'Choose your plan')]")
    WebElement chooseYourPlan;
    @FindBy(xpath = "//*[contains(text(),'Best value')]/../../label/div/div")
    WebElement yearlyPlanCheckbox;

    public WebElement getChooseYourPlan() {
        return chooseYourPlan;
    }

    public WebElement getYearlyPlanCheckbox() {
        return yearlyPlanCheckbox;
    }

    public void scrollToChooseYourPlan(WebDriver driver){
        scrollToView(getChooseYourPlan(), driver);
    }
    public void clickOnYearlyCheckBox(){
        clickOn(getYearlyPlanCheckbox());
    }
}
