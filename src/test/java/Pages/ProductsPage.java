package Pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends CommonMethods {

	// WebDriver driver;

	public ProductsPage(WebDriver driver) {
		super(driver);
		// this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//nav/ul/li")
	List<WebElement> topBarList;

	@FindBy(css = "#products .container .row div .card")
	List<WebElement> productList;

	@FindBy(css = "#products .container .row div")
	WebElement productsEnclosure;

	@FindBy(css = "#sidebar input[name='search']")
	WebElement searchTextbox;

	@FindBy(name = "minPrice")
	WebElement minPriceTextbox;

	@FindBy(name = "maxPrice")
	WebElement maxPriceTextbox;

	@FindBy(xpath = "//div[@class='py-2 border-bottom ml-3'][3]/div[@class='form-group ng-star-inserted']")
	List<WebElement> categoriesCheckboxes;

	@FindBy(xpath = "//div[@class='py-2 border-bottom ml-3'][4]/div[@class='form-group ng-star-inserted']")
	List<WebElement> subCategoriesCheckboxes;

	@FindBy(xpath = "//div[@class='py-2 ml-3']/div[@class='form-group ng-star-inserted']")
	List<WebElement> searchForCheckboxes;

	@FindBy(css = ".card-body button[tabindex='0']")
	List<WebElement> viewButtons;

	@FindBy(css = ".card-body button[style='float: right;']")
	List<WebElement> addToCartButtons;

	@FindBy(xpath = "//div[@id='toast-container']/div")
	WebElement successMessageElement;

	@FindBy(id = "res")
	WebElement itemsShowingNumber;
	
	@FindBy(xpath="//ul/li/button[contains(text(),'Cart')]")
	WebElement cartButton;

	public List<String> products() {
		List<String> products = new ArrayList<>();
		if (productList.size() == Integer.parseInt(itemsShowingNumber.getText().split(" ")[1])) {
			products.add(itemsShowingNumber.getText().split(" ")[1]);
			for (WebElement product : this.productList) {
				products.add(product.findElement(By.xpath("div/h5/b")).getText());
			}
			return products;
		} else {
			products.add(itemsShowingNumber.getText().split(" ")[1]);
			return products;
		}

	}
	
	public String viewItem(String product) {
		boolean flag=false;
		for(int i=0;i<productList.size();i++) {
			if(productList.get(i).findElement(By.xpath("div/h5/b")).getText().toLowerCase().equals(product)){
				viewButtons.get(i).click();
				flag=true;
				break;
			}
		}
		if(flag==true) {
			return product;
		}
		else {
			return null;
		}
	}

	public List<String> addToCart(List<Map<String, String>> productsToBeAdded) {
		explicitWait(successMessageElement, "invisibility");
		List<String> noOfTimes = new ArrayList<>();
		for (Map<String, String> productToBeAdded : productsToBeAdded) {
			Set<Entry<String, String>> productAndQuantityToBeAdded = productToBeAdded.entrySet();
			Iterator<Entry<String, String>> it = productAndQuantityToBeAdded.iterator();
			Entry<String, String> product = it.next();
			Entry<String, String> quantity = it.next();
			for (WebElement button : this.addToCartButtons) {
				if (button.findElement(By.xpath("preceding-sibling::h5/b")).getText().equals(product.getValue().toUpperCase())) {
					int i = Integer.parseInt(quantity.getValue());
					while (i > 0) {
						try {
							button.click();
						} catch (Exception e) {
							button.click();
						} 
						
						explicitWait(successMessageElement, "visibility");
						noOfTimes.add(successMessageElement.getText());
						
						i--;
					}
				}
			}

		}

		return noOfTimes;
	}

	public Map<String, List<String>> search(List<String> keywordList) throws InterruptedException {
		String s = itemsShowingNumber.getText().split(" ")[1];
		Map<String, String> actionsToPerform = new LinkedHashMap<>();
		Map<String, List<String>> searchResult = new HashMap<>();
		for (String keyword : keywordList) {
			searchTextbox.clear();
			actionsToPerform.put("click", null);
			actionsToPerform.put("sendkeys", keyword);
			actionToPerform(searchTextbox, actionsToPerform);
			searchTextbox.sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			List<String> searchResults = products();
			searchResult.put(keyword, searchResults);
			s = itemsShowingNumber.getText().split(" ")[1];
		}
		return searchResult;
	}

	public Map<String, List<String>> searchByCategories(List<String> categoryType) throws InterruptedException {
		String s = itemsShowingNumber.getText().split(" ")[1];
		Map<String, List<String>> searchResult = new HashMap<>();
		for (String category : categoryType) {
			for (WebElement checkbox : this.categoriesCheckboxes) {
				if (checkbox.findElement(By.xpath("label")).getText().equals(category)) {
					checkbox.findElement(By.xpath("input")).click();
					Thread.sleep(1000);
					List<String> searchresults = products();
					searchResult.put(category, searchresults);
					checkbox.findElement(By.xpath("input")).click();
					s = itemsShowingNumber.getText().split(" ")[1];
				}
			}
		}
		return searchResult;
	}

	public Map<String, List<String>> searchFor(String gender) throws InterruptedException {
		Map<String, List<String>> searchResult = new HashMap<>();

		for (WebElement checkbox : searchForCheckboxes) {
			if (checkbox.findElement(By.xpath("label")).getText().equals(gender)) {
				try {
					checkbox.findElement(By.xpath("input")).click();
				} catch (Exception e) {
					checkbox.findElement(By.xpath("input")).click();
				}

				List<String> searchresults = products();
				searchResult.put(gender, searchresults);
			}
		}

		return searchResult;
	}
	
	public void openCart() {
		try {
			cartButton.click();
		} catch (Exception e) {
			cartButton.click();
		}		
	}
	
	public String productPrice(String product) {
		String productPrice=null;
		for(int i=0;i<productList.size();i++) {
			if(productList.get(i).findElement(By.xpath("div/h5/b")).getText().toLowerCase().equals(product)){
				productPrice=productList.get(i).findElement(By.cssSelector("div .text-muted")).getText();
				viewItem(product);
			}
		}
		return productPrice;
	}

}
