package linkDevPages.Pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.LogStatus;


import linkDEV.Bases.PageBase;

public class LoginPage extends PageBase{
	
	final   static Logger logger = Logger.getLogger(LoginPage.class);
	//WebDriver driver;
	private WebDriverWait wait ;
	
	private WebElement loginButton;
	private WebElement loginUserName;
	private WebElement loginPassword;
	private WebElement loginSecondButton;
	private WebElement logout;
 
	

	public LoginPage(WebDriver driver)
	{	
		setDriver(driver);
		wait =new WebDriverWait(getDriver(), 200) ;
	}

	public void waitForPageLoad()
	{
		  try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
	}
	
	
	public void login(String userName , String password)
	{
		// this function is just to perform the login function 
		logger.info("click on login button ...");
		loginButton = findElementById("login2");
		click(loginButton);
		loginUserName = findElementById("loginusername"); 
		loginPassword = findElementById ("loginpassword");
		enterTextValue(driver,loginUserName,userName);
		enterTextValue(driver,loginPassword,password);
		loginSecondButton = findElementByDynamicXpath(driver, "//button[contains(text(),'Log in')]") ;
		click(loginSecondButton);
	}
	
	
	public boolean isElementDisplayed(String ButtonID) throws InterruptedException
	{
	   Thread.sleep(2000);
	   logout = findElementById(ButtonID);
	   return isElementExists(logout);
	   
	}
	
}