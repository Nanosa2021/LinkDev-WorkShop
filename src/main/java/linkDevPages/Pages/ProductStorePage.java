package linkDevPages.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.LogStatus;

import linkDEV.Bases.PageBase;

public class ProductStorePage extends PageBase{
	WebElement secondElement;
	private WebElement addToCartButton;
	String addToCartXpath = "//a[contains(text(),'Add to cart')]";
	
	
	public ProductStorePage(WebDriver driver)
	{	
		setDriver(driver);
		//wait =new WebDriverWait(getDriver(), 200) ;
	}
	
	
	public void addToCart()
	{
		addToCartButton = findElementByDynamicXpath(driver,addToCartXpath);
		addToCartButton.click();
	}

	public String getAlertText() throws InterruptedException
	{
		Thread.sleep(2000);
		System.out.println("text in the alert: " + driver.switchTo().alert().getText());
		return driver.switchTo().alert().getText();
	}
	
	public void confirmAlert() throws InterruptedException
	{
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
	}
	
	
}
