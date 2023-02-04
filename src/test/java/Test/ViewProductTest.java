package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Pages.ProductsPage;
import Pages.ViewProductPage;

public class ViewProductTest extends BaseTest{
	
	ViewProductPage viewProductPage;
	List<String> productsViewed;
	List<String> productsToView;
	
	
	public void openProductPage() throws IOException  { 
		LoginPage loginPage=new LoginPage(driver);
		String validEmail=accessPropertiesFile("validEmail");
		String validPassword=accessPropertiesFile("validPassword");
        loginPage.login(validEmail, validPassword);
        
	}
	
	@Test
	public void addPrice() throws IOException {
		openProductPage();
		ProductsPage productPage=new ProductsPage(driver);
        productsToView=accessExcelFile("Product Name");  
        viewProductPage=new ViewProductPage(driver);
        for(String productToView:productsToView) {
        	String priceProductPage=productPage.productPrice(productToView);
        	String priceViewProductPage=viewProductPage.price();
        	try {
        		Assert.assertEquals(priceViewProductPage, priceProductPage);
        		insertIntoExcel("Price",priceViewProductPage,productsToView.size());
        	}catch (Exception e) {
			}
        	
        	viewProductPage.continueShopping();
        	
        	
        }
	}
	
	@Test
	public void viewProducts() throws IOException {
		openProductPage();
		ProductsPage productPage=new ProductsPage(driver);
        productsToView=accessExcelFile("Product Name");
        viewProductPage=new ViewProductPage(driver);
        productsViewed=new ArrayList<>();
        for(String productToView:productsToView) {
        	productsViewed.add(productPage.viewItem(productToView));
        	viewProductPage.continueShopping();
        }
		Assert.assertEquals(productsToView, productsViewed);
	}
	

}
