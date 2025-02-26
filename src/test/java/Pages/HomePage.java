package Pages;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import util.TestData;

public class HomePage extends BasePageClass {
    public HomePage(WebDriver driver) {
        super(driver);
    }
    

    @FindBy(xpath = "//div[text()='Bakery Treats']")
    WebElement selectBakeryTreat;

    @FindBy(xpath = "//span[text()='Chocolate Caramel Shortbread']")
    WebElement selectProduct;
    
    @FindBy(xpath = "//button[@aria-label='Add 1 to order']")
    WebElement addToCartButton;
    
    @FindBy(xpath = "//a[text()='Go to checkout']")
    WebElement checkOutButton;
    
    @FindBy(xpath = "//button[text()='Skip']")
    WebElement skipbutton;
    
    @FindBy(xpath ="//div[contains(@data-testid,'delivery-address')]")
    WebElement deliveryAddress;
    
    @FindBy(xpath = "//p[text()='2 Leman Street']")
    WebElement selectAddress;
    
    @FindBy(xpath = "//span[text()='Chocolate Caramel Shortbread']/parent::*/following-sibling::*/span")
    WebElement productPrize;
    

    public void selectProducts() throws InterruptedException, IOException {
    	if(deliveryAddress.getText()!= "2 Leman Street") {
    		deliveryAddress.click();
    		Thread.sleep(3000);
    		selectAddress.click();
    		Thread.sleep(5000);
    		System.out.println(deliveryAddress.getText());
    	}
    	selectBakeryTreat.click();
    	Thread.sleep(5000);
    	TestData data = new TestData(System.getProperty("user.dir") + "\\TestData\\starBucksTestData.xlsx");
		  String productName = selectProduct.getText();
		  data.writeTestData("1", "productName", productName);
		  String price = productPrize.getText();
		  data.writeTestData("1", "productPrize", price);
    	selectProduct.click();
    	Thread.sleep(5000);
    	addToCartButton.click();
    	Thread.sleep(7000);
    	checkOutButton.click();
    	Thread.sleep(5000);
    	if(skipbutton.isDisplayed()) {
    		skipbutton.click();
    		Thread.sleep(5000);
    	}
    	
    }
}
