package test;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

public class SearchTest extends CommonAPI {
    @Test
    public void test1() throws InterruptedException {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("java book", Keys.ENTER);
    }
    @Test
    public void test2() throws InterruptedException {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("selenium book", Keys.ENTER);
    }
}
