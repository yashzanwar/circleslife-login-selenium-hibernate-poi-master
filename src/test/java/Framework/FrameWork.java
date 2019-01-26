package Framework;

import Data.EnvironmentParameterData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.relevantcodes.extentreports.ExtentReports;





/**
 * @author nevil
 *
 */
public class FrameWork {

    public static ExtentReports extentReports;
       public static String environmentURL;


    static volatile SessionFactory SessionFactory;
    DataBaseActivity dbActivity = new DataBaseActivity();
    public WebDriver getBrowser() throws Throwable {

		/* DesiredCapabilities caps = new DesiredCapabilities();
		    caps.setCapability("browser", "chrome");
		    caps.setCapability("browserstack.debug", "true");
		    caps.setCapability("build", "First build");

		    WebDriver driver = new RemoteWebDriver(new URL(URL),caps);*/
        String browsername;
        EnvironmentParameterData environmentData = getData(EnvironmentParameterData.class, "Web");
       /* PropertyConfiguration propertyConfig = new PropertyConfiguration();
        Properties pf = propertyConfig.getInstance();
        browsername = pf.getProperty("BrowserName");*/
        browsername = environmentData.getBrowsername();
        WebDriver driver = null;
        if(browsername.equalsIgnoreCase("firefox"))
        //if(true)
        {/****commented***/
            File file = new File("src/test/resources/geckodriver.exe").getCanonicalFile();
            System.setProperty("webdriver.gecko.driver", file.getAbsolutePath().replace("\\", "\\\\"));
            FirefoxProfile ffprofile = new FirefoxProfile();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            ffprofile.setAcceptUntrustedCertificates(true);
            capabilities.setCapability("acceptInsecureCerts",true);
            ffprofile.setAssumeUntrustedCertificateIssuer(true);
            ffprofile.setPreference("dom.webnotifications.enabled", false);
            ffprofile.setPreference("security.insecure_field_warning.contextual.enabled",false);
            capabilities.setCapability(FirefoxDriver.PROFILE, ffprofile);
            driver = new FirefoxDriver(capabilities);
            //	driver.manage().window().maximize();

        }
        if(browsername.equalsIgnoreCase("chrome"))

        {
            File file = new File("src/test/resources/chromedriver").getCanonicalFile();
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath().replace("\\", "\\\\"));
            System.out.println(file.getAbsolutePath().replace("\\", "\\\\"));
            try {
                ChromeOptions options = new ChromeOptions();
                //	options.addArguments("--disable-extensions");
                driver = new ChromeDriver(options);

            } catch (Exception e) {
                e.printStackTrace();
            }
            driver.manage().window().maximize();

        }

        if(browsername.equalsIgnoreCase("mobile"))
        {
            File file = new File("src/test/resources/chromedriver").getCanonicalFile();
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath().replace("\\", "\\\\"));
            System.out.println(file.getAbsolutePath().replace("\\", "\\\\"));
            try
            {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 6.0; HTC One M9 Build/MRA58K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.98 Mobile Safari/537.36");
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Dimension d=new Dimension(500, 700);
            driver.manage().window().setSize(d);

        }
        return driver;
    }

     public <T> T getData(Class clazz, String ID) {
        List<T> listOfhibernateData = new ArrayList<T>();
        try {
            if (SessionFactory == null) {
                SessionFactory = getSessionInstance();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Session session = SessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(clazz);
        criteria.add(Restrictions.like("testcaseid", ID));
        listOfhibernateData = criteria.list();
        tx.commit();
        session.flush();
        session.close();


        return listOfhibernateData.get(0);

    }

    private SessionFactory getSessionInstance() throws Throwable {
        String path = new File("src/test/resources/hibernate.cfg.xml").getCanonicalPath();
        return new Configuration().configure(new File(path).getCanonicalFile()).buildSessionFactory();
    }

    public String captureScreenshot(WebDriver browser) throws IOException {
        File scrFile = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(new File("test-output").getAbsolutePath() + File.separator
                + Reporter.getCurrentTestResult().getMethod().getMethodName() + ".jpg"));
        String scrBase64Image = ((TakesScreenshot) browser).getScreenshotAs(OutputType.BASE64);
        Reporter.log(
                "<br> <img src=\"data:image/png;base64," + scrBase64Image + "\" height=\"700\" width=\"800\"> <br>");
        //System.out.println("<img src=�data:image/png;base64," + scrBase64Image + "�>");
        Reporter.log(browser.getCurrentUrl());
        return scrBase64Image;
    }

    public String getEnvironment() throws Throwable {
    //    PropertyConfiguration pf = new PropertyConfiguration();
    //    Properties prop = pf.getInstance();
		/* environment = prop.getProperty("environmentName"); */
        return "tets";
    }

    @BeforeSuite(alwaysRun= true)
    public void beforeSuite() throws Throwable
    {
        File fileres = new File("src/test/resources/ExtentReport.html").getCanonicalFile();
        File file = new File(fileres.getAbsolutePath().replace("\\", "\\\\"));
        file.createNewFile();

        extentReports = new ExtentReports(fileres.getAbsolutePath().replace("\\", "\\\\"),true);
        SessionFactory = getSessionInstance();
        dbActivity.StartConnection();
		/*   List<XSSFSheet> sheetlist = dbActivity.getSheetsFromExcel();
	  for(XSSFSheet sheet:sheetlist)
	  {
		  String sheetname = sheet.getSheetName();
		  dbActivity.dropTable(sheetname);

	  }*/
        dbActivity.dropTableFromGsheet();
        dbActivity.createTableFromGSpreadsheets();
        //dbActivity.dumpDataIntoDatabase();
        GoogleSheets  gs = new 	GoogleSheets();

        com.google.gdata.data.spreadsheet.SpreadsheetEntry Worksheet = GoogleSheets.getSpreadSheet("automation Regression Coverage");
        com.google.gdata.data.spreadsheet.SpreadsheetEntry Worksheet1 = GoogleSheets.getSpreadSheet("Automation Data");
        com.google.gdata.data.spreadsheet.SpreadsheetEntry Worksheet2 = GoogleSheets.getSpreadSheet("Sanity Checklist");
        gs.clearField(Worksheet,"DeskTop" ,"Result");
        gs.clearField(Worksheet1,"APIData" ,"Result");
        //gs.clearField(Worksheet2,"Desktop" ,"AutomationResult");
        //gs.clearField(Worksheet2,"Msite" ,"AutomationResult");
    }

    @AfterSuite(alwaysRun = true)
    public void SessionTearDown() throws Throwable{

        extentReports.flush();
        //extentReports.close();
        SessionFactory.close();
        dbActivity.dropTableFromGsheet();
        //dbActivity.afterTest();

        System.out.println(SessionFactory.isClosed());

        //to copy html report as backup
        File dest = null;
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("dd_MM_HH_mm");
            Date date = new Date();
            File source =  new File("src/test/resources/ExtentReport.html");
            dest=new File("src/test/resources/ExtentReports");

            FileUtils.copyFileToDirectory(source,  dest);
            File oldfile =new File("src/test/resources/ExtentReports/ExtentReport.html");
            File newfile =new File("src/test/resources/ExtentReports/Report_"+dateFormat.format(date)+".html");
            oldfile.renameTo(newfile);
        }


        catch (Exception e) {
            e.printStackTrace();
        }

        try{
            File[] files = dest.listFiles();

            Arrays.sort(files, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.compare(f2.lastModified(),f1.lastModified());
                }
            });

            if(files.length>50)
            {
                for(int i=files.length-1;i>=50;i--)
                {
                    files[i].delete();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }



}

