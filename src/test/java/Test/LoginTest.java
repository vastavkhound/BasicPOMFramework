package Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Resources.Retry;

public class LoginTest extends BaseTest{
	
	LoginPage loginPage;
	
	@Test
	public void withoutCredentials() {
		loginPage=new LoginPage(driver);
		List<String> actualMessages=loginPage.login("", "");
		List<String> expectedMessages=Arrays.asList("*Email is required","*Password is required");
		Assert.assertEquals(actualMessages, expectedMessages);
	}
	
	@Test
	public void withoutPassword() throws IOException {
		loginPage=new LoginPage(driver);
		String validEmail=accessPropertiesFile("validEmail");
		List<String> actualMessages=loginPage.login(validEmail, "");
		List<String> expectedMessages=Arrays.asList("*Password is required");
		Assert.assertEquals(actualMessages, expectedMessages);
	}
	
	@Test
	public void invalidEmailType() throws IOException {
		loginPage=new LoginPage(driver);
		String invalidEmailType=accessPropertiesFile("invalidEmail");
		String validPassword=accessPropertiesFile("validPassword");
		List<String> actualMessages=loginPage.login(invalidEmailType, validPassword);
		List<String> expectedMessages=Arrays.asList("*Enter Valid Email");
		Assert.assertEquals(actualMessages, expectedMessages);
	}
	
	@Test(groups = {"regression"})
	public void invalidPassword() throws IOException {
		loginPage=new LoginPage(driver);
		String validEmail=accessPropertiesFile("validEmail");
		String invalidPassword=accessPropertiesFile("invalidPassword");
		List<String> actualMessages=loginPage.login(validEmail, invalidPassword);
		List<String> expectedMessages=Arrays.asList("Incorrect email or password.");
		Assert.assertEquals(actualMessages, expectedMessages);
	}
	
	@Test(retryAnalyzer = Retry.class,groups = {"regression"})
	public void validCredentials() throws IOException {
		loginPage=new LoginPage(driver);
		String validEmail=accessPropertiesFile("validEmail");
		String validPassword=accessPropertiesFile("validPassword");
		List<String> actualMessages=loginPage.login(validEmail, validPassword);
		List<String> expectedMessages=Arrays.asList("Login Successfully");
		Assert.assertEquals(actualMessages, expectedMessages);
	}
	
	@Test(dataProvider = "data")
	public void checkCredentials(HashMap<String,String> data) throws IOException {
		loginPage=new LoginPage(driver);
		List<String> actualMessages=loginPage.login(data.get("email"), data.get("password"));
		List<String> expectedMessages=Arrays.asList(data.get("message"));
		Assert.assertEquals(actualMessages, expectedMessages);
	}
	
	@DataProvider
	public Object[][] data() throws IOException{
		List<HashMap<String,String>> data=accessJSONFile("src\\test\\java\\Resources\\data.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

}
