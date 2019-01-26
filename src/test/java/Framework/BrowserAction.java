package Framework;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


import java.lang.reflect.Field;
import java.util.List;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static Framework.FrameWork.environmentURL;

public class BrowserAction {




    private static final String Text = null;
    protected WebDriver driver;
    static int DefaultTime = 60;

    public List<WebElement> getWebElements(Locator locator) {
        waitUntilDisplayed(locator,5);
        List<WebElement> webElementList = driver.findElements(locator.getBy());
        if (webElementList.isEmpty()) {
            Reporter.log("Viewed all list of " + locator.getName() + "");
        } else {
            Reporter.log("Unable to view list of " + locator.getName() + "");
        }
        return webElementList;
    }

    protected void EnterValue(Locator locator, String value) {
        WebDriverWait webdriverWait = new WebDriverWait(driver, 60);
        webdriverWait.until(ExpectedConditions.elementToBeClickable(locator.getBy()));
        driver.findElement(locator.getBy()).sendKeys(value);
        Reporter.log("Entered value  '" + value + "' in '" + locator.getName() + "'",true);

    }
    protected void ClearValue(Locator locator) {
        WebDriverWait webdriverWait = new WebDriverWait(driver, 60);
        webdriverWait.until(ExpectedConditions.elementToBeClickable(locator.getBy()));
        driver.findElement(locator.getBy()).clear();
        Reporter.log("Cleared value from '" + locator.getName() + "'",true);
    }

    protected  void wait(int a) throws InterruptedException {
        Thread.sleep(a);
        Reporter.log("Waited for"+a/1000+"sec");
    }





    /**
     * @param webElement
     * @param ExtendedLocator
     * @throws InstantiationException
     * @throws IllegalAccessException
     */




    protected void click(Locator locator) throws InstantiationException, IllegalAccessException {


        WebDriverWait webdriverWait = new WebDriverWait(driver, DefaultTime);
        webdriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
        driver.findElement(locator.getBy()).click();
        Reporter.log("Clicked On " + locator.getName() + "",true);



    }

    protected String getAttribute(WebElement locator,String Attribute){

        String attributevalue= locator.getAttribute(Attribute);


        return attributevalue;


    }

    protected boolean waitUntilDisplayed(Locator locator, int Timeout) {
        driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
        WebDriverWait ww = new WebDriverWait(driver, Timeout);
        try {
            ww.until(ExpectedConditions.elementToBeClickable(locator.getBy()));
            return true;
        } catch (Exception e) {

            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(DefaultTime, TimeUnit.SECONDS);
        }
    }


    }















