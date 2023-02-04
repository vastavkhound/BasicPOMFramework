package Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;

import Pages.LoginPage;
import Pages.ProductsPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductTest extends BaseTest{
	
	ProductsPage productPage;
	List<String> productsActual;
	Map<String,List<String>> searchResults;
	Map<String,List<String>> searchByResults;
	Map<String,List<String>> searchForResults;
	List<String> productsAddedToCart;
	String path;
	
	@After
	public void before_or_after(Scenario scenario) throws IOException {
		if(driver!=null) {
			path=screenshot(scenario.getName(), driver);
			closeBrowser();
		}			
		    	
    }
	
	
	
	@Given("User logged into the application")
	public void user_logged_into_the_application() throws IOException {
		initialize(); 
		LoginPage loginPage=new LoginPage(driver);
		String validEmail=accessPropertiesFile("validEmail");
		String validPassword=accessPropertiesFile("validPassword");
        loginPage.login(validEmail, validPassword);
	}

	@When("Check for the displayed products")
	public void check_for_the_displayed_products() {
	    // Write code here that turns the phrase above into concrete actions
		productPage=new ProductsPage(driver);
		productsActual=productPage.products();
	}

	@Then("Verify product list")
	public void get_the_product_list() {
		List<String> productsExpected=Arrays.asList("3","zara coat 3","adidas original","iphone 13 pro") ;
		if(productsActual.size()-1==Integer.parseInt(productsActual.get(0))) {
			for(int i=0;i<productsActual.size();i++) {
				if(productsExpected.get(i).equalsIgnoreCase(productsActual.get(i))) {
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
				}
			}
		}
		else {
			Assert.assertTrue(false);
		}
	}

	@When("User enters search names in the search bar")
	public void user_enters_product_names_in_the_search_bar(io.cucumber.datatable.DataTable dataTable) throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    List<String> keywords=dataTable.asList(String.class);
	    productPage=new ProductsPage(driver);
	    searchResults=productPage.search(keywords);
	}

	@Then("The required product is visible")
	public void the_required_product_is_visible() {
		Set<Entry<String, List<String>>> keysValues=searchResults.entrySet();
		for(Entry<String, List<String>> keyValue:keysValues) {
			List<String> values=keyValue.getValue();
			if(Integer.parseInt(values.get(0))>0) {
				for(int i=1;i<values.size();) {
					if(values.get(i).toLowerCase().contains(keyValue.getKey())){
						Assert.assertTrue(true);
						break;
					}
					else {
						Assert.assertTrue(false);
						break;
					}
				}
			}
			else {
				if(keyValue.getKey().equals("keyword")) {
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
				}
			}
			
		}
	}
	

	@When("check different checkboxes in the")
	public void check_different_checkboxes_in_the(io.cucumber.datatable.DataTable dataTable) throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		List<String> categoryType=dataTable.asList();
		productPage=new ProductsPage(driver);
		searchByResults= productPage.searchByCategories(categoryType);
		  
	}

	@Then("verify the number products shown")
	public void verify_the_number_products_shown() {
		Set<Entry<String, List<String>>> searchByResultSet=searchByResults.entrySet();
		for(Entry<String, List<String>> result:searchByResultSet) {
			if(result.getKey().equals("electronics")) {
				List<String> values=result.getValue();
				if((values.size()-1)==Integer.parseInt(values.get(0))) {
					for(int i=1;i<values.size()-1;i++) {
						if(values.get(i).contains("IPHONE 13")) {
							Assert.assertTrue(true);
						}
						else {
							Assert.assertTrue(false);
						}
					}
				}
				else {
					Assert.assertTrue(false);
				}
			}
			else if(result.getKey().equals("fashion")) {
				List<String> values=result.getValue();
				if((values.size()-1)==Integer.parseInt(values.get(0))) {
					for(int i=1;i<values.size()-1;i++) {
						if(values.get(i).contains("ZARA") || values.get(i).contains("ADIDAS")) {
							Assert.assertTrue(true);
						}
						else {
							Assert.assertTrue(false);
						}
					}
				}
				else {
					Assert.assertTrue(false);
				}
			}
			else {
				List<String> values=result.getValue();
				if((values.size()-1)==Integer.parseInt(values.get(0))) {
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
				}
			}
		}
	}
	
	@When("check different {string} in the")
	public void check_different_in_the(String string) throws InterruptedException {
	    productPage=new ProductsPage(driver);
	    searchForResults=productPage.searchFor(string);
	    
	}

	@Then("verify the {string} shown")
	public void verify_the_shown(String string) {
		Set<Entry<String, List<String>>> searchForResultsPair=searchForResults.entrySet();
		for(Entry<String, List<String>> pair:searchForResultsPair) {
			List<String> values=pair.getValue();
				if(pair.getKey().equals("men") && values.get(0).equals("2")) {
					Assert.assertEquals(values, Arrays.asList("2", "ADIDAS ORIGINAL", "IPHONE 13 PRO"));
				}
				else if(pair.getKey().equals("women") && values.get(0).equals("1")) {
					Assert.assertEquals(values, Arrays.asList("1", "ZARA COAT 3"));
				}
			
		}
	}
	
	@When("Add Products to Cart")
	public void add_products_to_cart(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		List<Map<String, String>> productsToAdd=dataTable.asMaps();
		productPage=new ProductsPage(driver);
		productsAddedToCart=productPage.addToCart(productsToAdd);
	}

	@Then("verify that products are added to cart")
	public void verify_that_products_are_added_to_cart() {
		if(productsAddedToCart.size()==6) {
			Assert.assertTrue(true);
		}
		else {
			Assert.assertTrue(false);
		}
		
	}

}
