package test;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchTest extends CommonAPI {
    //@Test
    public void test1() throws InterruptedException {
        typeAndEnter("//*[@id='twotabsearchtextbox']", "java book");
    }
    //@Test
    public void test2() throws InterruptedException {
        typeAndEnter("#twotabsearchtextbox", "selenium book");
    }
    //@Test
    public void dropdownOptions(){
        List<String> list = new ArrayList<>();
        List<WebElement> elemets = driver.findElements(By.xpath("//select[@class='nav-search-dropdown searchSelect nav-progressive-attrubute nav-progressive-search-dropdown']/option"));
        for (WebElement element : elemets) {
            list.add(element.getText());
        }
        System.out.println(list);
    }

//    @Test
    public void selectDropdownElement(){
        WebElement dropdown = driver.findElement(By.cssSelector("#searchDropdownBox"));
        Select sel = new Select(dropdown);
        sel.selectByVisibleText("Alexa Skills");
    }

    @Test
    public void searchItemsOneAfterOther(){
        List<String> itemToSearch = new ArrayList<>();
        itemToSearch.add("laptop");
        itemToSearch.add("ps5");
        itemToSearch.add("shoes");
        itemToSearch.add("bags");
        itemToSearch.add("mouse");

        for (String item: itemToSearch) {
            typeAndEnter("*[id='twotabsearchtextbox']", item);
            clear("#twotabsearchtextbox");

        }
    }

}
