package Pages;

import Service.DriverHandler;
import Service.Operations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.Calendar;

public class InvoicesPage {

    private WebDriver driver;

    @FindBy(css = "tbody")
    private WebElement table;

    @FindBy(css = "#date_gte")
    private WebElement sinceDateInput;

    @FindBy(css = "#date_lte")
    private WebElement beforeDateInput;

    public InvoicesPage() {
        driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public int getTableSize() {
        return table.findElements(By.cssSelector("tr")).size();
    }

    private WebElement getLine(int index) {
        return table.findElement(By.cssSelector("tr:nth-child(" + ++index + ")"));
    }

    public int getLineByName(String fullName) {
        for (int i = 0; i < getTableSize(); i++) {
            WebElement currentLine = getLine(i);
            if (getFullNameFromElement(currentLine).equals(fullName)) {
                return i;
            }
        }
        throw new AssertionError("По данным критериям ["+ fullName + "] элемент не найден");
    }

    private WebElement getPanel(int... index) {
        //Обычно открата только онда панель. По умолчанию будем считать нулевой элемент правильным.
        //Если передается индекс, то выбираем нужный
        //Возможна путаница в индексах, нужно будет что-то придумать.
        if (index.length == 0) {
            index = new int[]{0};
        }
        return table.findElements(By.cssSelector(".RaDatagrid-expandedPanel")).get(index[0]);
    }

    public InvoicesPage selectLine(int index) {
        //Выбор чекбокса в строке
        getLine(index).findElement(By.cssSelector("input")).click();
        return this;
    }

    public InvoicesPage clickLine(int index) {
        getLine(index).click();
        return this;
    }

    public InvoicesPage setSinceDateInput(Calendar date) {
        sinceDateInput.sendKeys(Operations.calendarToString(date));
        return this;
    }

    public InvoicesPage setBeforeDateInput(Calendar date) {
        beforeDateInput.sendKeys(Operations.calendarToString(date));
        return this;
    }

    public int getIdFromLine(int index) {
        return Integer.parseInt(getLine(index).findElement(
                By.cssSelector("td.column-id>span")).getText());
    }

    public Calendar getDateFromLine(int index) {
        return Operations.stringToCalendar(getLine(index).findElement(
                By.cssSelector("td.column-date>span")).getText());
    }

    public String getCustomersFullNameFromLine(int index) {
        return getLine(index).findElement(
                By.cssSelector("td.column-customer_id div.MuiTypography-body2")).getText();
    }

    public String getCustomersAddressFromLine(int index) {
        return getLine(index).findElement(
                By.cssSelector("td.column-customer_id>span:not(.css-4a8j24)")).getText(); //У имени покупателя и адресса почти полностью одинаковые классы.
    }

    public String getOrderFromLine(int index) {
        return getLine(index).findElement(
                By.cssSelector("td.column-command_id>span")).getText();
    }

    public int getIdFromPanel(int... index) {
        String invoice = getPanel().findElement(
                By.xpath("//h6[contains(text(),'Invoice')]")).getText();
        return Integer.parseInt(invoice.substring(invoice.indexOf(" ") + 1));
    }

    public Calendar getDateFromPanel(int... index) {
        return Operations.stringToCalendar(getPanel().findElement(
                By.cssSelector("div.MuiGrid-grid-xs-6>p")).getText());
    }

    public String getCustomersFullNameFromPanel(int... index) {
        String customersInfo = getPanel().findElement(
                By.cssSelector(".MuiGrid-grid-xs-12>p")).getText(); //Также содержит адрес.
        String fullName = customersInfo.substring(0, customersInfo.indexOf(" "));
        fullName = fullName + customersInfo.substring(customersInfo.indexOf(" "), customersInfo.indexOf("\n"));
        return fullName;
    }

    public String getCustomersAddressFromPanel(int... index) {
        return "TODO";
    }

    public String getOrderFromPanel(int... index) {
        return getPanel().findElement(
                By.cssSelector("div.MuiGrid-grid-xs-5>p")).getText();
    }

    private String getFullNameFromElement(WebElement element) {
        return element.findElement(By.cssSelector("td.column-customer_id div.MuiTypography-body2")).getText();
    }

}
