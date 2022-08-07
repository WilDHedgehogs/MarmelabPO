package Elements;

import Pages.CustomersPage;
import Pages.DashboardPage;
import Pages.InvoicesPage;
import Pages.OrdersPage;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class MainMenu {

    private final SelenideElement dashboardLink = $("a[href='#/']");

    private final SelenideElement salesTab = $x("//p[text()='Sales']");

    private final SelenideElement ordersLink = $("a[href='#/commands']");

    private final SelenideElement invoicesLink = $("a[href='#/invoices']");

    private final SelenideElement customersTab = $("//p[text()='Customers']");

    private final SelenideElement customersLink = $("a[href='#/customers']");

    @Step("Переход на Dashboard")
    public DashboardPage dashboardClick() {
        dashboardLink.click();
        return new DashboardPage();
    }

    @Step("Переход на Orders")
    public OrdersPage ordersClick() {
        if (!isSalesTabRevealed()) {
            revealSalesTab();
        }
        ordersLink.click();
        return new OrdersPage();
    }

    @Step("Переход на Invoices")
    public InvoicesPage invoicesClick() {
        if (!isSalesTabRevealed()) {
            revealSalesTab();
        }
        invoicesLink.click();
        return new InvoicesPage();
    }

    @Step("Переход на Customers")
    public CustomersPage customersClick() {
        if (!isCustomersTabRevealed()) {
            revealCustomersTab();
        }
        customersLink.click();
        return new CustomersPage();
    }

    public boolean isSalesTabRevealed() {
        return ordersLink.isDisplayed();
    }

    @Step("Отобразить Sales")
    public void revealSalesTab() {
        salesTab.click();
    }

    public boolean isCustomersTabRevealed() {
        return customersLink.isDisplayed();
    }

    @Step("Отобразить Customers")
    public void revealCustomersTab() {
        customersTab.click();
    }

}
