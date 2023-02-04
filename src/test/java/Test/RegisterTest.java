package Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Pages.RegisterPage;

public class RegisterTest extends BaseTest{
	
	RegisterPage registerPage;
	
    
	public void register() throws IOException {
		LoginPage loginPage=new LoginPage(driver);
		loginPage.clickRegister();
	}
	
	@Test
	public void noData() throws IOException {
		register();
		registerPage=new RegisterPage(driver);
		List<String> actualMessages=registerPage.Register("", "", "", "", "Choose your occupation", "", "", "", "");
		List<String> expectedMessages=Arrays.asList("*First Name is required","*Email is required","*Phone Number is required","*Password is required","Confirm Password is required","*Please check above checkbox");
		Assert.assertEquals(actualMessages, expectedMessages);
	}
	
	@Test(groups = {"regression"})
	public void invalidData() throws IOException {
		register();
		registerPage=new RegisterPage(driver);
		List<String> actualMessages=registerPage.Register("te", "", "vk", "ff", "Choose your occupation", "Female", "af", "by", "30");
		List<String> expectedMessages=Arrays.asList("*First Name must be 3 or more character long","*Enter Valid Email","*only numbers is allowed","*Phone Number must be 10 digit","Password and Confirm Password must match with each other.");
		Assert.assertEquals(actualMessages, expectedMessages);
	}
	
	@Test
	public void withoutLastName() throws IOException {
		register();
		registerPage=new RegisterPage(driver);
		List<String> actualMessages=registerPage.Register("test", "", "test@test.com", "9977556633", "Choose your occupation", "Female", "checking", "checking", "30");
		List<String> expectedMessages=Arrays.asList(" Last Name is required! ");
		Assert.assertEquals(actualMessages, expectedMessages);
		List<String> actualMessagesA=registerPage.Register("", "t", "", "", "", "", "", "", "");
		List<String> expectedMessagesA=Arrays.asList(" last Name must be 3 to 20 characters long! ");
		Assert.assertEquals(actualMessagesA, expectedMessagesA);
		
	}

}
