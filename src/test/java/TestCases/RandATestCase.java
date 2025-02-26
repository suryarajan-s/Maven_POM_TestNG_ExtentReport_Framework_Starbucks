package TestCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import util.utility;

public class RandATestCase extends BaseTest {

	@Test
	public void rAndAValidationTestCase() throws IOException, InterruptedException {
		 driver.manage().deleteAllCookies();
		    driver.get(reportAnalyticsURL);
		    driver.manage().window().maximize();

		    try {
		        rALoginPage.rALogin(
		            testData.getTestData("1", "rAUserName"),
		            testData.getTestData("1", "rACompanyName"),
		            testData.getTestData("1", "rAPassword")
		        );
		        extentTestThread.get().log(Status.PASS, "Login action performed successfully");
		    } catch (Throwable t) {
		        extentTestThread.get().log(Status.FAIL, "Error during login: " + t.getMessage());
		        throw t;
		    }
		    
		    utility util = new utility();
		    String orderNumber = util.readOrderNumberFromTextFile();
		    
		    
		    try {
		        guestCheckPage.rAGuestCheckReport(
		            testData.getTestData("1", "rAStoreName"),
		            orderNumber,
		            testData.getTestData("1", "productName"),
		            testData.getTestData("1", "productPrize")
		        );
		        extentTestThread.get().log(Status.PASS, "Successfully validated the order in R&A App");
		    } catch (Throwable t) {
		        extentTestThread.get().log(Status.FAIL, "Error during order validation: " + t.getMessage());
		        throw t;
		    }
		}
}
