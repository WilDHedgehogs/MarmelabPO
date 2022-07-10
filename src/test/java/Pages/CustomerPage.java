package Pages;

import Service.DriverHandler;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomerPage {

    private final WebDriver driver;

    @FindBy(css = "textarea#address")
    private WebElement addressInput;

    @FindBy(css = "button[type=submit]")
    private WebElement saveButton;

    public CustomerPage() {
        driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getAddress() {
        return addressInput.getText();
    }

    public CustomerPage setAddress(String address) {
        addressInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        addressInput.sendKeys(address);
        return this;
    }

    public CustomersPage saveChanges() {
        saveButton.click();
        return new CustomersPage();
    }

}
