package Pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePageClass {
	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[contains(@data-testid,'delivery-address')]")
	WebElement deliveryAddress;

	@FindBy(xpath = "//div[text()='Customize this item']")
	WebElement customizeButton;

	@FindBy(xpath = "//button[contains(text(),'Save')]")
	WebElement saveButton;

	public static WebElement dynamicXpath(WebDriver driver, String xpath, String value) {
		return driver.findElement(By.xpath(String.format(xpath, value)));
	}

	public void selectProducts(String menuData, String productName) throws InterruptedException, IOException {

		String selectMenuXpath = "//div[text()='%s']";
		WebElement selectMenuItem = dynamicXpath(driver, selectMenuXpath, menuData);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectMenuItem);
		Thread.sleep(500); // optional
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectMenuItem);
		Thread.sleep(5000);

		String productNameXpath = "//span[text()='%s']";
		WebElement selectProduct = dynamicXpath(driver, productNameXpath, productName);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectProduct);
		Thread.sleep(500); // optional
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectProduct);
		Thread.sleep(5000);
	}

	public void customizeSelectedProduct(String sizeData, String milkData, String syrupData, String shots, int quantity)
			throws InterruptedException, IOException {
		List<WebElement> sizeCheck = driver.findElements(By.xpath("//div[text()='Size']"));
		if (sizeCheck.size() != 0) {
			WebElement defaultSelection = driver
					.findElement(By.xpath("(//div[text()='Grande']/preceding::input)[last()]"));
			// String isSelected = defaultSelection.getDomAttribute("checked");
			System.out.println(defaultSelection.isSelected());
			Assert.assertTrue(defaultSelection.isSelected());
			Thread.sleep(3000);
			String sizeSelectionXpath = "//div[text()='%s']/ancestor::label";
			WebElement size = dynamicXpath(driver, sizeSelectionXpath, sizeData);
			size.click();
			Thread.sleep(5000);
			List<WebElement> customizeItemButton = driver.findElements(By.xpath("//div[text()='Customize this item']"));
			if (customizeItemButton.size() != 0) {
				customizeButton.click();
			}
			Thread.sleep(3000);

			List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
			for (WebElement checkBox : checkBoxes) {
				if (checkBox.isSelected()) {
					System.out.println("Checkbox with value " + checkBox.getAttribute("outerHTML")
							+ "  is selected. Hence Unchecking");
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkBox);
					Thread.sleep(500); // optional
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);
					Thread.sleep(3000);
					System.out.println("Unselected checkbox with id: " + checkBox.getAttribute("id"));
					Thread.sleep(3000);
				}

				else {
					System.out.println("The checkbox is already not checked");
				}

			}

			if (milkData != null && !milkData.trim().isEmpty()) {
				milkSelection(milkData);
			}

			if (syrupData != null && !syrupData.trim().isEmpty()) {
				syrupSelection(syrupData, quantity);
			}

			if (shots != null && !shots.trim().isEmpty()) {
				shotSelection(shots, quantity);
			}

			saveButton.click();
			Thread.sleep(3000);
		}
	}

	public void customizeSelectedProductWithoutNavigation(String sizeData, String milkData, String syrupData,
			String shots, int quantity) throws InterruptedException, IOException {
		List<WebElement> sizeCheck = driver.findElements(By.xpath("//div[text()='Size']"));
		if (sizeCheck.size() != 0) {
			WebElement defaultSelection = driver
					.findElement(By.xpath("(//div[text()='Grande']/preceding::input)[last()]"));
			// String isSelected = defaultSelection.getDomAttribute("checked");
			System.out.println(defaultSelection.isSelected());
			Assert.assertTrue(defaultSelection.isSelected());
			Thread.sleep(3000);
			String sizeSelectionXpath = "//div[text()='%s']/ancestor::label";
			WebElement size = dynamicXpath(driver, sizeSelectionXpath, sizeData);
			size.click();
			Thread.sleep(5000);
			customizeButton.click();
			Thread.sleep(3000);
			customizeButton.click();
			Thread.sleep(3000);
			List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
			for (WebElement checkBox : checkBoxes) {
				if (checkBox.isSelected()) {
					System.out.println("Checkbox with value " + checkBox.getAttribute("outerHTML")
							+ "  is selected. Hence Unchecking");
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkBox);
					Thread.sleep(500); // optional
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);
					Thread.sleep(3000);
					System.out.println("Unselected checkbox with id: " + checkBox.getAttribute("id"));
					Thread.sleep(3000);
				}

				else {
					System.out.println("The checkbox is already not checked");
				}

			}

			if (milkData != null && !milkData.trim().isEmpty()) {
				milkSelection(milkData);
			}

			if (syrupData != null && !syrupData.trim().isEmpty()) {
				syrupSelection(syrupData, quantity);
			}

			if (shots != null && !shots.trim().isEmpty()) {
				shotSelection(shots, quantity);
			}

			saveButton.click();
			Thread.sleep(3000);
		}
	}

	public void shotSelection(String shots, int quantity) throws InterruptedException {
		String shotSelectionXpath = "//div[text()='%s']/ancestor::div[contains(@class,'al am c3')]/parent::div/parent::div/parent::div/parent::div//button";
		WebElement shotSelection = dynamicXpath(driver, shotSelectionXpath, shots);
		for (int i = 0; i < quantity; i++) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", shotSelection);
			Thread.sleep(500); // optional
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", shotSelection);
			Thread.sleep(3000);
		}
	}

	public void milkSelection(String milkSelection) throws InterruptedException {
		String milkSelectionXpath = "//div[text()='%s']/ancestor::label/preceding-sibling::input";
		WebElement milkSelectionElement = dynamicXpath(driver, milkSelectionXpath, milkSelection);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", milkSelectionElement);
		Thread.sleep(500); // optional
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", milkSelectionElement);
		Thread.sleep(3000);
	}

	public void syrupSelection(String syrup, int quantity) throws InterruptedException {
		String syrupSelectionXpath = "//div[text()='%s']/ancestor::div[contains(@class,'al am c3')]/parent::div/parent::div/parent::div/parent::div//button";
		WebElement syrupSelectionElement = dynamicXpath(driver, syrupSelectionXpath, syrup);
		for (int i = 0; i < quantity; i++) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", syrupSelectionElement);
			Thread.sleep(500); // optional
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", syrupSelectionElement);
			Thread.sleep(3000);
		}

	}

	public void selectStoreAndDeliveyAddress(String addressData) throws InterruptedException, IOException {
		if (deliveryAddress.getText() != "2 Leman Street") {
			deliveryAddress.click();
			Thread.sleep(3000);
			String addressXpath = "//p[text()='%s']";
			WebElement selectAddress = dynamicXpath(driver, addressXpath, addressData);
			selectAddress.click();
			Thread.sleep(5000);
			System.out.println(deliveryAddress.getText());
			Assert.assertTrue(addressData.equalsIgnoreCase(deliveryAddress.getText()));
		}

	}
}
