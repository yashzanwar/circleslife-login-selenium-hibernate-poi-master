package TestCases;

import Data.AccountData;
import Data.AssertData;
import Data.DeliveryDetails;
import Data.EnvironmentParameterData;
import Framework.CustomSoftAssert;
import Framework.FrameWork;
import Framework.TestListner;
import Pages.AccountPage;
import Pages.BuyPlan;
import Pages.MyAccount;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.Properties;

import static Framework.FrameWork.environmentURL;


public class Z_SanitySuite {
    FrameWork framework = new FrameWork();
    String ClassName = this.getClass().getSimpleName();
    String PackageName = this.getClass().getPackage().getName();
    Properties properties = System.getProperties();
    AssertData SanAssertData = new AssertData("Checklist", "Sheet1", "AutomationResult", "SubElements",
            properties.getProperty("BUILD_URL") + "testngreports/" + PackageName + "/" + ClassName + "/");

    @SuppressWarnings("deprecation")
    @BeforeClass(alwaysRun = true)
    public void preSetupFortest() throws Throwable {
        String resultField = System.getProperty("ResultFieldName");
        if (resultField != null) {
            SanAssertData = new AssertData("Checklist", "Sheet1", resultField, "SubElements",
                    properties.getProperty("BUILD_URL") + "testngreports/" + PackageName + "/" + ClassName + "/");
        }
        EnvironmentParameterData EnvironmentData = framework.getData(EnvironmentParameterData.class, "Web");
        environmentURL = EnvironmentData.getBaseurl();


    }


    @Test(groups = {"sanity","Local"})
    public void Login_With_UserName() throws  Throwable {

        AccountData Login = framework.getData(AccountData.class, "loginset");
        DeliveryDetails deliveryDetails = framework.getData(DeliveryDetails.class,"valid");
        WebDriver browser = framework.getBrowser();
        CustomSoftAssert customSoftAssert = new CustomSoftAssert(SanAssertData, browser);
        try {
            browser.get(environmentURL);
            AccountPage accountPage = new AccountPage(browser);
            String name = accountPage.LoginWithUserName(Login);
            MyAccount myAccount = new MyAccount(browser);
            myAccount.navigateToMyAccount();
            customSoftAssert.assertTrue(myAccount.getFirstName().toLowerCase().contains(name.toLowerCase()),"TCID1");
            BuyPlan buyPlan = new BuyPlan(browser);
            buyPlan.buyAPlan();
            buyPlan.fillDeliveryInfo(deliveryDetails);
            buyPlan.pay();
            customSoftAssert.assertTrue(buyPlan.isCheckoutPageOpened(),"TCID3");

        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            browser.quit();
        }
    }

    @Test(groups = {"sanity"})
    public void Check_New_Registration() throws Throwable {
        AccountData Login = framework.getData(AccountData.class, "newlogin");
        WebDriver browser = framework.getBrowser();
        CustomSoftAssert customSoftAssert = new CustomSoftAssert(SanAssertData, browser);
        try {
            browser.get(environmentURL);
            AccountPage accountPage = new AccountPage(browser);
            String name = accountPage.RegisterNewAccount(Login);
            MyAccount myAccount = new MyAccount(browser);
            myAccount.navigateToMyAccount();
            customSoftAssert.assertTrue(myAccount.getFirstName().toLowerCase().contains(name.toLowerCase()),"TCID2");
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            browser.quit();
        }
    }

}
