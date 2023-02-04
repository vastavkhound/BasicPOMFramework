package Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends CommonMethods {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userEmail")
	WebElement loginEmail;

	By loginPassword = By.id("userPassword");

	@FindBy(css = ".invalid-feedback")
	List<WebElement> errorMessages;

	@FindBy(name = "login")
	WebElement loginButton;

    @FindBy(xpath = "//div[@id='toast-container']/div/div")
	WebElement messageElement;

	@FindBy(xpath = "//ul/li/button[contains(text(),' Sign Out ')]")
	WebElement sigOutButton;
	
	@FindBy(linkText = "Register")
	WebElement registerButton;

	public void clickRegister() {
		registerButton.click();
	}
	
	public List<String> login(String username, String password) {
		List<String> messages = new ArrayList<>();
		explicitWait(loginEmail, "visibility");
		loginEmail.sendKeys(username);
		driver.findElement(loginPassword).sendKeys(password);
		loginButton.click();
		String text = null;
		try {
			text = messageElement.getAttribute("aria-label");
			messages.add(text);
			return messages;
		} catch (Exception e) {
			for (WebElement errorMessage : this.errorMessages) {
				String error = errorMessage.getText();
				messages.add(error);
			}
			return messages;
		}
	}
}
