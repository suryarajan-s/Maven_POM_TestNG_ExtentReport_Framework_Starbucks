package Pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class reportAnalyticsLoginPage extends BasePageClass {
    public reportAnalyticsLoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[contains(@id,'user-name')]")
    WebElement rAUserName;
    
    @FindBy(xpath = "//input[contains(@id,'org-name')]")
    WebElement rAEnterpriseName;
    
    @FindBy(xpath = "//input[contains(@id,'password-input')]")
    WebElement rAPassword;
    
    @FindBy(xpath = "//oj-button[@id='signinBtn']")
    WebElement rASignInButton;
    
    @FindBy(xpath = "//span[contains(@id,'ui-id-4')]")
    WebElement rALoginID;
    
    public void rALogin(String userName,String enterpriseName, String password) throws InterruptedException {
    	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(70));
    	wait.until(ExpectedConditions.visibilityOf(rAUserName));
    	rAUserName.sendKeys(userName);
    	rAEnterpriseName.sendKeys(enterpriseName);
    	rAPassword.sendKeys(password);
    	rASignInButton.click();
    	WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(150));
		wait1.until(ExpectedConditions.visibilityOf(rALoginID));
    	//add validation for login
    	String applicationID = rALoginID.getText();
    	Assert.assertTrue(applicationID.toLowerCase().indexOf(userName.toLowerCase())==-1);
    	
    	
    	
    }  	
}
