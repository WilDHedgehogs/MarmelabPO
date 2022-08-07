package Pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$;

public class CustomerPage {
    private final SelenideElement addressInput = $("textarea#address");

    private final SelenideElement saveButton = $("button[type=submit]");

    public String getAddress() {
        return addressInput.getText();
    }

    public CustomerPage() {
//        refresh(); //TODO: По хорошему лучше refresh, но он странно отрабатывает
    }

    @Step("Заполнение адреса [{address}] на странице пользователя")
    public CustomerPage setAddress(String address) {
        addressInput.setValue(Keys.chord(Keys.CONTROL, "a"));
        addressInput.setValue(address);
        return this;
    }

    @Step("Сохранение изменений на странице пользователя")
    public CustomersPage saveChanges() {
        saveButton.click();
        return new CustomersPage();
    }

}
