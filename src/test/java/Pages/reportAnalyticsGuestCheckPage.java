package Pages;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import util.TestData;

public class reportAnalyticsGuestCheckPage extends BasePageClass {
	public reportAnalyticsGuestCheckPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[text()='Guest Checks']")
	WebElement guestCheckTab;

	@FindBy(xpath = "//div[contains(@aria-labelledby,'location_select')]")
	WebElement rALocationSelection;

	@FindBy(xpath = "//input[contains(@aria-controls,'location_select')]")
	WebElement rALocationInput;

	@FindBy(xpath = "//div[contains(@aria-label,'West Bromwich Drive Thru')]")
	WebElement rALocationInputSelection;

	@FindBy(xpath = "//input[@id='start_business_date_picker|input']")
	WebElement dateSelectionField;

	@FindBy(xpath = "//span[text()='Search']")
	WebElement searchButton;

	@FindBy(xpath = "//th[@title='Check Number']")
	WebElement searchResult;

	@FindBy(xpath = "//input[contains(@id,'check_number')]")
	WebElement selectCheckNumber;

	@FindBy(xpath = "//input[contains(@id,'check_total')]")
	WebElement selectCheckTotal;

	@FindBy(xpath = "//span[text()='Equal']")
	WebElement equalSelection;

	@FindBy(xpath = "//input[contains(@id,'check_number_input')]")
	WebElement enterCheckNumber;

	@FindBy(xpath = " //input[contains(@id,'check_total_input')]")
	WebElement enterCheckTotal;

	@FindBy(xpath = "//div[text()='Name']/following::td[6]//span")
	WebElement productName;

	@FindBy(xpath = "//td[text()='Subtotal']/following::td[1]")
	WebElement price;

	public void rAGuestCheckReport(String locationSelection,String inputProduct,
			String inputPriceOfProduct,String TCID) throws InterruptedException, IOException {
		TestData data = new TestData(System.getProperty("user.dir") + "\\TestData\\starBucksTestData.xlsx");
		WebDriverWait guestCheckWait = new WebDriverWait(driver, Duration.ofSeconds(20));
		guestCheckWait.until(ExpectedConditions.elementToBeClickable(guestCheckTab));
		guestCheckTab.click();
		Thread.sleep(3000);
		rALocationSelection.click();
		Thread.sleep(3000);
		rALocationInput.sendKeys(locationSelection);
		Thread.sleep(3000);

		// see if the store exists then click
		rALocationInputSelection.click();
		Thread.sleep(3000);
		dateSelectionField.click();
		Thread.sleep(3000);
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");
		String date = currentDate.format(formatter);
		driver.findElement(By.xpath("//a[text()='" + date + "']")).click();
		Thread.sleep(3000);

		selectCheckTotal.click();
		Thread.sleep(3000);
		equalSelection.click();
		Thread.sleep(3000);
		System.out.println("The input price of the product is : " +data.getTestData(TCID, "productPrize")+ "");
		inputPriceOfProduct = data.getTestData(TCID, "productPrize");
		Thread.sleep(2000);
		enterCheckTotal.sendKeys(inputPriceOfProduct);
		Thread.sleep(3000);
		searchButton.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(250));
		wait.until(ExpectedConditions.visibilityOf(searchResult));
		System.out.println(data.getTestData(TCID, "orderTime"));
		String orderNumberDisplyed = driver
				.findElement(By.xpath("//*[contains(., '" + data.getTestData(TCID, "orderTime") + "')][last()]/ancestor::tr//td[2]//a[1]"))
				.getText();
		System.out.println(orderNumberDisplyed);
		driver.findElement(By.xpath("//a[text()='" + orderNumberDisplyed + "']")).click();

		WebDriverWait waitForResult = new WebDriverWait(driver, Duration.ofSeconds(150));
		waitForResult.until(ExpectedConditions.visibilityOf(productName));

		String productNameFromReport = productName.getText();
		String cleanedActual = inputProduct.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().trim();
		String cleanedExpected = productNameFromReport.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().trim();
		cleanedExpected = cleanedExpected.replaceFirst("^[a-z]{2}\\s*", "");
		cleanedActual = cleanedActual.trim();
		cleanedExpected = cleanedExpected.trim();
		
		System.out.println(cleanedActual);
		System.out.println(cleanedExpected);
		System.out.println(cleanedActual.contains(cleanedExpected));
		
		WebDriverWait waitForResult1 = new WebDriverWait(driver, Duration.ofSeconds(150));
		waitForResult1.until(ExpectedConditions.visibilityOf(price));

		String prizeFromReport = price.getText();
		Assert.assertTrue(inputPriceOfProduct.contains(prizeFromReport));
		
		Thread.sleep(8000);

	}
}
