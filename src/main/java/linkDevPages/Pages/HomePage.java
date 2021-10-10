package linkDevPages.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.internal.collections.Pair;
import com.relevantcodes.extentreports.LogStatus;

import linkDEV.Bases.PageBase;

public class HomePage extends PageBase{
	List <WebElement> listProductsNames ;
	List <WebElement> listProductsPrices ;
	WebElement productElementName;
	WebElement productElementPrice;
	String productName;
	String productPrice;
	String addToCartXpath = "//a[contains(text(),'Add to cart')]";
	String productListXpath = "//div[@class='card-block']";
	
	
	public HomePage(WebDriver driver)
	{	
		setDriver(driver);
		//wait =new WebDriverWait(getDriver(), 200) ;
	}
	
	public Pair<String , Integer> selectProduct(int productOrder) throws InterruptedException
	{
	// this function is to select product by providing the product list and the order of the product which need to be selected / added 
	// this function reterning a pair of Name and price od the product 
		
      listProductsNames=driver.findElements(By.xpath(productListXpath+"/h4/a"));
      listProductsPrices=driver.findElements(By.xpath(productListXpath+"/h5"));
      
      int listSize = listProductsNames.size();
      //System.out.println("List size = "+listSize);
      
     // the below is to get the name and price of the selected item 
      productElementName = listProductsNames.get(productOrder-1);
      productName = productElementName.getText();
      productElementPrice = listProductsPrices.get(productOrder-1);
      productPrice = productElementPrice.getText();
      
     // the below lines is just to convert the price to valid Integer  
      String productPriceWithoutCurrency = productPrice.replace("$", ""); // strNew is 
     // System.out.println("productPriceWithoutCurrency = " +productPriceWithoutCurrency);
      int price = Integer.parseInt(productPriceWithoutCurrency);
      //System.out.println("productPriceWithoutCurrencyIntger = " +price);
    
      // the below is to display product Name and product price then click on the item
     System.out.println("Product Name is:  "+ productName);
     System.out.println("Product Price is:  "+ productPrice);
     click(productElementName);
     
     Pair<String, Integer> simplePair = new Pair<String, Integer>(productName, price);
    	
	return simplePair;
	}
	
	public void navigateTo(String mainMenuWord) throws InterruptedException
	{
		Thread.sleep(2000);
		WebElement mainMenuItem = findElementByDynamicXpath(driver,"//a[contains(text(),'"+mainMenuWord+"')]");
		Thread.sleep(1000);
		mainMenuItem.click();
	}
}
