package Pages;

import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;

import static com.codeborne.selenide.Selenide.*;

public class OrdersPage {

    private final SelenideElement deliveredButton = $x("//button[contains(text(),'delivered')]");

    private final SelenideElement table = $("tbody");

    public OrdersPage() {
//        refresh(); //TODO: По хорошему лучше refresh, но он странно отрабатывает
    }

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

    public OrdersPage selectLine(int index) {
        getLine(index).$("input").click();
        return this;
    }

    public OrderPage clickLine(int index) {
        getLine(index).click();
        return new OrderPage();
    }

    public String getDate(int index) {
        return "TODO";
    }

    public String getReference(int index) {
        return "TODO";
    }

    public String getCustomersFullName(int index) {
        return "TODO";
    }

    public String getCustomersAddress(int index) {
        return "TODO";
    }

    public OrdersPage checkMessage(String text) {
        Assert.assertEquals($("h6.MuiTypography-subtitle1").getText(), text);
        return this;
    }
}
