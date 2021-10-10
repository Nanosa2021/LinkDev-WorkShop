package linkDev.Test;

import java.lang.reflect.Method;
import java.text.ParseException;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.LogStatus;

import linkDevPages.Pages.HomePage;
import linkDevPages.Pages.LoginPage;
import linkDevPages.Pages.ProductStorePage;
import linkDEV.Bases.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class ScenarioOne extends TestBase{


	Logger logger = Logger.getLogger(ScenarioOne.class);
	LoginPage loginPage;
	HomePage  homePage;
	ProductStorePage productStore;
	String userName = properties.getProperty("userName");
	String passsword = properties.getProperty("password");
	String logoutButtonID = "logout2";
	String firstProductToBeAdded = properties.getProperty("firstProductToBeAdded");
	int product2ToBeAddedFirstTime = Integer.parseInt(firstProductToBeAdded);
	String productAddedAlerMessage =properties.getProperty("productAddedAlerMessage"); 
  

    @BeforeTest
	public void beforeTest(XmlTest xmlTest) throws Exception {
		//logger.info("Login");
		test = extent.startTest((this.getClass().getSimpleName()+" :: " + xmlTest.getName()), xmlTest.getName());
		test.assignAuthor("Nancy Emad");
		test.assignCategory("LinkDev-Exam");
		test.log(LogStatus.PASS,  properties.getProperty("browser")+" Browser Launched Successfully" );
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		openPage("StoreURL");
		loginPage = new LoginPage(driver);
		homePage  = new HomePage(driver);
		productStore = new ProductStorePage(driver);
		loginPage.waitForPageLoad();
		driver.manage().window().maximize();
		test = extent.startTest((this.getClass().getSimpleName()+" :: " + method.getName()), method.getName());
		test.assignAuthor("Nancy Emad");
		test.assignCategory("LinkDev-Exam");
	}

	@Test
	public void ScenarioOne() throws ParseException, InterruptedException
	{
		
		// This Scenario is to do the login Successfully 
		// I added product Number 2  to the cart dynamically (meaning that if the second product name and price changed still it will be selected )
		logger.info("login started " );
		loginPage.login(userName,passsword);
		Assert.assertTrue(loginPage.isElementDisplayed(logoutButtonID));
		homePage.selectProduct(product2ToBeAddedFirstTime);
		
		
		productStore.addToCart();
		String alertString = productStore.getAlertText();
		Assert.assertEquals(alertString,productAddedAlerMessage);
		productStore.confirmAlert();
		
		test.log(LogStatus.PASS, "Screenshot below Shown that" + product2ToBeAddedFirstTime + "product Added : " + test.addScreenCapture(takeScreenShot(" ")));
		test.log(LogStatus.PASS, "Adding second product is done successfully ");
		logger.info("Scenario one is done successfully " );
		
	}
	
	@AfterMethod
	public void afterMethod() {
	   logout();
       closeBrowser();		
       extent.endTest(test);
       extent.flush();
	}
	
}
