package Framework;

import Data.AssertData;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class CustomSoftAssert extends SoftAssert {
    GoogleSheets googleSheet=null;
    SpreadsheetEntry Worksheet = null;
    String Sheetname = null;
    URL listFeedUrl=null;
    String ResultFieldName = null;
    WebDriver webdriver = null;
    FrameWork framework = new FrameWork();
    String FieldName = null;
    String URL = null;


    public CustomSoftAssert(AssertData assertData,WebDriver driver) throws Throwable, Throwable, Throwable
    {
        System.out.println(assertData.getWorkSheetName());
        Sheetname = assertData.getSheetName();
        googleSheet = new GoogleSheets();
        Worksheet = googleSheet.getSpreadSheet(assertData.getWorkSheetName());
        listFeedUrl = googleSheet.getFeedFromSheet(Worksheet, Sheetname);
        ResultFieldName = assertData.getResultFiledName();
        webdriver = driver;
        FieldName = assertData.getTestcaseDesc();
        URL = assertData.getJenKinsURL()+ Thread.currentThread().getStackTrace()[2].getMethodName();

    }

    @Override
    public void assertTrue(boolean condition, String message) {
        // TODO Auto-generated method stub
        //super.assertFalse(!condition, message);
        super.assertTrue(condition, message);



    }


    @Override
    public void onAssertFailure(IAssert<?> assertCommand,AssertionError ex) {
        String CaseID = assertCommand.getMessage();


        try {

            //googleSheet.updateResultField(CaseID, Worksheet,Sheetname, "Fail",ResultFieldName);
            if(URL==null)
            {
                URL=" ";
            }
            googleSheet.updateResultField(CaseID, Worksheet,Sheetname, URL ,ResultFieldName);
            String TestCaseDescription = googleSheet.getFieldValue(CaseID, Worksheet,Sheetname,  FieldName);
            if(TestRunner.isLive)
            {
                TestCaseDescription="";
            }
            //googleSheet.updateRemarkField(CaseID, Worksheet,Sheetname, ex.getLocalizedMessage());
            Reporter.log("<p><font size=\"3\" color=\"red\">"+TestCaseDescription+"--"+CaseID+"--Failed\"</font></p>");
            if(! Sheetname.equalsIgnoreCase("APIData"))
            {
                String Base64 = framework.captureScreenshot(webdriver);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        // TODO Auto-generated method stub
        super.onAssertFailure(assertCommand);
    }

    @Override
    public void onAssertSuccess(IAssert<?> assertCommand) {
        String CaseID = assertCommand.getMessage();

        try {
            googleSheet.updateResultField(CaseID, Worksheet,Sheetname, "Pass",ResultFieldName);
            String TestCaseDescription = googleSheet.getFieldValue(CaseID, Worksheet,Sheetname,  FieldName);
            Reporter.log("<p><font size=\"3\" color=\"green\">"+TestCaseDescription+"--"+CaseID+"--Passed\"</font></p>");
            if(TestRunner.isLive)
            {
                TestCaseDescription="";
            }
  //         TestListner.testing.get().log(LogStatus.PASS, ""+TestCaseDescription+"--"+CaseID+" Passed !");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub
        super.onAssertSuccess(assertCommand);



    }

}

