package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends BasePageClass {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[text()='Log in']")
	WebElement loginBtn;

	@FindBy(xpath = "//input[contains(@title,'email')]")
	WebElement userEmail;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement continueButton;

	@FindBy(xpath = "//input[@id='PASSWORD']")
	WebElement userPassword;

	@FindBy(xpath = "//div[text()='Next']")
	WebElement nextButton;

	@FindBy(xpath = "//button[text()='More options']")
	WebElement moreOptions;

	@FindBy(xpath = "//div/p[text()='Password']")
	WebElement passwordOption;

	public void enterUserDetails(String userName, String password) throws InterruptedException {
		webActionHelperMethods.MaximizeBrowser();
		loginBtn.click();
		userEmail.sendKeys(userName);
		Thread.sleep(3000);
		userEmail.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		List<WebElement> conditionCheck = driver.findElements(By.xpath("//input[@id='PASSWORD']"));

		if (conditionCheck.size() != 0) {
			userPassword.sendKeys(password);
			Thread.sleep(3000);
			nextButton.click();
			Thread.sleep(10000);
		}

		else {
			moreOptions.click();
			Thread.sleep(3000);
			passwordOption.click();
			Thread.sleep(3000);
			userPassword.sendKeys(password);
			Thread.sleep(3000);
			nextButton.click();
			Thread.sleep(10000);
		}

	}

}
