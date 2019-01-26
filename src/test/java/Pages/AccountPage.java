package Pages;

import Data.AccountData;
import Framework.BrowserAction;
import Framework.Locator;
import com.relevantcodes.extentreports.ReporterType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.util.Random;


public class AccountPage extends BrowserAction {

    public AccountPage(WebDriver driver) {

        this.driver = driver;
    }

    Locator signUpButton(){
        return new Locator(By.xpath("//span[text()='CREATE ACCOUNT']"),"Sign Up Button");
    }

    Locator signIn(){
        return new Locator(By.xpath("//a[text()='Buy']"),"Sign In Button");
    }

    Locator firstName(){
        return new Locator(By.xpath("//input[@name='first_name']"),"First Name");
    }

    Locator lastName(){
        return new Locator(By.xpath("//input[@name='last_name']"),"Last Name");
    }

    Locator createAccount(){
        return new Locator(By.xpath("//button[text()='Create account']"),"Create Account Button");
    }
    Locator emailId(){
        return new Locator(By.xpath("//input[@name='email']"),"email text box");
    }

    Locator password(){
        return new Locator(By.xpath("//input[@name='password']"),"password text box");
    }

    Locator signInButton(){
        return new Locator(By.xpath("//button[text()='Sign In']"),"Sign In Button");
    }

    public String LoginWithUserName(AccountData accountData) throws IllegalAccessException, InstantiationException, InterruptedException {
        try {
            click(signIn());
            Thread.sleep(1500);
            EnterValue(emailId(), accountData.getUsername());
            EnterValue(password(), accountData.getPassword());
            click(signInButton());
            return accountData.getFirstName();
        }catch (Throwable e){
          e.printStackTrace();
          return "Nothing";
        }
    }

    public String RegisterNewAccount(AccountData accountData) throws IllegalAccessException, InstantiationException, InterruptedException {
        Random random = new Random(System.currentTimeMillis());
        click(signIn());
        click(signUpButton());
        wait(1500);
        EnterValue(firstName(), accountData.getFirstName());
        EnterValue(lastName(), accountData.getLastName());
        EnterValue(emailId(),accountData.getFirstName().concat(String.valueOf(random.nextInt()+"@circles.life")));
        EnterValue(password(), accountData.getPassword());
        click(createAccount());
        return accountData.getFirstName();
    }
}
