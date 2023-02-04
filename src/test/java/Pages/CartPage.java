package Pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends CommonMethods{
	
	public CartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	By productNameLocators=By.xpath("//div/div[@class='cartSection']/h3");
	
	@FindBy(css=".btn.btn-danger")
	List<WebElement> deleteButtons;
	
	public List<String> productNames() {
		List<WebElement> productNameElements=driver.findElements(productNameLocators);
		if(productNameElements.size()>2) {
			srollWindow(0,250);
		}
		List<String> productNames=new ArrayList<>();
		productNameElements.stream().forEach(s->productNames.add(s.getText()));
		return productNames;
	}
	
	public List<String> deleteProductsFromCart(List<String> productsToDelete) throws InterruptedException {
		for(String productToDelete:productsToDelete) {
			List<WebElement> productNameElements=driver.findElements(productNameLocators);
			if(productNameElements.size()>2) {
				srollWindow(0,250);
			}
			for(int i=0;i<productNameElements.size();i++) {
				if(productNameElements.get(i).getText().toLowerCase().equals(productToDelete)) {					
						productNameElements.get(i).findElement(By.xpath("parent::div/parent::div/div/button[@class='btn btn-danger']")).click();
						explicitWait(productNameLocators, "elementsToBeLessThan",productNameElements.size());
						break;
				}
			}
		}
		return productNames();
	}

}
