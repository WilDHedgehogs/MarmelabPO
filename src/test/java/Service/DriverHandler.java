package Service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DriverHandler {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void Prepare() {
        if (PropertiesHandler.getValue("browser").equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", PropertiesHandler.getValue("chromeDriverPath"));
            driver = new ChromeDriver();
        } else {
            System.setProperty("webdriver.gecko.driver", PropertiesHandler.getValue("firefoxDriverPath"));
            driver = new FirefoxDriver();
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(PropertiesHandler.getValue("url"));
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            Prepare();
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            Prepare();
        }
        return wait;
    }

    public static void stopDriver() {
        driver.quit();
//        driver = null;
    }

}
