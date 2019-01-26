package Framework;

import TestCases.Z_SanitySuite;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.util.HashMap;

public class TestRunner {

    private static final String String = null;
    public static volatile HashMap<String,ExtentTest> parentExtentMap=new HashMap<String,ExtentTest>();
    public static boolean isLive = false;

    public static void main(String[] args) throws Throwable {

        TestListenerAdapter tla = testngPlay();
        if(!tla.getFailedTests().isEmpty())
        {
            String TestGroup = System.getProperty("TestGroup");
            if(TestGroup.contains("production"))
            {
  //              GatewaySMS gateWaySms = new GatewaySMS();
  //              gateWaySms.sendSms();
            }
        }

    }






    /**
     * below method to run testng programmatically
     * @return its returns instance of testlistenerAdapter
     * @throws Throwable
     */
    @SuppressWarnings("deprecation")
    public static TestListenerAdapter testngPlay() throws Throwable {
        try {


            String  TestcaseIds ="Local";

            TestListenerAdapter tla = new TestListenerAdapter();
            TestNG testng = new TestNG();
            TestListner testliten = new TestListner();
            testng.addListener(testliten);
            testng.addListener(tla);

            String TestGroup = System.getProperty("TestGroup");
            if(TestGroup!=null)
            {testng.setGroups(TestGroup);
            }
            else{

                testng.setGroups(TestcaseIds);
            }

            //testng.setGroups(prop.getProperty("TestGroup")); // this will collect the group and will all testcase will be run under same group

                testng.setParallel("methods");
                testng.setDataProviderThreadCount(1);
                testng.setThreadCount(1);//no of parallel testcases can be run is defined by this

            testng.setVerbose(12);
            testng.setTestClasses(new Class[] {DataBaseActivity.class,  Z_SanitySuite.class,FrameWork.class});
            //testng.addListener(tla);
            testng.run();
            if(TestRunner.isLive && ! tla.getFailedTests().isEmpty())
            {
         //       Email email=new Email();
         //       email.sendMail(tla);
            }
            return testliten;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}

