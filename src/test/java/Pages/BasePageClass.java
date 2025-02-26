package Pages;

import actionHelper.WebActionHelperMethods;
import util.TestData;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePageClass {
    protected WebDriver driver;
    protected TestData data;
    static WebActionHelperMethods webActionHelperMethods;
    

    public BasePageClass(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        webActionHelperMethods= new WebActionHelperMethods(driver);
    }

}
