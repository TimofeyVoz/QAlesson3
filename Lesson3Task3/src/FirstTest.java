import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","10");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","D:/Tima/Обучение/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }


     @Test
     public void testTask3(){

         waitForElementAndClick(
                 By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                 "Cannot find Search Wikipedia input",
                 5);

         waitForElementAndSendKeys(
                 By.id( "org.wikipedia:id/search_src_text"),
                 "Java",
                 "Cannot find Search Wikipedia input",
                 5);

        List <WebElement> elements = waitForAllElementsPresent(
                 By.id("org.wikipedia:id/page_list_item_title"),
                 "Cannot find Objects",
                 15);

         if( elements != null && elements.size()>0){

             for(int i = 0; i<elements.size(); i++)
             {
                 WebElement element = elements.get(i);
                 String article_title = element.getAttribute("text");
                 Assert.assertThat("Not every search result contains \"java\"",article_title, CoreMatchers.containsString("Java"));
             }
         }else { Assert.fail("No search results");}
     }

    private List<WebElement> waitForAllElementsPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");

        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }
    private WebElement assertElementHasText(By by, String value, String error_message)
    {
        WebElement element = waitForElementPresent(by,"Cannot find element");
        String article_title = element.getAttribute("text");
        Assert.assertEquals(error_message,value,article_title);
        return element;
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");

        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
         return  waitForElementPresent(by,error_message,5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
       WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
       element.click();
       return element;
    }
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }


    private boolean waitForElementNotPresent (By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.clear();
        return element;
    }


}
