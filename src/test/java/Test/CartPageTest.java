package Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;

import Pages.CartPage;
import Pages.LoginPage;
import Pages.ProductsPage;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CartPageTest extends BaseTest{
	
	ProductsPage productPage;
	List<Map<String, String>> productsToAdd;
	CartPage cartPage;
	List<String> productsInCart;
	List<String> productsToDelete;
String path;
	
	@After
	public void before_or_after(Scenario scenario) throws IOException {
		if(driver!=null) {
			path=screenshot(scenario.getName(), driver);
			closeBrowser();
		}			
		    	
    }
	
	@Given("User is logged in to the application")
	public void user_is_logged_in_to_the_application() throws IOException {
		initialize();
		LoginPage loginPage=new LoginPage(driver);
		String validEmail=accessPropertiesFile("validEmail");
		String validPassword=accessPropertiesFile("validPassword");
        loginPage.login(validEmail, validPassword);
	}

	@Given("user has added products to cart")
	public void user_has_added_products_to_cart(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		productsToAdd=dataTable.asMaps();
		productPage=new ProductsPage(driver);
	    productPage.addToCart(productsToAdd);
	}

	@Given("user opens the cart")
	public void user_opens_the_cart() {
		productPage=new ProductsPage(driver);
		productPage.openCart();
	}
	
	@When("check for the products in cart")
	public void check_for_the_products_in_cart() {
		cartPage=new CartPage(driver);
		productsInCart=cartPage.productNames();
	}

	@Then("The above products should be present in the cart")
	public void the_above_products_should_be_present_in_the_cart() {
		List<String> expectedProductsInCart=new ArrayList<>();
	    for(Map<String,String> product:productsToAdd) {
	    	for(Entry<String,String> p:product.entrySet()) {
	    		if(p.getKey().equals("Product")){
	    			expectedProductsInCart.add(p.getValue().toUpperCase());
	    			break;
	    		}
	    	}
	    }
	    Assert.assertEquals(productsInCart, expectedProductsInCart);
	}
	
	@When("delete the products in cart")
	public void delete_the_products_in_cart(io.cucumber.datatable.DataTable dataTable) throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		productsToDelete=dataTable.asList();
		cartPage=new CartPage(driver);
		productsInCart=cartPage.deleteProductsFromCart(productsToDelete);
	}

	@Then("The above products should be deleted")
	public void the_above_products_should_be_deleted() {
		List<Boolean> actual=productsToDelete.stream().map(s->productsInCart.contains(s)).collect(Collectors.toList());
		List<Boolean> expected=Arrays.asList(false,false);
		Assert.assertEquals(actual, expected);
	}

}
