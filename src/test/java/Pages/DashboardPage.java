package Pages;

import Elements.MainMenu;
import Service.DriverHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {

    private WebDriver driver;

    public DashboardPage() {
        this.driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public DashboardPage returnToDashboard() {
        MainMenu.dashboardClick();
        return this;
    }

}
