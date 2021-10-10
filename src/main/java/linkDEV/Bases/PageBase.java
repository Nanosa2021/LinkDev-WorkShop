package linkDEV.Bases;

import java.util.Properties;



import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageBase {
	final   static Logger logger = Logger.getLogger(PageBase.class);
	Properties properties;
	String ElementValue;
	protected WebDriver driver;

	public Properties getProperties() {
		return properties;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public WebElement findElementById(String id)
	{
		WebElement element = null;

		try{
			element= driver.findElement(By.id(id));
			logger.info("element with " +id +"found successfully "); 
		}

		catch (Exception ex) {
			logger.error("element with " +id +" does not found  " +  " due to exception " + ex.getMessage());
		}
		return element;
	}

	public WebElement findElementByDynamicXpath(WebDriver driver,String xPath)
	{
		WebElement element = null;
        System.out.println("Xpath = " +xPath);
		try{
			element= driver.findElement(By.xpath(xPath));
			logger.info("element with " +xPath +"found successfully "); 
		}

		catch (Exception ex) {
			logger.error("element with " +xPath +" does not found successfully " +  " due to exception " + ex.getMessage());
		}
		return element;
	}


	public void enterTextValue (WebDriver driver,WebElement element, String value)
	{
		try{
			element.sendKeys(value);
			logger.info("element with " +value +"found successfully "); 
		}

		catch (Exception ex) {
			logger.error("element with " +value +" does not found successfully " +  " due to exception " + ex.getMessage());
		}
	}

	public boolean isElementExists(WebElement element)
	{
		if (element.isDisplayed())
		{
			return true ;
		}
		else 
			return false;
	}

	public void click (WebElement element)
	{
		try{
			if (element.isDisplayed())
			{
				ElementValue = element.getAttribute("value");
				element.click();
				logger.info("element with " +ElementValue +"clicked successfully ");
			}
		}
			catch (org.openqa.selenium.StaleElementReferenceException ex) {
				logger.error("element with " +element.getAttribute("value") +" does not clicked " +  " due to exception " + ex.getMessage());
			}
		}

	

	}
