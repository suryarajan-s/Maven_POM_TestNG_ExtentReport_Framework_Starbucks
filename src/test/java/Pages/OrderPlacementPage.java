package Pages;

import java.io.IOException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

	@FindBy(xpath = "//div[text()='Subtotal']/following::span")
	WebElement productPrice;

	@FindBy(xpath = "//div[contains(text(),'Preparing your order')]")
	WebElement placedOrderValidation;

	@FindBy(xpath = "//button[text()='See details']")
	WebElement seeDetailsButton;
	
	@FindBy(xpath = "//button[@aria-label='Decrement']/following::div")
	WebElement quantityValidation;
	
	@FindBy(xpath = "//button[@aria-label='Close']")
	WebElement closeButton;
	
	@FindBy(xpath = "//div[text()='Delivery Fee']/following::div[3]")
	WebElement deliveryPriorityValidation;
	
	@FindBy(xpath = "//button[@data-testid='edit-delivery-instructions-button']")
	WebElement editButtonDeliveryOption;
	
	@FindBy(xpath = "//button[text()='Update']")
	WebElement updateButton;
	
	@FindBy(xpath = "//a[contains(@href,'location')]/following::a[1]//span")
	WebElement editDeliveryValidation;

	public static WebElement dynamicXpath(WebDriver driver, String xpath, String value) {
		return driver.findElement(By.xpath(String.format(xpath, value)));
	}

	public void addToOrder(String milk, String syrup, String shots, String sizeData, String bean,String TCID)
			throws InterruptedException, IOException {
		TestData data = new TestData(System.getProperty("user.dir") + "\\TestData\\starBucksTestData.xlsx");
		Thread.sleep(3000);
		sizeData = sizeData.trim();
		String condimentsXapth = "//div[text()='%s']/following::ul";
		WebElement condimentsValidationElement = dynamicXpath(driver, condimentsXapth, sizeData);

		String condimentsAdded = condimentsValidationElement.getText();
		System.out.println(condimentsAdded);
		if (condimentsAdded.contains(milk)) {
			Assert.assertTrue(condimentsAdded.contains(milk));
		} else if (condimentsAdded.contains(syrup)) {
			Assert.assertTrue(condimentsAdded.contains(syrup));
		}
		
		else if(condimentsAdded.contains(bean))
		{
			Assert.assertTrue(condimentsAdded.contains(bean));
		}
		else {
			Assert.assertTrue(condimentsAdded.contains(shots));
		}

		addToCartButton.click();
		Thread.sleep(7000);

		String price = productPrice.getText().replace("£", "");
		System.out.println(price);
		Thread.sleep(2000);
		data.writeTestData(TCID, "productPrize", price);
		System.out.println("the price is written in the excel file " + price + " ");
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
		System.out.println("the order time is written in the excel file " + data.getTestData(TCID, "orderTime") + "");

	}

	public void addFrequentlyOrderedItem(String TCID) throws InterruptedException, IOException {
		TestData data = new TestData(System.getProperty("user.dir") + "\\TestData\\starBucksTestData.xlsx");
		WebDriverWait waitForResult = new WebDriverWait(driver, Duration.ofSeconds(150));
		waitForResult.until(ExpectedConditions.visibilityOf(seeDetailsButton));
		seeDetailsButton.click();
		Thread.sleep(3000);

		String productNameXpath = "//h3[text()='Frequently bought together']/following::a//div//span";
		WebElement selectProduct = driver.findElement(By.xpath(productNameXpath));
		WebDriverWait waitForItem = new WebDriverWait(driver, Duration.ofSeconds(150));
		waitForItem.until(ExpectedConditions.visibilityOf(selectProduct));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectProduct);
		Thread.sleep(500); // optional
		String selectedProductName = selectProduct.getText();
		data.writeTestData(TCID, "frequentlyOrderedItem", selectedProductName);
		Thread.sleep(4000);
		System.out.println("the selected frequently ordered product is written in the excel file "
				+ data.getTestData(TCID, "frequentlyOrderedItem") + "");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectProduct);
		Thread.sleep(5000);

		addToCartButton.click();
		Thread.sleep(7000);

		addToCartButton.click();
		Thread.sleep(7000);

		String frequntlyOrderedItemXpath = "//div[text()='%s']";
		WebElement frequntlyOrderedItemElement = dynamicXpath(driver, frequntlyOrderedItemXpath,
				data.getTestData(TCID, "frequentlyOrderedItem"));

		String frequntlyOrderedItemAdded = frequntlyOrderedItemElement.getText();
		Assert.assertTrue(frequntlyOrderedItemAdded.contains(data.getTestData(TCID, "frequentlyOrderedItem").trim()));

		String price = productPrice.getText().replace("£", "");
		System.out.println(price);
		Thread.sleep(2000);
		data.writeTestData(TCID, "productPrize", price);
		System.out.println("the price is written in the excel file " + price + " ");
		checkOutButton.click();
		Thread.sleep(5000);
		if (skipbutton.isDisplayed()) {
			skipbutton.click();
			Thread.sleep(5000);
		}

	}
	
	public void addToOrderForNonDrink(String TCID) throws IOException, InterruptedException {
		TestData data = new TestData(System.getProperty("user.dir") + "\\TestData\\starBucksTestData.xlsx");
		addToCartButton.click();
		Thread.sleep(7000);
		if (data.getTestData(TCID, "quantityValueDropDown") != null && !data.getTestData(TCID, "quantityValueDropDown").trim().isEmpty()) {
			String quantity = quantityValidation.getText();
			Assert.assertTrue(quantity.contains(data.getTestData(TCID, "quantityValueDropDown")));
		}
		
		String price = productPrice.getText().replace("£", "");
		System.out.println(price);
		Thread.sleep(2000);
		data.writeTestData(TCID, "productPrize", price);
		System.out.println("the price is written in the excel file " + price + " ");
		checkOutButton.click();
		Thread.sleep(5000);
		if (skipbutton.isDisplayed()) {
			skipbutton.click();
			Thread.sleep(5000);
		}
	}
	
	public void clickCloseButton() throws InterruptedException {
		closeButton.click();
		Thread.sleep(5000);
	}
	
	public void clickCheckoutButton() throws InterruptedException {
		checkOutButton.click();
		Thread.sleep(5000);
		if (skipbutton.isDisplayed()) {
			skipbutton.click();
			Thread.sleep(5000);
		}

	}
	
	public void addMultipleItemsToOrder(String milk, String syrup, String shots, String sizeData, String bean,String TCID)
			throws InterruptedException, IOException {
		TestData data = new TestData(System.getProperty("user.dir") + "\\TestData\\starBucksTestData.xlsx");
		Thread.sleep(3000);
		sizeData = sizeData.trim();
		String condimentsXapth = "//div[text()='%s']/following::ul";
		WebElement condimentsValidationElement = dynamicXpath(driver, condimentsXapth, sizeData);

		String condimentsAdded = condimentsValidationElement.getText();
		System.out.println(condimentsAdded);
		if (condimentsAdded.contains(milk)) {
			Assert.assertTrue(condimentsAdded.contains(milk));
		} else if (condimentsAdded.contains(syrup)) {
			Assert.assertTrue(condimentsAdded.contains(syrup));
		}
		
		else if(condimentsAdded.contains(bean))
		{
			Assert.assertTrue(condimentsAdded.contains(bean));
		}
		else {
			Assert.assertTrue(condimentsAdded.contains(shots));
		}

		addToCartButton.click();
		Thread.sleep(7000);

		String price = productPrice.getText().replace("£", "");
		System.out.println(price);
		Thread.sleep(2000);
		data.writeTestData(TCID, "productPrize", price);
		System.out.println("the price is written in the excel file " + price + " ");
	}
	
	public void setDeliveryPriority(String deliveryPriority) throws InterruptedException {
		String deliveryPriorityXpath = "//span[text()='%s']";
		WebElement deliveryPriorityElement = dynamicXpath(driver, deliveryPriorityXpath, deliveryPriority);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deliveryPriorityElement);
		Thread.sleep(500); // optional
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", deliveryPriorityElement);
		Thread.sleep(3000);
		
		System.out.println(deliveryPriorityElement.getText());
		Assert.assertTrue(deliveryPriorityElement.getText().contains(deliveryPriority));
		
	}
	
	public void editDeliveryOption(String editDeliveryOption,String dropOffOption) throws InterruptedException {
		editButtonDeliveryOption.click();
		Thread.sleep(3000);
		
		String editDropOffOptionXpath = "//span[text()='%s']";
		WebElement editDropOffElement = dynamicXpath(driver, editDropOffOptionXpath, dropOffOption);
		editDropOffElement.click();
		
		String editDeliveryOptionXpath = "//span[text()='%s']/ancestor::label";
		WebElement editDeliveryOptionElement = dynamicXpath(driver, editDeliveryOptionXpath, editDeliveryOption);
		editDeliveryOptionElement.click();
		Thread.sleep(5000);
		List<WebElement> updateButtonSize = driver.findElements(By.xpath("//button[text()='Update']"));
		if (updateButtonSize.size() != 0) {
			updateButton.click();
			Thread.sleep(3000);
		}
		Assert.assertTrue(editDeliveryValidation.getText().contains(editDeliveryOption));
	}
}
