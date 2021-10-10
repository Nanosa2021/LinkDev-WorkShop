package linkDEV.Bases;


import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

//import CommonClasses.Property;
import io.github.bonigarcia.wdm.WebDriverManager;
import linkDevPages.Pages.LoginPage;
import net.lightbody.bmp.BrowserMobProxyServer;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

//import net.lightbody.bmp.proxy.ProxyServer;

public class TestBase {

	public static Properties properties;
	public static  WebDriver driver;
	public static String browserType;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static  String log4jConfPath;
	public static BrowserMobProxyServer server;
	PageBase pageBase ;
	
	final   static Logger logger = Logger.getLogger(TestBase.class);
	static {

        
		//System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		System.setProperty("webdriver.ie.driver", "./Drivers/IEDriverServer.exe");
		System.setProperty("webdriver.gecko.driver","./Drivers/geckodriver.exe");

		properties = ReadProperties("./config/config.properties");
		browserType = properties.getProperty("browser");
		log4jConfPath=  properties.getProperty("log4jPropertiesPath");
		PropertyConfigurator.configure(log4jConfPath);
		System.out.println("properties Initialized ");
		extent = new ExtentReports(properties.getProperty("testReportPath"),true);
		extent.loadConfig(new File(properties.getProperty("extentReportConfig")));
		

	}

	public  static Properties ReadProperties(String FilePath) {

		try {
			FileInputStream testProperties = new FileInputStream(FilePath);
			properties = new Properties();
			properties.load(testProperties);
			return properties;
		} catch (FileNotFoundException e) {
			logger.error("Test base Constractor " + e.getMessage());
		} catch (IOException e) {
			logger.error("Test base Constractor " + e.getMessage());
		}
		return properties;
	}


	public static void launchBrowser() throws Exception {
      //  String Browser_Config = Property.getPropertyValue("Browser");
        String Browser_Config = properties.getProperty("browser");
        try {
            if (Browser_Config.equalsIgnoreCase("chrome")) {
               // WebDriverManager.getInstance(CHROME).setup();
               // WebDriverManager.getInstance(CHROME).setup();
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                options.addArguments("--window-size=1024,768");
               // options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
                //driver.manage().window().maximize();
                //driver.manage().deleteAllCookies();
                //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            } else if (Browser_Config.equalsIgnoreCase("firefox")) {
                //WebDriverManager.getInstance(FIREFOX).setup();
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                options.addArguments("--window-size=1024,768");
                //options.addArguments("--start-maximized");
                //driver.manage().window().maximize();
                driver.manage().deleteAllCookies();
               // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            } else {
                throw new Exception("enter valid browser name");
            }

            //return driver;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

	
	@SuppressWarnings("deprecation")
	public static void  openPage(String pageUrl)
	{

		try
		{
			if(browserType.equals("firefox"))

			{
				driver = new FirefoxDriver();
				driver.get(properties.getProperty(pageUrl));
			}
			else if(browserType.equals("IE")){

				InternetExplorerOptions options = new InternetExplorerOptions();
				options.introduceFlakinessByIgnoringSecurityDomains();
				options.requireWindowFocus();
				driver = new InternetExplorerDriver(options);

				driver.get(properties.getProperty(pageUrl));
				driver.get("javascript:document.getElementById('overridelink').click();");
			}

			else {
				System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
				driver = new ChromeDriver();
				// Open the URL
				logger.info("Opening Browser ...");
				logger.info("Open URL = " + pageUrl);
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.navigate().to(properties.getProperty(pageUrl));


			}

		}
		catch (Exception ex) {
			logger.error("Open Browser" + ex.getMessage());
		}

	}

	public static void closeBrowser()
	{

		try {

			logger.info("Closing Browser ...");
			driver.quit();
			//driver.close();
		} catch (Exception e) {
			logger.error("Close Browser" + e.getMessage());
		}
	}
	
	
	public static void logout()
	{
		
		try {
			driver.findElement(By.id("logout2")).click();
			logger.info("Logout ...");
			
		} catch (Exception e) {
			logger.error("Logout" + e.getMessage());
		}
	}
	 public static String takeScreenShot(String screenshotName)  {
		   String path =System.getProperty("user.dir") + properties.getProperty("screenShotsPath") ;
        //below line is just to append the date format with the screenshot name to avoid duplicate names 
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				TakesScreenshot ts = (TakesScreenshot) driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
				          //after execution, you could see a folder "FailedTestsScreenshots" under src folder
				String destination =path+screenshotName+dateName+".png";
				File finalDestination = new File(destination);
				try {
					FileUtils.copyFile(source, finalDestination);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				          //Returns the captured file path
				return destination;
}

}


