package linkDevPages.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.LogStatus;


import linkDEV.Bases.PageBase;

public class Cart extends PageBase{
	WebElement secondElement;
	private WebElement addToCartButton;
	String addToCartXpath = "//a[contains(text(),'Add to cart')]";
	String cartListToBeDeleted = "//tbody[@id='tbodyid']//td//a[contains(text(),'Delete')]";
	List <WebElement> listCartItemsToBeDeleted ;
	String productRows = "//tbody[@id='tbodyid']/tr";
	
	
	public Cart(WebDriver driver)
	{	
		setDriver(driver);
		//wait =new WebDriverWait(getDriver(), 200) ;
	}
	
	public void emptyCart() throws InterruptedException
	{
		Thread.sleep(2000);
		listCartItemsToBeDeleted = driver.findElements(By.xpath(cartListToBeDeleted));
		if (listCartItemsToBeDeleted.size()>0)
		{
		for(int i =0 ; i < listCartItemsToBeDeleted.size();i++)
		{
			driver.findElements(By.xpath(cartListToBeDeleted)).get(0).click();
			Thread.sleep(2000);
		//	driver.navigate().refresh();
			Thread.sleep(2000);
		}
		}
		
	}
	
	public List<WebElement>getRows()
	{
	
		return driver.findElements(By.xpath(productRows));
	}
	
	public int getTotalPrice()
	{
		return Integer.parseInt(findElementById("totalp").getText());
	}
	
	public void placeOrder()
	{
		findElementByDynamicXpath(driver, "//button[@type='button' and contains(text(),'Place Order')]").click();
		
	}
	
	
}
