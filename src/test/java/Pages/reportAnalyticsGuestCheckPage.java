package Pages;

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
    
    @FindBy(xpath = "//span[text()='Equal']")
    WebElement equalSelection;
    
    @FindBy(xpath = "//input[contains(@id,'check_number_input')]")
    WebElement enterCheckNumber;
    
    @FindBy(xpath = "//div[text()='Name']/following::td[6]//span")
    WebElement productName;
    
    @FindBy(xpath = "//div[text()='Amount']/following::td[7]//span")
    WebElement prize;
    
    public void rAGuestCheckReport(String locationSelection,String orderNumber,String inputProduct, String inputPriceOfProduct) throws InterruptedException {
    	WebDriverWait guestCheckWait= new WebDriverWait(driver, Duration.ofSeconds(20));
    	guestCheckWait.until(ExpectedConditions.elementToBeClickable(guestCheckTab));
    	guestCheckTab.click();
    	Thread.sleep(3000);
    	rALocationSelection.click();
    	Thread.sleep(3000);
    	rALocationInput.sendKeys(locationSelection);
    	Thread.sleep(3000);
    	//see if the store exists then click
    	rALocationInputSelection.click();
    	Thread.sleep(3000);
    	dateSelectionField.click();
    	Thread.sleep(3000);
    	LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");
        String date = currentDate.format(formatter);
    	driver.findElement(By.xpath("//a[text()='"+date+"']")).click();
    	Thread.sleep(3000);
    	selectCheckNumber.click();
    	Thread.sleep(3000);
    	equalSelection.click();
    	Thread.sleep(3000);
    	enterCheckNumber.sendKeys(orderNumber);
    	Thread.sleep(3000);
    	searchButton.click();
    	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(150));
    	wait.until(ExpectedConditions.visibilityOf(searchResult));
    	String orderNumberDisplyed = driver.findElement(By.xpath("//a[text()='"+orderNumber+"']")).getText();
    	System.out.println(orderNumberDisplyed);
    	driver.findElement(By.xpath("//a[text()='"+orderNumber+"']")).click();
    	WebDriverWait waitForResult= new WebDriverWait(driver, Duration.ofSeconds(150));
    	waitForResult.until(ExpectedConditions.visibilityOf(productName));
    	
    	String productNameFromReport=productName.getText();
    	Assert.assertTrue(inputProduct.contains(productNameFromReport));
    	
    	String prizeFromReport = prize.getText();
    	Assert.assertTrue(inputPriceOfProduct.contains(prizeFromReport));
    	
    }  	
}
