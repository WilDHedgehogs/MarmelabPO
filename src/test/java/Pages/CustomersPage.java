package Pages;

import Service.DriverHandler;
import Service.Operations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CustomersPage {

    private WebDriver driver;

    @FindBy(css = "input#q")
    private WebElement searchInput;

    @FindBy(css = "tbody")
    private WebElement table;

    public CustomersPage() {
        driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public CustomerPage findCustomer(String fullName) {
        String surName = Operations.getSurNameFromFullName(fullName);
        searchInput.sendKeys(surName);

        List<WebElement> filteredTable = table.findElements(By.cssSelector("tr"));
        for (int i = 0; i < filteredTable.size(); i++) {
            WebElement currentLine = getLine(i + 1); //В вебе нумерация с единицы?
            if (currentLine.getText().equals(fullName)) {
                currentLine.click();
                break;
            }
        }

        return new CustomerPage();
    }

    private WebElement getLine(int index) {
        return table.findElement(By.cssSelector("tr:nth-child(" + index + ")"));
    }
}
