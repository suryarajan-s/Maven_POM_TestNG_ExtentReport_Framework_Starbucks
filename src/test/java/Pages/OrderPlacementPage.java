package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderPlacementPage extends BasePageClass {
    public OrderPlacementPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[text()='Place order']")
    WebElement placeOrderButton;
    
    
    public void placeOrder() throws InterruptedException {
    	placeOrderButton.click();
    	Thread.sleep(10000);
    	
    }  	
}
