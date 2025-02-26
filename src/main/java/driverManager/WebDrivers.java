package driverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDrivers {public static WebDriver getDriver(DriverManagerType browserName) {
    switch (browserName.getDriver()) {
        case "ChromeDriver":
            ChromeOptions chromeOptions = new ChromeOptions();
           // chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
            
            WebDriverManager.chromedriver().clearDriverCache().setup();
            return new ChromeDriver(chromeOptions);
        case "firefoxdriver":
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        case "iedriver":
            WebDriverManager.iedriver().setup();
            return new InternetExplorerDriver();
    }
    return null;
}

}
