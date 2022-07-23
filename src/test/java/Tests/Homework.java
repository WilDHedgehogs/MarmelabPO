package Tests;

import Elements.MainMenu;
import Pages.*;
import Service.DriverHandler;
import Service.Operations;
import Service.PropertiesHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(value = Parameterized.class)
public class Homework {
    private String login;
    private String password;
    @Before
    public void init() {
        DriverHandler.Prepare();
        login = PropertiesHandler.getValue("login");
        password = PropertiesHandler.getValue("password");
    }

    @After
    public void release() {
        DriverHandler.stopDriver();
        login = null;
        password = null;
    }

    public Homework(String browser) {
        PropertiesHandler.setValue("browser", browser);
    }

    @Parameterized.Parameters(name = "browser: {0}")
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
                {"chrome"},
                {"firefox"}
        });
    }

    @Test
    public void Execution() {
        new LoginPage().login(login, password);
        MainMenu mainMenu = new MainMenu();

        mainMenu.ordersClick()
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

        String firstCustomer = mainMenu.invoicesClick()
                .setSinceDateInput(sinceDate)
                .setBeforeDateInput(beforeDate)
                .getCustomersFullNameFromLine(0);

        Assert.assertNotEquals(firstCustomer, "Korey Mohr");

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

        Assert.assertEquals(tableId, panelId);
        Assert.assertTrue(Operations.compareDates(tableInvoiceDate, panelInvoiceDate));
        Assert.assertEquals(tableCustomersName, panelCustomersName);
        Assert.assertEquals(tableOrderNumber, panelOrderNumber);

        CustomerPage customerPage = mainMenu
                .customersClick()
                .findCustomer(tableCustomersName);
        String oldAddress = customerPage.getAddress();
        String newAddress = "Test Address";

        customerPage.setAddress(newAddress).saveChanges();
        String tableAddress = mainMenu
                .invoicesClick()
                .getCustomersAddressFromLine(invoicesPage.getLineByName(tableCustomersName));
        Assert.assertTrue(tableAddress.contains(newAddress));

        mainMenu.customersClick()
                .findCustomer(tableCustomersName)
                .setAddress(oldAddress)
                .saveChanges();
    }

}
