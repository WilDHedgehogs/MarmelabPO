package Pages;

import Service.DriverHandler;
import Service.Operations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CustomersPage {

    private WebDriver driver;

    @FindBy(css = "input#q")
    private WebElement searchInput;

    @FindBy(css = "tbody")
    private WebElement table;

    @FindBy(css = "input.MuiSelect-nativeInput")
    private WebElement displayedRowsInput;

    public CustomersPage() {
        driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public CustomerPage findCustomer(String fullName) {
        String surName = Operations.getSurNameFromFullName(fullName);
        searchInput.sendKeys(surName);
        By rows = By.cssSelector("tr");
        DriverHandler.getWait().until(ExpectedConditions.numberOfElementsToBeLessThan(
                rows, Integer.parseInt(displayedRowsInput.getAttribute("value"))));

        List<WebElement> filteredTable = table.findElements(rows);
        for (int i = 0; i < filteredTable.size(); i++) {
            WebElement currentLine = getLine(i);
            if (getCustomersFullNameFromLine(i).equals(fullName)) {
                currentLine.click();
                return new CustomerPage();
            }
        }

        throw new AssertionError("По данным критериям ["+ fullName + "] элемент не найден");
    }

    private WebElement getLine(int index) {
        return table.findElement(By.cssSelector("tr:nth-child(" + ++index + ")"));
    }

    public String getCustomersFullNameFromLine(int index) {
        return getLine(index).findElement(
                By.cssSelector("td.column-customer_id div.MuiTypography-body2")).getText();
    }

    private String getFullNameFromElement(WebElement element) {
        return element.findElement(By.cssSelector("td.column-customer_id div.MuiTypography-body2")).getText();
    }
}
