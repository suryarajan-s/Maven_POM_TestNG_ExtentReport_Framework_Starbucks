package TestCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class DeliveryTestCase extends BaseTest {
	

	@Test
	public void DeliveryUberEats() throws IOException, InterruptedException {
		driver.manage().deleteAllCookies();
		driver.get(URL);
		try {
			loginPage.enterUserDetails(testData.getTestData("1", "userName"), testData.getTestData("1", "password"));
			extentTestThread.get().log(Status.PASS, "We have Successfully logged In the UberEats App");
		}  catch (Throwable t) {
	        extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
	        throw t;
		}
		try {
			homePage.selectProducts();
			extentTestThread.get().log(Status.PASS, "We have added products from Home page In the UberEats App");
		} catch (Throwable t) {
	        extentTestThread.get().log(Status.FAIL, "Error during adding products in Uber eats: " + t.getMessage());
	        throw t;
		}
		try {
			orderPlacepage.placeOrder();
			extentTestThread.get().log(Status.PASS, "We have placed order In the UberEats App");
			Thread.sleep(4000);
		}catch (Throwable t) {
	        extentTestThread.get().log(Status.FAIL, "Error during placing order in Uber eats: " + t.getMessage());
	        throw t;
		}

	}

}
