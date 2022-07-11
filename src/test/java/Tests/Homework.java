package Tests;

import Elements.MainMenu;
import Pages.*;
import Service.Operations;
import Service.PropertiesHandler;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class Homework {

    @Test
    public void Execution() {
        new LoginPage().login(PropertiesHandler.getValue("login"),
                PropertiesHandler.getValue("password"));

        MainMenu.ordersClick()
                .selectDelivered()
                .selectLine(0)
                .selectLine(1)
                .selectLine(2)
                .checkMessage("3 items selected");

        Calendar sinceDate = new GregorianCalendar();
        sinceDate.set(Calendar.YEAR, sinceDate.get(Calendar.YEAR) - 1);
        sinceDate.set(Calendar.MONTH, 1);
        sinceDate.set(Calendar.DAY_OF_MONTH, 1);

        Calendar beforeDate = new GregorianCalendar();
        beforeDate.set(Calendar.MONTH, 8);
        beforeDate.set(Calendar.DAY_OF_MONTH, 1);

        String firstCustomer = MainMenu.invoicesClick()
                .setSinceDateInput(sinceDate)
                .setBeforeDateInput(beforeDate)
                .getCustomersFullNameFromLine(0);

        assert (!firstCustomer.contains("Korey Mohr"));

        InvoicesPage invoicesPage = new InvoicesPage();
        int randomIndex = new Random().nextInt(invoicesPage.getTableSize());
        int tableId = invoicesPage.getIdFromLine(randomIndex);
        Calendar tableInvoiceDate = invoicesPage.getDateFromLine(randomIndex);
        String tableCustomersName = invoicesPage.getCustomersFullNameFromLine(randomIndex);
        String tableOrderNumber = invoicesPage.getOrderFromLine(randomIndex);
        invoicesPage.clickLine(randomIndex);
        int panelId = invoicesPage.getIdFromPanel();
        Calendar panelInvoiceDate = invoicesPage.getDateFromPanel();
        String panelCustomersName = invoicesPage.getCustomersFullNameFromPanel();
        String panelOrderNumber = invoicesPage.getOrderFromPanel();

        assert tableId == panelId;
        assert Operations.compareDates(tableInvoiceDate, panelInvoiceDate);
        assert tableCustomersName.equals(panelCustomersName);
        assert tableOrderNumber.equals(panelOrderNumber);

        CustomerPage customerPage = MainMenu
                .customersClick()
                .findCustomer(tableCustomersName);
        String oldAddress = customerPage.getAddress();
        String newAddress = "Test Address";

        customerPage.setAddress(newAddress).saveChanges();
        String tableAddress = MainMenu
                .invoicesClick()
                .getCustomersAddressFromLine(invoicesPage.getLineByName(tableCustomersName));
        assert tableAddress.contains(newAddress);

        MainMenu.customersClick()
                .findCustomer(tableCustomersName)
                .setAddress(oldAddress)
                .saveChanges();


    }

}
