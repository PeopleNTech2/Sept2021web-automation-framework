package base;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ExtentManager;
import reporting.ExtentTestManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommonAPI {
    public WebDriver driver = null;
    public String browserstack_username = "nacerhadjsaid1";
    public String browserstack_accesskey = "pK4miZ8sp15afqsvGckE";
    public String saucelabs_username = "";
    public String saucelabs_accesskey = "";
    boolean flag = false;

    //ExtentReport
    public static com.relevantcodes.extentreports.ExtentReports extent;

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName().toLowerCase();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }
    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));

        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ExtentTestManager.endTest();
        extent.flush();
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }
        driver.quit();
    }
    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    @Parameters({"useCloudEnv", "cloudEnvName", "os", "os_version", "browserName", "browserVersion", "url"})
    @BeforeMethod
    public void setUp(@Optional("false") boolean useCloudEnv, @Optional("false") String cloudEnvName,
                      @Optional("OS X") String os, @Optional("10") String os_version, @Optional("chrome") String browserName, @Optional("34")
                              String browserVersion, @Optional("https://www.walmart.com") String url) throws IOException {

        if (useCloudEnv == true) {
            if (cloudEnvName.equalsIgnoreCase("browserstack")) {
                getCloudDriver(cloudEnvName, browserstack_username, browserstack_accesskey, os, os_version, browserName, browserVersion);
            } else if (cloudEnvName.equalsIgnoreCase("saucelabs")) {
                getCloudDriver(cloudEnvName, saucelabs_username, saucelabs_accesskey, os, os_version, browserName, browserVersion);
            }
        } else {
            getLocalDriver(os, browserName);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        driver.get(url);
        driver.manage().window().maximize();
    }

    public WebDriver getLocalDriver (@Optional("OS X") String OS, String browserName){
        if (browserName.equalsIgnoreCase("chrome")) {
            if (OS.equalsIgnoreCase("OS X")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/drivers/chromedriver");
            } else if (OS.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/drivers/chromedriver.exe");
            }
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("ff")) {
            if (OS.equalsIgnoreCase("OS X")) {
                System.setProperty("webdriver.gecko.driver", "../Generic/drivers/geckodriver");
            } else if (OS.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.gecko.driver", "../Generic/drivers/geckodriver.exe");
            }
            driver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("ie")) {
            System.setProperty("webdriver.ie.driver", "");
            driver = new InternetExplorerDriver();
        }
        return driver;
    }
    public WebDriver getCloudDriver (String envName, String envUsername, String envAccessKey, String os, String
            os_version, String browserName,
                                     String browserVersion)throws IOException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("browser", browserName);
        cap.setCapability("browser_version", browserVersion);
        cap.setCapability("os", os);
        cap.setCapability("os_version", os_version);
        if (envName.equalsIgnoreCase("Saucelabs")) {
            //resolution for Saucelabs
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ":" + envAccessKey +
                    "@ondemand.saucelabs.com:80/wd/hub"), cap);
        } else if (envName.equalsIgnoreCase("Browserstack")) {
            cap.setCapability("resolution", "1024x768");
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ":" + envAccessKey +
                    "@hub-cloud.browserstack.com/wd/hub"), cap);
        }
        return driver;
    }
    @AfterMethod
    public void afterMethod () {
        driver.quit();
    }

    public void typeAndEnter(String locator, String input){
        try {
            driver.findElement(By.cssSelector(locator)).sendKeys(input, Keys.ENTER);
        }catch(Exception ex1){
            driver.findElement(By.xpath(locator)).sendKeys(input, Keys.ENTER);
        }
    }
    public void clear(String locator){
        try {
            driver.findElement(By.cssSelector(locator)).clear();
        }catch(Exception ex1){
            driver.findElement(By.xpath(locator)).clear();
        }
    }
    public void clickOnCss(String locator){
        driver.findElement(By.cssSelector(locator)).click();
    }
    public void clickOnElement(String locator){
        try {
            driver.findElement(By.cssSelector(locator)).click();
        }catch(Exception ex1){
            try {
                driver.findElement(By.xpath(locator)).click();
            }catch(Exception ex2){
                driver.findElement(By.id(locator)).click();
            }
        }
    }
    //------------------------------------------------------------------------------------------------------------------------
    //generic methods for page factory
    //------------------------------------------------------------------------------------------------------------------------
    public void typeInto(WebElement element, String value){
        element.sendKeys(value);
    }
    public void typeEnter(WebElement element, String str){
        element.sendKeys(str, Keys.ENTER);
    }
    public void selectDropdownElement(WebElement element, String value){
        Select sel = new Select(element);
        try {
            sel.selectByVisibleText(value);
        }catch (Exception e){
            sel.selectByValue(value);
        }

    }
    public void waitFor(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void clearTextField(WebElement element){
        element.clear();
    }
    public void clickOn(WebElement element){
        element.click();
    }
    public void hoverOver(WebDriver driver, WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }
    public void scrollToView(WebElement element, WebDriver driver){
        JavascriptExecutor js = ((JavascriptExecutor)driver);
        js.executeScript("arguments[0].scrollIntoView(true)", element);
    }
    public String  getCurrentPageUrl(){
        String url = driver.getCurrentUrl();
        return url;
    }
    public void navigateBack(){
        driver.navigate().back();
    }
    public void navigateForward(){
        driver.navigate().forward();
    }
    public void navigateToHomeWindow(){
        driver.switchTo().defaultContent();
    }
    public String getWebElementText(WebElement element){
        String st = element.getText();
        return st;
    }
    public List<String> getTextFromListOfWebElements(List<WebElement> list) {
        List<String> items = new ArrayList<String>();
        for (WebElement element : list) {
            items.add(element.getText());
        }
        return items;
    }
    public void okAlert(){
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
    public void cancelAlert(){
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }
    public void iFrameHandle(WebElement element){
        driver.switchTo().frame(element);
    }
    public void getLinks(String linkText){
        driver.findElement(By.linkText(linkText)).findElement(By.tagName("a")).getText();
    }
    public boolean checkSelected(WebElement element){
        if (element.isSelected()){
            flag = true;
        }
        return flag;
    }
    public boolean checkNotSelected(WebElement element){
        if (!element.isSelected()){
            flag = true;
        }
        return flag;
    }
    public boolean checkEnabled(WebElement element){
        if (element.isEnabled()){
            flag = true;
        }
        return flag;
    }
    public boolean checkDisabled(WebElement element){
        if (!element.isEnabled()){
            flag = true;
        }
        return flag;
    }
    public boolean checkDisplayed(WebElement element){
        if (element.isDisplayed()){
            flag = true;
        }
        return flag;
    }
    public boolean checkNotDisplayed(WebElement element){
        if (!element.isDisplayed()){
            flag = true;
        }
        return flag;
    }
    public static WebDriver handleNewTab(WebDriver driver1){
        String oldTab = driver1.getWindowHandle();
        List<String> newTabs = new ArrayList<String>(driver1.getWindowHandles());
        newTabs.remove(oldTab);
        driver1.switchTo().window(newTabs.get(0));
        return driver1;
    }
    public void waitUntilClickAble(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public void waitUntilVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void waitUntilSelectable(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeSelected(element));
    }
    public void captureScreenshot() {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File("screenShots.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenshot(String screenshotName){
        DateFormat df = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
        Date date = new Date();
        df.format(date);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir")+ "/screenshots/"+screenshotName+" "+df.format(date)+".png"));
            System.out.println("Screenshot captured");
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot "+e.getMessage());;
        }
    }
    public static String convertToString(String str){
        String splitString ;
        splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(str), ' ');
        return splitString;
    }

}