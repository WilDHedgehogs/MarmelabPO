package Elements;

import Pages.CustomersPage;
import Pages.DashboardPage;
import Pages.InvoicesPage;
import Pages.OrdersPage;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

public class MainMenu {

    private final SelenideElement dashboardLink = $("a[href='#/']");

    private final SelenideElement salesTab = $x("//p[text()='Sales']");

    private final SelenideElement ordersLink = $("a[href='#/commands']");

    private final SelenideElement invoicesLink = $("a[href='#/invoices']");

    private final SelenideElement customersTab = $("//p[text()='Customers']");

    private final SelenideElement customersLink = $("a[href='#/customers']");

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

    public boolean isSalesTabRevealed() {
        return ordersLink.isDisplayed(); //TODO: Доработать проверку
    }

    public void revealSalesTab() {
        salesTab.click();
    }

    public boolean isCustomersTabRevealed() {
        return customersLink.isDisplayed(); //TODO: Доработать проверку
    }

    public void revealCustomersTab() {
        customersTab.click();
    }

}
