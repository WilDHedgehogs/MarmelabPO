package Pages;

import Service.Operations;
import Service.PropertiesHandler;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import java.util.Calendar;
import static com.codeborne.selenide.Selenide.$;

public class InvoicesPage {

    private final SelenideElement table = $("tbody");

    private final SelenideElement sinceDateInput = $("#date_gte");

    private final SelenideElement beforeDateInput = $("#date_lte");

    public int getTableSize() {
        return table.$$("tr").shouldBe(CollectionCondition.sizeGreaterThan(0)).size();
    }

    private SelenideElement getLine(int index) {
        return table.$("tr:nth-child(" + ++index + ")");
    }

    public InvoicesPage() {
//        refresh(); //TODO: По хорошему лучше refresh, но он странно отрабатывает
    }

    public int getLineByName(String fullName) {
        for (int i = 0; i < getTableSize(); i++) {
            SelenideElement currentLine = getLine(i);
            if (getFullNameFromElement(currentLine).equals(fullName)) {
                return i;
            }
        }
        throw new AssertionError("По данным критериям ["+ fullName + "] элемент не найден");
    }

    private SelenideElement getPanel(int... index) {
        //Обычно открата только онда панель. По умолчанию будем считать нулевой элемент правильным.
        //Если передается индекс, то выбираем нужный
        //Возможна путаница в индексах, нужно будет что-то придумать.
        if (index.length == 0) {
            index = new int[]{0};
        }
        return table.$$(".RaDatagrid-expandedPanel").get(index[0]);
    }

    public InvoicesPage selectLine(int index) {
        //Выбор чекбокса в строке
        getLine(index).$("input").click();
        return this;
    }

    public InvoicesPage clickLine(int index) {
        SelenideElement line = getLine(index);
        if (PropertiesHandler.getValue("browser").equals("firefox")) {
            line.$("svg").click();
        } else {
           line.click();
        }

        return this;
    }

    public InvoicesPage setSinceDateInput(Calendar date) {
        sinceDateInput.setValue(Operations.calendarToString(date));
        return this;
    }

    public InvoicesPage setBeforeDateInput(Calendar date) {
        beforeDateInput.setValue(Operations.calendarToString(date));
        return this;
    }

    public int getIdFromLine(int index) {
        return Integer.parseInt(getLine(index).$("td.column-id>span").getText());
    }

    public Calendar getDateFromLine(int index) {
        return Operations.stringToCalendar(getLine(index).$("td.column-date>span").getText());
    }

    public String getCustomersFullNameFromLine(int index) {
        return getLine(index).$("td.column-customer_id div.MuiTypography-body2").getText();
    }

    public String getCustomersAddressFromLine(int index) {
        return getLine(index).$("td.column-customer_id>span:not(.css-4a8j24)").getText(); //У имени покупателя и адресса почти полностью одинаковые классы.
    }

    public String getOrderFromLine(int index) {
        return getLine(index).$("td.column-command_id>span").getText();
    }

    public int getIdFromPanel(int... index) {
        String invoice = getPanel().$x(".//h6[contains(text(),'Invoice')]").getText();
        return Integer.parseInt(invoice.substring(invoice.indexOf(" ") + 1));
    }

    public Calendar getDateFromPanel(int... index) {
        return Operations.stringToCalendar(getPanel().$("div.MuiGrid-grid-xs-6>p").getText());
    }

    public String getCustomersFullNameFromPanel(int... index) {
        String customersInfo = getPanel().$(".MuiGrid-grid-xs-12>p").getText(); //Также содержит адрес.
        String fullName = customersInfo.substring(0, customersInfo.indexOf(" "));
        fullName = fullName + customersInfo.substring(customersInfo.indexOf(" "), customersInfo.indexOf("\n"));
        return fullName;
    }

    public String getCustomersAddressFromPanel(int... index) {
        return "TODO";
    }

    public String getOrderFromPanel(int... index) {
        return getPanel().$("div.MuiGrid-grid-xs-5>p").getText();
    }

    private String getFullNameFromElement(SelenideElement element) {
        return element.$("td.column-customer_id div.MuiTypography-body2").getText();
    }

}
