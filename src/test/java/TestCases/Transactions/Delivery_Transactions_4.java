package TestCases.Transactions;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import TestCases.BaseTest;

public class Delivery_Transactions_4 extends BaseTest {

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
			loginPage.verifyLogin(testData.getTestData("4_TR", "expectedUserName"));
			extentTestThread.get().log(Status.PASS, "We have Successfully logged In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
			throw t;
		}

		try {
			homePage.selectStoreAndDeliveyAddress(testData.getTestData("4_TR", "streetName"));
			extentTestThread.get().log(Status.PASS,
					"We have selected the store and Delivery address from Home page In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during selection of store and delivery address in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			homePage.selectProducts(testData.getTestData("4_TR", "menuData"), testData.getTestData("4_TR", "productName"));
			extentTestThread.get().log(Status.PASS, "We have added products from Home page In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during adding products in Uber eats: " + t.getMessage());
			throw t;
		}
		
		try {
			homePage.customizeSelectedProductWithoutNavigation(testData.getTestData("4_TR", "sizeData"),
					testData.getTestData("4_TR", "milkSelection"), testData.getTestData("4_TR", "syrupSelection"),
					testData.getTestData("4_TR", "shotSelection"), 1,
					testData.getTestData("4_TR", "quantityValueDropDown"), testData.getTestData("4_TR", "beanSelection"));
			extentTestThread.get().log(Status.PASS,
					"We have customized product based on user data In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during customizing products in Uber eats: " + t.getMessage());
			throw t;
		}
		
		try {
			orderPlacepage.addToOrder(testData.getTestData("4_TR", "milkSelection"),
					testData.getTestData("4_TR", "syrupSelection"), testData.getTestData("4_TR", "shotSelection"),
					testData.getTestData("4_TR", "sizeData"), testData.getTestData("4_TR", "beanSelection"),"4_TR");
			extentTestThread.get().log(Status.PASS,
					"We have validated the condiments added and proceeded with the checkout In the UberEats App");
			Thread.sleep(4000);
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error in validating condiments and checking out in Uber eats: " + t.getMessage());
			throw t;
		}
		
		try {
			orderPlacepage.addTip(testData.getTestData("4_TR", "tipValue"),testData.getTestData("4_TR", "tipAmount"));
			extentTestThread.get().log(Status.PASS, "We are able to add Tip and validate the total price change In the UberEats App");
			Thread.sleep(4000);
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error in adding tip in Uber eats: " + t.getMessage());
			throw t;
		}

		
		try {
			orderPlacepage.placeOrder("4_TR");
			extentTestThread.get().log(Status.PASS, "We have placed order In the UberEats App");
			Thread.sleep(4000);
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during placing order in Uber eats: " + t.getMessage());
			throw t;
		}
		Thread.sleep(3000);
		driver.get(reportAnalyticsURL);
		driver.manage().window().maximize();

		try {
			rALoginPage.rALogin(testData.getTestData("4_TR", "rAUserName"), testData.getTestData("4_TR", "rACompanyName"),
					testData.getTestData("4_TR", "rAPassword"));
			extentTestThread.get().log(Status.PASS, "Login action performed successfully");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
			throw t;
		}

		try {
			guestCheckPage.rAGuestCheckReport(testData.getTestData("4_TR", "rAStoreName"),
					testData.getTestData("4_TR", "productName"), testData.getTestData("4_TR", "productPrize"),
					"4_TR");
			extentTestThread.get().log(Status.PASS, "Successfully validated the order in R&A App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during order validation: " + t.getMessage());
			throw t;
		}
	}

}
