package Pages;

import java.io.IOException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import util.TestData;

public class OrderPlacementPage extends BasePageClass {
	public OrderPlacementPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[text()='Place order']")
	WebElement placeOrderButton;

	@FindBy(xpath = "//button[@data-testid='add-to-cart-button']")
	WebElement addToCartButton;

	@FindBy(xpath = "//a[text()='Go to checkout']")
	WebElement checkOutButton;

	@FindBy(xpath = "//button[text()='Skip']")
	WebElement skipbutton;

	@FindBy(xpath = "//div[@class='sy']//ul")
	WebElement condimentsValidation;

	@FindBy(xpath = "//div[text()='Subtotal']/following::span")
	WebElement productPrice;

	@FindBy(xpath = "//div[contains(text(),'Preparing your order')]")
	WebElement placedOrderValidation;

	public static WebElement dynamicXpath(WebDriver driver, String xpath, String value) {
		return driver.findElement(By.xpath(String.format(xpath, value)));
	}

	public void addToOrder(String milk, String syrup, String shots, String sizeData, String TCID)
			throws InterruptedException, IOException {
		TestData data = new TestData(System.getProperty("user.dir") + "\\TestData\\starBucksTestData.xlsx");
		Thread.sleep(3000);
		sizeData = sizeData.trim();
		String condimentsXapth = "//div[text()='%s']/following::div[2]//ul";
		WebElement condimentsValidationElement = dynamicXpath(driver, condimentsXapth, sizeData);

		String condimentsAdded = condimentsValidationElement.getText();
		System.out.println(condimentsAdded);
		if (condimentsAdded.contains(milk)) {
			Assert.assertTrue(condimentsAdded.contains(milk));
		} else if (condimentsAdded.contains(syrup)) {
			Assert.assertTrue(condimentsAdded.contains(syrup));
		}

		else {
			Assert.assertTrue(condimentsAdded.contains(shots));
		}

		addToCartButton.click();
		Thread.sleep(7000);

		String price = productPrice.getText().replace("£", "");
		System.out.println(price);
		data.writeTestData(TCID, "productPrize", price);

		checkOutButton.click();
		Thread.sleep(5000);
		if (skipbutton.isDisplayed()) {
			skipbutton.click();
			Thread.sleep(5000);
		}

	}

	public void placeOrder(String TCID) throws InterruptedException, IOException {
		TestData data = new TestData(System.getProperty("user.dir") + "\\TestData\\starBucksTestData.xlsx");
		placeOrderButton.click();
		WebDriverWait waitForResult = new WebDriverWait(driver, Duration.ofSeconds(150));
		waitForResult.until(ExpectedConditions.visibilityOf(placedOrderValidation));
		Assert.assertTrue(placedOrderValidation.isDisplayed());
		ZonedDateTime systemTime = ZonedDateTime.now();
		ZonedDateTime ukTime = systemTime.withZoneSameInstant(ZoneId.of("Europe/London"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String ukFormattedTime = ukTime.format(formatter);
		System.out.println(ukFormattedTime);
		data.writeTestData(TCID, "orderTime", ukFormattedTime);
		Thread.sleep(4000);
		System.out.println("the file is written in the excel file " + data.getTestData(TCID, "orderTime") + "");

	}
}
