package TestCases.Reorder;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import TestCases.BaseTest;

public class Delivery_Reorder_1 extends BaseTest {

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
			loginPage.verifyLogin(testData.getTestData("1_RO", "expectedUserName"));
			extentTestThread.get().log(Status.PASS, "We have Successfully logged In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
			throw t;
		}

		try {
			homePage.selectStoreAndDeliveyAddress(testData.getTestData("1_RO", "streetName"));
			extentTestThread.get().log(Status.PASS,
					"We have selected the store and Delivery address from Home page In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during selection of store and delivery address in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			homePage.selectProducts(testData.getTestData("1_RO", "menuData"), testData.getTestData("1_RO", "productName"));
			extentTestThread.get().log(Status.PASS, "We have added products from Home page In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during adding products in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			homePage.customizeSelectedProductWithoutNavigation(testData.getTestData("1_RO", "sizeData"),
					testData.getTestData("1_RO", "milkSelection"), testData.getTestData("1_RO", "syrupSelection"),
					testData.getTestData("1_RO", "shotSelection"), 1, testData.getTestData("1_RO", "quantityValueDropDown"),
					testData.getTestData("1_RO", "beanSelection"));
			extentTestThread.get().log(Status.PASS,
					"We have customized product based on user data In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during customizing products in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			orderPlacepage.addToOrder(testData.getTestData("1_RO", "milkSelection"),
					testData.getTestData("1_RO", "syrupSelection"), testData.getTestData("1_RO", "shotSelection"),
					testData.getTestData("1_RO", "sizeData"), testData.getTestData("1_RO", "beanSelection"), "1_RO");
			extentTestThread.get().log(Status.PASS,
					"We have validated the condiments added and proceeded with the checkout In the UberEats App");
			Thread.sleep(4000);
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error in validating condiments and checking out in Uber eats: " + t.getMessage());
			throw t;
		}

		try {
			orderPlacepage.placeOrder("1_RO");
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
			rALoginPage.rALogin(testData.getTestData("1_RO", "rAUserName"), testData.getTestData("1_RO", "rACompanyName"),
					testData.getTestData("1_RO", "rAPassword"));
			extentTestThread.get().log(Status.PASS, "Login action performed successfully");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
			throw t;
		}

		try {
			guestCheckPage.rAGuestCheckReport(testData.getTestData("1_RO", "rAStoreName"),
					testData.getTestData("1_RO", "productName"), testData.getTestData("1_RO", "productPrize"),
					"1_RO");
			extentTestThread.get().log(Status.PASS, "Successfully validated the order in R&A App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during order validation: " + t.getMessage());
			throw t;
		}
		
		driver.get(URL);

		driver.manage().window().maximize();
		String cookieFilePath1 = System.getProperty("user.dir") + "//src//main//resources//LoginCookies.csv";
		loginPage.addCookiesFromCSV(driver, cookieFilePath1);
		driver.navigate().refresh();

		/*
		 * loginPage.enterUserDetails(testData.getTestData("1", "userName"),
		 * testData.getTestData("1", "password"));
		 */
		Thread.sleep(5000);

		try {
			loginPage.verifyLogin(testData.getTestData("1_RO", "expectedUserName"));
			extentTestThread.get().log(Status.PASS, "We have Successfully logged In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
			throw t;
		}

		try {
			homePage.selectStoreAndDeliveyAddress(testData.getTestData("1_RO", "streetName"));
			extentTestThread.get().log(Status.PASS,
					"We have selected the store and Delivery address from Home page In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL,
					"Error during selection of store and delivery address in Uber eats: " + t.getMessage());
			throw t;
		}
	}
	
	
	
	

}
