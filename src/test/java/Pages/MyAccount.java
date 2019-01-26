package Pages;

import Framework.BrowserAction;
import Framework.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyAccount extends BrowserAction {

    public MyAccount(WebDriver driver) {

        this.driver = driver;
    }

    Locator myAccountButton(){
        return new Locator(By.xpath("//div[text()='MY ACCOUNT']"),"My Account Button");
    }

    Locator userdetails(){
        return new Locator(By.xpath("//div[@class='col-xs-7']//input[@class='form-control']"),"User Details");
    }

    public void navigateToMyAccount() throws IllegalAccessException, InstantiationException, InterruptedException {
        Thread.sleep(5000);
        click(myAccountButton());

    }

    public String getFirstName() throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> elementList = getWebElements(userdetails());
        String FirstName = getAttribute(elementList.get(0),"value");
        return FirstName;
    }
}
