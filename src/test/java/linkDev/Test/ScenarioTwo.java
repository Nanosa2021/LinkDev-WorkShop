package linkDev.Test;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.internal.collections.Pair;
import org.testng.xml.XmlTest;
import com.relevantcodes.extentreports.LogStatus;

import com.google.common.base.Verify;
import com.relevantcodes.extentreports.LogStatus;

import linkDevPages.Pages.Cart;
import linkDevPages.Pages.HomePage;
import linkDevPages.Pages.LoginPage;
import linkDevPages.Pages.ProductStorePage;
import linkDEV.Bases.TestBase;

public class ScenarioTwo extends TestBase{


	Logger logger = Logger.getLogger(ScenarioTwo.class);
	LoginPage loginPage;
	HomePage  homePage;
	Cart cart;
	ProductStorePage productStore;
	String userName = properties.getProperty("userName");
	String passsword = properties.getProperty("password");
	String productAddedAlerMessage =properties.getProperty("productAddedAlerMessage");     
	String logoutButtonID = "logout2";
	String firstProductToBeAdded = properties.getProperty("firstProductToBeAdded");
	int product2ToBeAddedFirstTime = Integer.parseInt(firstProductToBeAdded);
	String secondProductToBeAdded = properties.getProperty("SecondProductToBeAdded");
	int product3ToBeAddedSecondTime = Integer.parseInt(secondProductToBeAdded);
	 
	
	Pair<String,Integer> firstProductPair;
	Pair<String,Integer> secondProductPair;
	//Pair<String,Integer> ActualCartPair;
	//Pair<String,Integer> secondActualCartPair;
	List<Pair<String,Integer>> listPair ;
	List<Pair<String,Integer>> listPairActualCart ;
	SoftAssert softAssert = new SoftAssert();
	

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
		cart = new Cart(driver);

		productStore = new ProductStorePage(driver);
		loginPage.waitForPageLoad();
		driver.manage().window().maximize();
		test = extent.startTest((this.getClass().getSimpleName()+" :: " + method.getName()), method.getName());
		test.assignAuthor("Nancy Emad");
		test.assignCategory("LinkDev-Exam");
	}

	@Test
	public void ScenarioTwo() throws ParseException, InterruptedException
	{
		//in this scenario I am just repeated what is happened in scenario 1 in order to elminate dependency testing
		//which is one of the main factors of good automation scripts 
		//so the new part in this script is to add another item to the cart and assert on the added items on the cart (Name and price )
		
		logger.info("Scenario 2 Started" );

		listPair= new ArrayList<Pair<String,Integer>>();
		listPairActualCart= new ArrayList<Pair<String,Integer>>();
		firstProductPair = new Pair<String, Integer>("",1);
		secondProductPair= new Pair<String, Integer>("",1);
	
		loginPage.login(userName,passsword);
		Thread.sleep(1000);
		Assert.assertTrue(loginPage.isElementDisplayed(logoutButtonID));


		// the below is to empty the cart if it contains any old products 
		 homePage.navigateTo("Cart");
		 cart.emptyCart();

       
		homePage.navigateTo("Home");
		firstProductPair= homePage.selectProduct(product2ToBeAddedFirstTime);
		listPair.add(firstProductPair);
		productStore.addToCart();

		String alertString = productStore.getAlertText();
		Assert.assertEquals(alertString, productAddedAlerMessage);
		productStore.confirmAlert();
		
		test.log(LogStatus.PASS, "Before Going to Cart : Screenshot below Shown that product number " + product2ToBeAddedFirstTime + " Added successfully: " + test.addScreenCapture(takeScreenShot(" ")));
		test.log(LogStatus.PASS, "Adding product number " +product2ToBeAddedFirstTime+  " is done successfully ");
		logger.info("Adding second product has been done successfully" );
	


		homePage.navigateTo("Home");

		secondProductPair=homePage.selectProduct(product3ToBeAddedSecondTime);
		listPair.add(secondProductPair);
		productStore.addToCart();

		alertString = productStore.getAlertText();
		Assert.assertEquals(alertString, productAddedAlerMessage);
		productStore.confirmAlert();
        
		test.log(LogStatus.PASS, "Before Going to Cart : Screenshot below Shown that product number " + product3ToBeAddedSecondTime + " Added successfully: " + test.addScreenCapture(takeScreenShot(" ")));
		test.log(LogStatus.PASS, "Adding product number " +product3ToBeAddedSecondTime+  " is done successfully ");
		test.log(LogStatus.PASS, "Adding 2 products are done successfully ");
		logger.info("2 products are added successfully" );

		
		homePage.navigateTo("Cart");
		List<WebElement>Rows = cart.getRows();

		// the below code is to find the Name and Price in the cart and compare them with the stored values on the paired array list which added earlier 		
		int totalPrice=0;
		for(int i =0 ; i < Rows.size() ; i++)
		{

			Thread.sleep(2000);
			String Name = Rows.get(i).findElements(By.xpath("td")).get(1).getText();
			System.out.println("Actual Product Name In Cart"+Name);

			Integer Price = Integer.parseInt(Rows.get(i).findElements(By.xpath("td")).get(2).getText());
			System.out.println("Actual Product Price In Cart "+Price);

            // the below is to assert that what I have added earlier is the same as what is displayed in the cart 
			System.out.println("Actual Name is "+ Name + "And Expected Name is "+listPair.get(i).first() );
			System.out.println("Actual Price is "+ Price + "And Expected Price is "+listPair.get(i).second() );
			softAssert.assertEquals(Name, listPair.get(i).first());
			softAssert.assertEquals(Price, listPair.get(i).second());
			Thread.sleep(2000);
			totalPrice+=Price;

		}
		
		// Assert on the total price is correct 
		System.out.println("Total Price = "+totalPrice);
		int actualTotalPrice = cart.getTotalPrice();
		softAssert.assertEquals(actualTotalPrice, totalPrice);
		
		test.log(LogStatus.PASS, "After Going to cart : Screenshot below Shown that 2 products has been Added and displayed on cart successfully: " + test.addScreenCapture(takeScreenShot(" ")));
		test.log(LogStatus.PASS, "Cart displaying the same added 2 products with the same names and prices  ");
		test.log(LogStatus.PASS, "Note that total price is displayed successfully as total of the two products ");
		logger.info("2 products are displaying on the cart successfully with the same name and price and total price " );
		logger.info("Note that total price is displayed successfully " );
		
	}

	@AfterMethod
	public void afterMethod() { 
		 logout();
	     closeBrowser();	
	     extent.endTest(test);
	     extent.flush();
	}

}
