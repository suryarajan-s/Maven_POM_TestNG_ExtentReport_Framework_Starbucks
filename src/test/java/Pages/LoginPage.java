package Pages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import util.utility;

public class LoginPage extends BasePageClass {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[text()='Log in']")
	WebElement loginBtn;

	@FindBy(xpath = "//input[contains(@title,'email')]")
	WebElement userEmail;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement continueButton;

	@FindBy(xpath = "//input[@id='PASSWORD']")
	WebElement userPassword;

	@FindBy(xpath = "//div[text()='Next']")
	WebElement nextButton;

	@FindBy(xpath = "//button[text()='More options']")
	WebElement moreOptions;

	@FindBy(xpath = "//div/p[text()='Password']")
	WebElement passwordOption;
	
	@FindBy(xpath = "//button[@data-testid='menu-button']")
	WebElement hamBurgerButton;
	
	@FindBy(xpath = "//aside//div//div//div[2]/div")
	WebElement userName;
	
	@FindBy(xpath = "//h1")
	WebElement returnToMainContent;
	

	
	public void type(WebElement element, String username) {
		for (char c: username.toCharArray()) {
			element.sendKeys(String.valueOf(c));
			try {
				Thread.sleep((long)(Math.random()*100)+100);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addCookiesFromCSV(WebDriver driver, String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header row if present
 
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                if (st.countTokens() >= 2) { // Ensure at least name and value exist
                    String name = st.nextToken().trim();
                    String value = st.nextToken().trim();
                    String domain = st.hasMoreTokens() ? st.nextToken().trim() : null;
                    String path = st.hasMoreTokens() ? st.nextToken().trim() : "/";
                    boolean isSecure = st.hasMoreTokens() && Boolean.parseBoolean(st.nextToken().trim());
 
                    Cookie cookie = new Cookie.Builder(name, value)
                            .domain(domain)
                            .path(path)
                            .isSecure(isSecure)
                            .build();
 
                    driver.manage().addCookie(cookie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public void enterUserDetails(String userName, String password) throws InterruptedException {
		webActionHelperMethods.MaximizeBrowser();
		loginBtn.click();
		Thread.sleep(3000);
		userEmail.sendKeys(userName);
		Thread.sleep(3000);
		userEmail.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		
		WebElement captchaCheck = driver.findElement(By.xpath("//button[text()='Start Puzzle']"));
		
		if(captchaCheck.isDisplayed()) {
			Properties prop = utility.loadProperties(System.getProperty("user.dir") + "//src//test//resources//Env.properties");
			driver.get(prop.getProperty("starBucksDeliveryURL"));
			  driver.manage().window().maximize(); String cookieFilePath =
			  System.getProperty("user.dir") + "//src//main//resources//Login.csv";
			  addCookiesFromCSV(driver, cookieFilePath);
			  driver.navigate().refresh();
		}
		
		List<WebElement> conditionCheck = driver.findElements(By.xpath("//input[@id='PASSWORD']"));

		 if (conditionCheck.size() != 0) {
			userPassword.sendKeys(password);
			Thread.sleep(3000);
			nextButton.click();
			Thread.sleep(10000);
		}

		else {
			moreOptions.click();
			Thread.sleep(3000);
			passwordOption.click();
			Thread.sleep(3000);
			userPassword.sendKeys(password);
			Thread.sleep(3000);
			nextButton.click();
			Thread.sleep(10000);
		}

	}
	
	public void verifyLogin(String applicationUserName) throws InterruptedException {
		hamBurgerButton.click();
		Thread.sleep(3000);
		String actualUserName=userName.getText();
		Assert.assertTrue(actualUserName.equalsIgnoreCase(applicationUserName));
		hamBurgerButton.sendKeys(Keys.ESCAPE);
		Thread.sleep(5000);
	}

}
