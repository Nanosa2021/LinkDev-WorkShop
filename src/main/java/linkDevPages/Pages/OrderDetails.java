package linkDevPages.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.relevantcodes.extentreports.LogStatus;

import linkDEV.Bases.PageBase;

public class OrderDetails extends PageBase{
	WebElement secondElement;
	private WebElement addToCartButton;
	String addToCartXpath = "//a[contains(text(),'Add to cart')]";
	String cartListToBeDeleted = "//tbody[@id='tbodyid']//td//a[contains(text(),'Delete')]";
	List <WebElement> listCartItemsToBeDeleted ;
	
	public OrderDetails(WebDriver driver)
	{	
		setDriver(driver);
		//wait =new WebDriverWait(getDriver(), 200) ;
	}
	
	public void fillOrderDetails(String name , String country , String city , String card , String month , String year) throws InterruptedException
	{
		//Thread.sleep(2000);
		findElementById("name").sendKeys(name);
		findElementById("country").sendKeys(country);
		findElementById("city").sendKeys(city);
		findElementById("card").sendKeys(card);
		findElementById("month").sendKeys(month);;
		findElementById("year").sendKeys(year);
	}
	
	public void purchase()
	{
		findElementByDynamicXpath(driver, "//button[@type='button' and contains(text(),'Purchase')]").click();
	}
	
	public String getPurchaseConfirmation()
	{
		return findElementByDynamicXpath(driver,"//div[@class='sweet-alert  showSweetAlert visible']//h2").getText();
	}
     
	public String getOrderDetails(String orderElement)
	{
		String totalText = findElementByDynamicXpath(driver, "//p[@class='lead text-muted ']").getText();
        System.out.println("totaaaaaaal Text"+ totalText);
        String[] lines = totalText.split("\n");
        for (int i=0;i<lines.length;i++)
        {
        	System.out.println(lines[i]);
        }
        
        switch (orderElement)
        {
        case "Amount":
         String Amount = lines[1].replace("Amount: ","").trim();
         String AmountWitoutCurrency=Amount.replace("USD", "").trim();
         System.out.println("Amountttttttt="+Amount);
         System.out.println("Amountttttttt without currency="+AmountWitoutCurrency);
         return AmountWitoutCurrency;
         
        case "Card Number":
            String cardNumber = lines[2].replace("Card Number: ","").trim();
            System.out.println("cardNumber="+cardNumber);
            return cardNumber;
            
        case "Name":
        	String name = lines[3].replace("Name: ","").trim();
            System.out.println("Name="+name);
            return name;
            
//        case "Date":
//        	String date = lines[4].replace("Date: ","").trim();
//            System.out.println("Date="+date);
//            return date;
             
        default:
        	return "Not Found";
        }
         
		//String productPriceWithoutCurrency = totalText.replace("USD", "").trim(); // strNew is
		//int priceValue = Integer.parseInt(productPriceWithoutCurrency);
		
	}
	
	public void confirmOrder()
	{
		Actions act = new Actions(driver);
		//WebElement okButton =findElementById("//button[contains(text(),'OK')]");
		WebElement okButton =findElementByDynamicXpath(driver,"//button[contains(text(),'OK')]");
		//div[@class='sa-confirm-button-container']
		//okButton.sendKeys("");
		//act.doubleClick(okButton).perform();
		okButton.click();
	}
	
}
