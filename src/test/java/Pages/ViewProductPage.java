package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewProductPage extends CommonMethods{

	public ViewProductPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}

	@FindBy(tagName = "h2")
	WebElement productName;
	
	@FindBy(css=".btn.btn-primary")
	WebElement addToCartButton;
	
	@FindBy(linkText = "Continue Shopping")
	WebElement continueShoppingButton;
	
	@FindBy(css = "div div div h3")
	WebElement price;
	
	public String viewProduct() {
		String product=productName.getText();
		return product;
	}
	
	public void continueShopping() {
		continueShoppingButton.click();
	}
	
	public String price() {
		return price.getText();
		
	} 
}
