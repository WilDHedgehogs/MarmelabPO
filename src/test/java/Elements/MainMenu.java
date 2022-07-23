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

    private final WebDriver driver;

    @FindBy(css = "a[href='#/']")
    private WebElement dashboardLink;

    @FindBy(xpath = "//p[text()='Sales']")
    private WebElement salesTab;

    @FindBy(css = "a[href='#/commands']")
    private WebElement ordersLink;

    @FindBy(css = "a[href='#/invoices']")
    private WebElement invoicesLink;

    @FindBy(xpath = "//p[text()='Customers']")
    private WebElement customersTab;

    @FindBy(css = "a[href='#/customers']")
    private WebElement customersLink;

    public MainMenu() {
        driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public DashboardPage dashboardClick() {
        dashboardLink.click();
        return new DashboardPage();
    }

    public OrdersPage ordersClick() {
        ordersLink.click();
        return new OrdersPage();
    }

    public InvoicesPage invoicesClick() {
        invoicesLink.click();
        return new InvoicesPage();
    }

    public CustomersPage customersClick() {
        customersLink.click();
        return new CustomersPage();
    }

    private void update() {
        //TODO: Доработать обновление
        PageFactory.initElements(driver, MainMenu.class);
    }

    public boolean isSalesTabRevealed() {
        return ordersLink.isDisplayed(); //TODO: Доработать проверку
    }

    public void revealSalesTab() {
        salesTab.click();
        update();
    }

    public boolean isCustomersTabRevealed() {
        return customersLink.isDisplayed(); //TODO: Доработать проверку
    }

    public void revealCustomersTab() {
        customersTab.click();
        update();
    }

}
