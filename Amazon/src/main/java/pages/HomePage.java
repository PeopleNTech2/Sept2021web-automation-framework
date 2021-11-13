package pages;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PIIT_NYA on 11/7/2021.
 */
public class HomePage extends CommonAPI{

    @FindBy (id = "nav-logo-sprites")
    WebElement logo;
    @FindBy (id = "twotabsearchtextbox")
    WebElement searchField;
    @FindBy (xpath = "//select[@class='nav-search-dropdown searchSelect nav-progressive-attrubute nav-progressive-search-dropdown']/option")
    List<WebElement> dropdownOptions;
    @FindBy (css = "#searchDropdownBox")
    WebElement searchDropdownElement;
    @FindBy (css = "#nav-link-prime")
    WebElement primeLinkMenu;
    @FindBy (xpath = "//a[contains(text(),'Try Prime')]")
    WebElement tryPrimeElement;

    public WebElement getLogo() {
        return logo;
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public List<WebElement> getDropdownOptions() {
        return dropdownOptions;
    }

    public WebElement getSearchDropdownElement() {
        return searchDropdownElement;
    }

    public WebElement getPrimeLinkMenu() {
        return primeLinkMenu;
    }

    public WebElement getTryPrimeElement() {
        return tryPrimeElement;
    }

    public boolean isLogoDisplaed(){
        return getLogo().isDisplayed();
    }
    public void searchItem(String str){
        typeEnter(getSearchField(), str);
    }
    public void selectDropdownElement(String value){
        selectDropdownElement(getSearchDropdownElement(), value);
    }
    public List<String> getSearchDropdownOptionsText(){
        List<String> list = new ArrayList<>();
        List<WebElement> elemets = getDropdownOptions();
        for (WebElement element : elemets) {
            list.add(element.getText());
        }
        return list;
    }
    public void clearSearchField(){
        clearTextField(getSearchField());
    }
    public void hoverOverPrime(WebDriver driver){
        hoverOver(driver, getPrimeLinkMenu());
    }
    public void clickOnTryPrime(){
        clickOn(getTryPrimeElement());
    }
}
