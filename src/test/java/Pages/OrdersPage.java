package Pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.Assert;

import static com.codeborne.selenide.Selenide.*;

public class OrdersPage {

    private final SelenideElement deliveredButton = $x("//button[contains(text(),'delivered')]");

    private final SelenideElement table = $("tbody");

    public OrdersPage() {
//        refresh(); //TODO: По хорошему лучше refresh, но он странно отрабатывает
    }

    @Step("Выбор вкладки Delivered на странице Orders")
    public OrdersPage selectDelivered() {
        deliveredButton.click();
        return this;
    }

    public int getTableSize() {
        return table.$$("tr").size();
    }

    private SelenideElement getLine(int index) {
        return table.$("tr:nth-child(" + ++index + ")");
    }

    @Step("Выбор строки {index} на странице Orders")
    public OrdersPage selectLine(int index) {
        getLine(index).$("input").click();
        return this;
    }

    @Step("Клик по строке {index} на странице Orders")
    public OrderPage clickLine(int index) {
        getLine(index).click();
        return new OrderPage();
    }

    @Step("Получение даты из строки {index} на странице Orders")
    public String getDate(int index) {
        return "TODO";
    }

    @Step("Получение номера документа из строки {index} на странице Invoices")
    public String getReference(int index) {
        return "TODO";
    }

    @Step("Получение имени покупателя из строки {index} на странице Invoices")
    public String getCustomersFullName(int index) {
        return "TODO";
    }

    @Step("Получение адреса покупателя из строки {index} на странице Invoices")
    public String getCustomersAddress(int index) {
        return "TODO";
    }

    @Step("Проверка всплывающего сообщения")
    public OrdersPage checkMessage(String text) {
        Assert.assertEquals($("h6.MuiTypography-subtitle1").getText(), text);
        return this;
    }
}
