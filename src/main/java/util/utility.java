package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;




public class utility {
	static WebDriver driver;
    static Properties properties;
    static InputStream input;

    public static Properties loadProperties(String path) {
        try {
            input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            return properties;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    
    public static String getScreenshot(WebDriver driver) throws IOException {
		String screenshotFilePath;
		screenshotFilePath= System.getProperty("user.dir") + "//AutomationReports//" +utility.timeStamp()+".png";
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFile , new File(screenshotFilePath));
		return screenshotFilePath;
	}
	public static String timeStamp() {
		Instant instant= Instant.now();
		return instant.toString().replace("-","_").replace(":", "_").replace(".","_"); 

	}
	
	public String readOrderNumberFromTextFile() throws FileNotFoundException {
			
		
			File file = new File(System.getProperty("user.dir") +"//Warming.txt");
			String addList = null;
			String order = null;
			String orderNumber = "";
			List<String> list = new ArrayList<String>();
			Scanner sc = new Scanner(file);
//			Reading text file and adding to ArrayList
			while (sc.hasNextLine()) {
				addList = sc.nextLine();
				list.add(addList);
			}
			
//			To find the last order
			for (int i = list.size() - 1; i >= 0; i--) {
				if (list.get(i).contains("Order")) {
					order = list.get(i);
					break;
				}
			}
			
//			To separate number from String order
			for (int j = 0; j < order.length(); j++) {
				if (Character.isDigit(order.charAt(j))) {
					orderNumber += order.charAt(j);
				}
			}
			sc.close();
			return orderNumber;

		}


	}
    
   
