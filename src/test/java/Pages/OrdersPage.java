package Pages;

import Service.DriverHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrdersPage {

    private WebDriver driver;

    @FindBy(xpath = "//button[contains(text(),'delivered')]")
    private WebElement deliveredButton;

    @FindBy(css = "tbody")
    private WebElement table;

    public OrdersPage() {
        driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public OrdersPage selectDelivered() {
        deliveredButton.click();
        return this;
    }

    public int getTableSize() {
        return table.findElements(By.cssSelector("tr")).size();
    }

    private WebElement getLine(int index) {
        return table.findElement(By.cssSelector("tr:nth-child(" + index + ")"));
    }

    public OrdersPage selectLine(int index) {
        getLine(index).findElement(By.cssSelector("input")).click();
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
        assert driver.findElement(By.cssSelector("h6.MuiTypography-subtitle1"))
                .getText().equals(text);
        return this;
    }
}
