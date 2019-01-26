package Framework;

import com.relevantcodes.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.util.LinkedHashMap;



public class TestListner extends TestListenerAdapter {


    //protected ExtentTest test;
    public static ThreadLocal<ExtentTest> testing = new ThreadLocal<ExtentTest>()
    {
        @Override
        protected  ExtentTest initialValue()
        {
            return null ;

        }
    };

    public static ThreadLocal<LinkedHashMap<String, ExtentTest>> extentMap = new ThreadLocal<LinkedHashMap<String, ExtentTest>>()
    {
        @Override
        protected LinkedHashMap<String, ExtentTest> initialValue()
        {
            return new LinkedHashMap<String, ExtentTest>();

        }
    };

    public synchronized void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub

        Reporter.log("*******************************************************************",true);
        Reporter.log("TEST CASE : "+result.getName().replace("_", " "),true);
        Reporter.log("*******************************************************************",true);

        if(TestRunner.parentExtentMap== null || TestRunner.parentExtentMap.isEmpty() ||  ! TestRunner.parentExtentMap.containsKey(result.getName().replace("_", " ")))
        {
            System.out.println(TestRunner.parentExtentMap.toString());
   //         TestRunner.parentExtentMap.put(result.getName().replace("_", " "),Framework.extentReports.startTest(result.getName().replace("_", " ")));
        }


    }


    public void onTestSuccess(ITestResult result) {
        if(!extentMap.get().isEmpty())
        {
            for(String key:extentMap.get().keySet())
            {
                System.out.println(extentMap.get());
                System.out.println( key);
                if( key.contains("child"))
                {
                    TestRunner.parentExtentMap.get(result.getName().replace("_", " ")).appendChild((ExtentTest) extentMap.get().get(key));
                }
            }
    //        Framework.extentReports.endTest(TestRunner.parentExtentMap.get(result.getName().replace("_", " ")));
            extentMap.get().clear();
        }
    }

    public void onTestFailure(ITestResult result) {
        if(!extentMap.get().isEmpty())
        {
            for(String key:extentMap.get().keySet())
            {
                System.out.println( key);
                if( key.contains("child"))
                {
                    //extentMap.get().get("parent").appendChild((ExtentTest) extentMap.get().get(key));
                    TestRunner.parentExtentMap.get(result.getName().replace("_", " ")).appendChild((ExtentTest) extentMap.get().get(key));
                }
            }
   //         Framework.extentReports.endTest(TestRunner.parentExtentMap.get(result.getName().replace("_", " ")));
            extentMap.get().clear();
            System.out.println("failed method end");
        }
    }

    public void onTestSkipped(ITestResult result) {
        for(String key:extentMap.get().keySet())
        {
            System.out.println( key);
            if( key.contains("child"))
            {
                //extentMap.get().get("parent").appendChild((ExtentTest) extentMap.get().get(key));
                TestRunner.parentExtentMap.get(result.getName().replace("_", " ")).appendChild((ExtentTest) extentMap.get().get(key));
            }
        }
        //Framework.extentReports.endTest(TestRunner.parentExtentMap.get(result.getName().replace("_", " ")));
        extentMap.get().clear();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

        // TODO Auto-generated method stub

    }

    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }

    public void onFinish(ITestContext testContext) {

		/*   super.onFinish(testContext);

        // List of test results which we will delete later
        List<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();

        // collect all id's from passed test
        Set <Integer> passedTestIds = new HashSet<Integer>();
        for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
            passedTestIds.add(TestUtil.getId(passedTest));
        }

        Set <Integer> failedTestIds = new HashSet<Integer>();
        for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {

            // id = class + method + dataprovider
            int failedTestId = TestUtil.getId(failedTest);

            // if we saw this test as a failed test before we mark as to be deleted
            // or delete this failed test if there is at least one passed version
            if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
                testsToBeRemoved.add(failedTest);
            } else {
                failedTestIds.add(failedTestId);
            }
        }

        // finally delete all tests that are marked
        for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator.hasNext(); ) {
            ITestResult testResult = iterator.next();
            if (testsToBeRemoved.contains(testResult)) {
                iterator.remove();
            }
        }*/





    }


}

