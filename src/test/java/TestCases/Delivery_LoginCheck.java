package TestCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class Delivery_LoginCheck extends BaseTest {

	@Test
	public void DeliveryUberEats() throws IOException, InterruptedException {

		// driver.manage().deleteAllCookies();
		Thread.sleep(3000);
		driver.get(URL);

		
		  driver.manage().window().maximize(); String cookieFilePath =
		  System.getProperty("user.dir") + "//src//main//resources//LoginCookies.csv";
		  loginPage.addCookiesFromCSV(driver, cookieFilePath);
		  driver.navigate().refresh();
		 

		
		/*
		 * loginPage.enterUserDetails(testData.getTestData("1", "userName"),
		 * testData.getTestData("1", "password"));
		 */
		 
		Thread.sleep(5000);

		try {
			loginPage.verifyLogin(testData.getTestData("1", "expectedUserName"));
			extentTestThread.get().log(Status.PASS, "We have Successfully logged In the UberEats App");
		} catch (Throwable t) {
			extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
			throw t;
		}

	}

}
