package Pages;

import Elements.MainMenu;
import Service.DriverHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {

    private final WebDriver driver;

    public DashboardPage() {
        this.driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public DashboardPage returnToDashboard() {
        new MainMenu().dashboardClick();
        return this;
    }

}
