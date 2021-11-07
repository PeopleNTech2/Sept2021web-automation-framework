package test;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.HomePage;
import utility.DataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchTest extends CommonAPI{
    //@Test
    public void searchJavaBookTest() {
        driver.getTitle();
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.searchItem("java book");
    }
    //@Test
    public void searchSeleniumBookTest() throws InterruptedException {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.searchItem("selenium book");
    }
    //@Test
    public void dropdownOptions(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        for (String option: homePage.getSearchDropdownOptionsText()){
            System.out.println(option);
        }
    }

    //@Test
    public void selectDropdownElement(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.selectDropdownElement("Alexa Skills");
    }

    //@Test
    public void searchItemsOneAfterOther(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        List<String> itemToSearch = new ArrayList<>();
        itemToSearch.add("laptop");
        itemToSearch.add("ps5");
        itemToSearch.add("shoes");
        itemToSearch.add("bags");
        itemToSearch.add("mouse");

        for (String item: itemToSearch) {
            homePage.searchItem(item);
            homePage.clearSearchField();
        }
    }
    @Test
    public void searchMultipleItems() throws IOException {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        DataReader dataReader = new DataReader();
        String[] itemToSearch = dataReader.colReader("C:\\Users\\PIIT_NYA\\IdeaProjects\\web-automation-framework\\Amazon\\data\\items.xls", 1);

        for (String item: itemToSearch) {
            homePage.searchItem(item);
            homePage.clearSearchField();
        }
    }

}
