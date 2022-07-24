package Pages;

import com.codeborne.selenide.SelenideElement;
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

    public CustomerPage setAddress(String address) {
        addressInput.setValue(Keys.chord(Keys.CONTROL, "a"));
        addressInput.setValue(address);
        return this;
    }

    public CustomersPage saveChanges() {
        saveButton.click();
        return new CustomersPage();
    }

}
