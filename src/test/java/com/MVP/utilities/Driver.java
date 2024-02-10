package com.MVP.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;

public class Driver {

    /**
    We make WebDriver private, because we want to close access from outside the class.
    We make a static because we will use it in a static method.
     */
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();
    static String browser;
    /**
    Creating a private constructor, so we are closing access to the
    object of this class from outside the class
     */
    private Driver(){}
    /**
    Create a re-usable utility method which will return same driver instance when
    we call it.
     */
    public static WebDriver getDriver(){
        if (driverPool.get() == null){
            // Determine the mode (headless or not) based on a system property or configuration
            boolean isHeadless = Boolean.parseBoolean(System.getProperty("HEADLESS", "false"));

            if(System.getProperty("BROWSER") == null){
                browser = ConfigurationReader.getProperty("browser");
            }else{
                browser = System.getProperty("BROWSER");
            }
            /**
                Depending on the browserType that will be return from configuration.properties file
                switch statement will determine the case, and open the matching browser
             */

            ChromeOptions options = new ChromeOptions();
            if (isHeadless) {
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
            }

            switch (browser){
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver(options));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver(options));
                    break;
            }
            driverPool.get().manage().window().maximize();
            driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driverPool.get();
    }

    /**
    This method will make sure our driver value is always null after using quit() method
     */
    public static void closeDriver(){
        if (driverPool.get() != null){
             driverPool.get().quit();
             driverPool.remove();
        }
    }

}
