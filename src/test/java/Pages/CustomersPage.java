package Pages;

import Service.Operations;
import Service.PropertiesHandler;
import com.codeborne.selenide.*;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class CustomersPage {

    private final SelenideElement searchInput = $("input#q");
    private final SelenideElement table = $("tbody");

    private final SelenideElement displayedRowsInput = $("input.MuiSelect-nativeInput");

    public CustomersPage() {
//        refresh(); //TODO: По хорошему лучше refresh, но он странно отрабатывает
    }

    @Step("Поиск покупателя {fullName} на странице Customers")
    public CustomerPage findCustomer(String fullName) {
        try {
            if (!searchInput.getAttribute("value").equals("")) {
                int currentTableSize = getTableSize();
                $("svg.RaResettableTextField-clearIcon").click(); //Кнопка очистки поля ввода, потому что clear() не очищает
                table.$$("tr").shouldHave(CollectionCondition.sizeGreaterThan(currentTableSize));
            }
        } catch (org.openqa.selenium.NoSuchElementException ignored) {}

        String surName = Operations.getSurNameFromFullName(fullName);
        int defaultTableSize = getTableSize();
        searchInput.setValue(surName);

        ElementsCollection filteredTable = table.$$("tr").
                shouldHave(CollectionCondition.sizeLessThan(defaultTableSize));

        for (int i = 0; i < filteredTable.size(); i++) {
            SelenideElement currentLine = getLine(i);
            if (getCustomersFullNameFromLine(i).equals(fullName)) {
                if (PropertiesHandler.getValue("browser").equals("firefox")) {
                    currentLine.$(".column-customer_id").click();
                } else {
                    currentLine.click();
                }

                return new CustomerPage();
            }
        }

        throw new AssertionError("По данным критериям ["+ fullName + "] элемент не найден");
    }

    private int getTableSize() {
        return table.$$("tr").shouldHave(CollectionCondition.sizeGreaterThan(0)).size();
    }

    private SelenideElement getLine(int index) {
        return table.$("tr:nth-child(" + ++index + ")");
    }

    @Step("Получение имени пользователя из таблицы на странице Customers по индексу {index}")
    public String getCustomersFullNameFromLine(int index) {
        return getLine(index).$("td.column-customer_id div.MuiTypography-body2").getText();
    }

    private String getFullNameFromElement(SelenideElement element) {
        return element.$("td.column-customer_id div.MuiTypography-body2").getText();
    }
}
