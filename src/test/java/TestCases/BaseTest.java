package TestCases;

import Pages.LoginPage;
import Pages.OrderPlacementPage;
import Pages.reportAnalyticsGuestCheckPage;
import Pages.reportAnalyticsLoginPage;
import Pages.HomePage;
import driverManager.DriverManagerType;
import driverManager.WebDrivers;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import util.TestData;
import util.utility;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class BaseTest {

	protected LoginPage loginPage;
	protected HomePage homePage;
	protected OrderPlacementPage orderPlacepage;
	protected reportAnalyticsLoginPage rALoginPage;
	protected reportAnalyticsGuestCheckPage guestCheckPage;
	WebDriver driver;
	static Properties properties;
	static TestData testData;
	static utility utils;
	static String URL;
	static String reportAnalyticsURL;
	static ExtentSparkReporter extentSparkReporter;
	static ExtentReports extentReports;
	// static ExtentTest extentTest;
	protected static ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<ExtentTest>();

	@BeforeSuite
	public void startReport() throws IOException {
		String currDir = System.getProperty("user.dir");
		testData = new TestData(currDir + "\\TestData\\starBucksTestData.xlsx");
		String reportFolderPath = System.getProperty("user.dir")
				+ "//AutomationReports//StarbucksAutomationReport.html";
		extentSparkReporter = new ExtentSparkReporter(reportFolderPath);
		extentReports = new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);
		extentSparkReporter.config().setDocumentTitle("StarBucks Automation Report");
		extentSparkReporter.config().setReportName("Test Report");
		extentSparkReporter.config().setTheme(Theme.STANDARD);
		extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extentSparkReporter.config().setEncoding("utf-8");
		extentSparkReporter.config().setTimelineEnabled(true); // Enables Timeline graphs
		extentSparkReporter.config().enableOfflineMode(true);
	}

	@BeforeMethod
	public void setup(Method method) {
		driver = WebDrivers.getDriver(DriverManagerType.CHROME);
		extentTestThread.set(extentReports.createTest(method.getName()));
		String propertyPath = System.getProperty("user.dir") + "//src//test//resources//Env.properties";
		properties = utility.loadProperties(propertyPath);
		URL = properties.getProperty("starBucksDeliveryURL");
		reportAnalyticsURL = properties.getProperty("reportAnalyticsURL");
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		orderPlacepage = new OrderPlacementPage(driver);
		rALoginPage = new reportAnalyticsLoginPage(driver);
		guestCheckPage = new reportAnalyticsGuestCheckPage(driver);
	}

	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
	    ExtentTest test = extentTestThread.get();

	    if (result.getStatus() == ITestResult.FAILURE) {
	        test.log(Status.FAIL, "Overall Test Status: FAILED");
	        test.log(Status.FAIL, result.getThrowable());
	        test.addScreenCaptureFromPath(utils.getScreenshot(driver));
	    } else if (result.getStatus() == ITestResult.SUCCESS) {
	        test.log(Status.PASS, "Overall Test Status: PASSED");
	        test.addScreenCaptureFromPath(utils.getScreenshot(driver));
	    } else {
	        test.log(Status.SKIP, "Overall Test Status: SKIPPED");
	        test.addScreenCaptureFromPath(utils.getScreenshot(driver));
	    }
	    
	    driver.quit();
	}

	@AfterSuite
	public void tearDown() {
		// to write or update test information to the reporter
		if (extentReports != null) {
			extentReports.flush();
		}
	}

}