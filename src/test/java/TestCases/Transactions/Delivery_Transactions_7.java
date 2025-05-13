package TestCases.Transactions;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import TestCases.BaseTest;

public class Delivery_Transactions_7 extends BaseTest {

	@Test
	public void DeliveryUberEats() throws IOException, InterruptedException {

		// driver.manage().deleteAllCookies();
		Thread.sleep(3000);
		driver.get(URL);

		driver.manage().window().maximize();
		String cookieFilePath = System.getProperty("user.dir") + "//src//main//resources//LoginCookies.csv";
		loginPage.addCookiesFromCSV(driver, cookieFilePath);
		driver.navigate().refresh();

		/*
		 * loginPage.enterUserDetails(testData.getTestData("1", "userName"),
		 * testData.getTestData("1", "password"));
		 */
		Thread.sleep(5000);

		try {
			loginPage.verifyLogin(testData.getTestData("7_TR", "expectedUserName"));
			extentTestThread.get().log(Status.PASS, "We have Successfully logged In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
			throw t;
		}

		try {
			homePage.selectStoreAndDeliveyAddress(testData.getTestData("7_TR", "streetName"));
			extentTestThread.get().log(Status.PASS,
					"We have selected the store and Delivery address from Home page In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during selection of store and delivery address in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			homePage.selectProducts(testData.getTestData("7_TR", "menuData"),
					testData.getTestData("7_TR", "productName"));
			extentTestThread.get().log(Status.PASS, "We have added products from Home page In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during adding products in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			homePage.customizeNonDrinkProduct(testData.getTestData("7_TR", "quantityValueDropDown"));
			extentTestThread.get().log(Status.PASS,
					"We have customized product based on user data In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during customizing products in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			orderPlacepage.addToOrderForNonDrink("7_TR");
			extentTestThread.get().log(Status.PASS,
					"We have added the items and validated as per user data and proceeded with the checkout In the UberEats App");
			Thread.sleep(4000);
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error in adding items  validating and checking out in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			orderPlacepage.setDeliveryPriority(testData.getTestData("7_TR", "deliveryPriority"), "7_TR");
			extentTestThread.get().log(Status.PASS,
					"We have set the delivery options and validated as per user data and proceeded with the checkout In the UberEats App");
			Thread.sleep(4000);
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error in setting the delivery Options and  validating the same in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			orderPlacepage.validatePlacedScheduleOrder("7_TR");
			extentTestThread.get().log(Status.PASS,
					"We have have placed order In the UberEats App and validated the scheduled order In the UberEats App");
			Thread.sleep(4000);
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during during placing order in Uber eats and validating the scheduled order in Uber eats: "
							+ t.getMessage());
			throw t;
		}
		Thread.sleep(3000);

		try {
			orderPlacepage.cancelOrder(testData.getTestData("7_TR", "cancelReason"));
			extentTestThread.get().log(Status.PASS, "We have have cancelled order In the UberEats App ");
			Thread.sleep(4000);
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during during cancelling  order in Uber eats : " + t.getMessage());
			throw t;
		}
		Thread.sleep(3000);

		try {
			loginPage.verifyLogin(testData.getTestData("7_TR", "expectedUserName"));
			extentTestThread.get().log(Status.PASS,
					"Validated whether homepage is landed after cancelling order In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error in homepage validation: " + t.getMessage());
			throw t;
		}

		driver.get(reportAnalyticsURL);
		driver.manage().window().maximize();

		try {
			rALoginPage.rALogin(testData.getTestData("7_TR", "rAUserName"),
					testData.getTestData("7_TR", "rACompanyName"), testData.getTestData("7_TR", "rAPassword"));
			extentTestThread.get().log(Status.PASS, "Login action performed successfully");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
			throw t;
		}

		try {
			guestCheckPage.rAGuestCheckReport(testData.getTestData("7_TR", "rAStoreName"),
					testData.getTestData("7_TR", "productName"), testData.getTestData("7_TR", "productPrize"), "7_TR");
			extentTestThread.get().log(Status.PASS, "Successfully validated the order in R&A App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during order validation: " + t.getMessage());
			throw t;
		}

	}

}
