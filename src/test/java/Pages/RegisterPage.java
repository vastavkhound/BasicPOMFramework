package Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends CommonMethods {

	WebDriver driver;

	public RegisterPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "firstName")
	WebElement firstNameRegister;

	@FindBy(id = "lastName")
	WebElement lastNameRegister;

	@FindBy(id = "userEmail")
	WebElement emailRegister;

	@FindBy(id = "userMobile")
	WebElement mobileNumberRegister;

	@FindBy(className = "custom-select")
	WebElement occupationRegister;

	@FindBy(css = "div label input")
	List<WebElement> genderRegister;

	By passwordRegister = By.id("userPassword");

	By confirmPasswordRegister = By.id("confirmPassword");

	@FindBy(xpath = "//input[@type='checkbox']")
	WebElement ageCheckboxRegister;

	@FindBy(id = "login")
	WebElement registerButton;

	@FindBy(css = ".invalid-feedback div")
	List<WebElement> invalidEntries;

	@FindBy(xpath = "//div[contains(text(),'*Please check above checkbox')]")
	WebElement checkBoxRequired;
	
	@FindBy(xpath = "//div[@id='toast-container']/div/div")
	WebElement messageElement;

	public List<String> Register(String firstName, String lastName, String email, String phoneNumber, String occupation,
			String gender, String password, String confirmPassword, String age) {
		List<String> messages = new ArrayList<>();
		firstNameRegister.sendKeys(firstName);
		lastNameRegister.sendKeys(lastName);
		emailRegister.sendKeys(email);
		mobileNumberRegister.sendKeys(phoneNumber);

		select(occupationRegister, occupation);

		for (WebElement genderChoose : genderRegister) {
			if (genderChoose.findElement(By.xpath("following-sibling::span")).getText().equals(gender)) {
				genderChoose.click();
			}
		}

		driver.findElement(passwordRegister).sendKeys(password);
		driver.findElement(confirmPasswordRegister).sendKeys(confirmPassword);

			if (age.isEmpty()) {
				registerButton.click();
			} else {
				if (Integer.parseInt(age) >= 18) {
					ageCheckboxRegister.click();
					registerButton.click();
				}
				else {
					registerButton.click();
				}
			}
		
		try {
			messages.add(messageElement.getAttribute("innerHTML"));
			explicitWait(messageElement,"invisibility");
		}catch (Exception e) {
			for (WebElement invalidEntry : invalidEntries) {
				String message = invalidEntry.getText();
				messages.add(message);
			}
			
			try {
				messages.add(checkBoxRequired.getText());
			} catch (Exception e1) {
			}
		}
		
		return messages;
	}

}
