package Pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.CompositeAction;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CommonMethods {
	
	WebDriver driver;
	
	WebDriverWait wait;
	
	Actions actions;
	
	public CommonMethods(WebDriver driver) {
		this.driver=driver;
	}
	
	public void srollWindow(int x,int y) {
		JavascriptExecutor jse=(JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy("+x+","+y+")");
	}
	
	public void explicitWait(By locator,String type,int number) {
		this.wait=new WebDriverWait(driver, Duration.ofSeconds(3));
		switch (type) {
		
        case "elementsToBeLessThan":
        	elementsToBeLessThan(locator,number);
			break;
       
	
		default:
			break;
		}
	}
	
	public void elementsToBeLessThan(By locator,int number) {
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(locator, number));
	}
	
	public void explicitWait(WebElement webElement,String type) {
		this.wait=new WebDriverWait(driver, Duration.ofSeconds(3));
		switch (type) {
		case "visibility":
			elementVisibility(webElement);
			break;
        case "invisibility":
        	elementInvisibility(webElement);
			break;
        case "visibilityorinvisibility":
        	elementVisibilityorInvisibility(webElement);
			break;
        case "clickable":
        	elementClickable(webElement);
			break;
	
		default:
			break;
		}
	
	}
	
	public void elementVisibility(WebElement webElement) {
		this.wait.until(ExpectedConditions.visibilityOf(webElement));
	}
	
	public void elementInvisibility(WebElement webElement) {
		this.wait.until(ExpectedConditions.invisibilityOf(webElement));
	}
	
	public void elementVisibilityorInvisibility(WebElement webElement) {
		//ExpectedConditions.visibilityOf(webElement) ExpectedConditions.invisibilityOf(webElement)
		this.wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(webElement),ExpectedConditions.invisibilityOf(webElement)));
	}
	
	public void elementClickable(WebElement webElement) {
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}
	
	public void select(WebElement webElement,String visibleText) {
		Select select=new Select(webElement);
		if(select.getFirstSelectedOption().getText().equals(visibleText)) {
		}
		else {
			for(WebElement s:select.getOptions()) {
				if(s.getText().equals(visibleText)) {
					select.selectByVisibleText(visibleText);
				}
			}
		}
	}
	
//	public String screenshot(String name,WebDriver driver) throws IOException {
//		File file=new File(System.getProperty("user.dir")+"\\src\\test\\java\\Resources\\"+name+".png");
//		TakesScreenshot screenshot=(TakesScreenshot)driver;
//		File screenshotFile=screenshot.getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(screenshotFile, file);
//		return file.getAbsolutePath();
//	}
	
	public void actionToPerform(WebElement webElement, Map<String,String> actionsToPerform) {
		actions=new Actions(driver);
		for(Entry<String,String> a:actionsToPerform.entrySet()) {
			 switch (a.getKey()) {
			case "click":
				actions.click(webElement).build().perform();
				break;
			case "sendkeys":
				actions.sendKeys(a.getValue()).build().perform();
				break;	
			default:
				break;
			}
		}
			
	}

}
