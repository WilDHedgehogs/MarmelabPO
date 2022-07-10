package Elements;

import Pages.CustomersPage;
import Pages.DashboardPage;
import Pages.InvoicesPage;
import Pages.OrdersPage;
import Service.DriverHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainMenu {

    private static WebDriver driver;

    static {
        driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, MainMenu.class);
    }

    @FindBy(css = "a[href='#/']")
    private static WebElement dashboardLink;

    @FindBy(xpath = "//p[text()='Sales']")
    private static WebElement salesTab;

    @FindBy(css = "a[href='#/commands']")
    private static WebElement ordersLink;

    @FindBy(css = "a[href='#/invoices']")
    private static WebElement invoicesLink;

    @FindBy(xpath = "//p[text()='Customers']")
    private static WebElement customersTab;

    @FindBy(css = "a[href='#/customers']")
    private static WebElement customersLink;

    public static DashboardPage dashboardClick() {
        dashboardLink.click();
        return new DashboardPage();
    }

    public static OrdersPage ordersClick() {
        ordersLink.click();
        return new OrdersPage();
    }

    public static InvoicesPage invoicesClick() {
        invoicesLink.click();
        return new InvoicesPage();
    }

    public static CustomersPage customersClick() {
        customersLink.click();
        return new CustomersPage();
    }

    private static void update() {
        //TODO: Доработать обновление
        PageFactory.initElements(driver, MainMenu.class);
    }

    public static boolean isSalesTabRevealed() {
        return ordersLink.isDisplayed(); //TODO: Доработать проверку
    }

    public static void revealSalesTab() {
        salesTab.click();
        update();
    }

    public static boolean isCustomersTabRevealed() {
        return customersLink.isDisplayed(); //TODO: Доработать проверку
    }

    public static void revealCustomersTab() {
        customersTab.click();
        update();
    }

}
