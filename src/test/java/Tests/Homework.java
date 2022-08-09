package Tests;

import Elements.MainMenu;
import Pages.*;
import Service.Operations;
import Service.PropertiesHandler;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit.TextReport;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@RunWith(value = Parameterized.class)
public class Homework {
    private String login;
    private String password;

    @Rule
    public TextReport textReport = new TextReport();

    @Before
    public void init() {
        if (Boolean.parseBoolean(PropertiesHandler.getValue("use_docker"))) {
            // Url удалённого веб драйвера
            Configuration.remote = "http://localhost:4444/wd/hub";
            // Определяем версию браузера
            if (Configuration.browser.equals("chrome")) {
                Configuration.browserVersion = PropertiesHandler.getValue("chrome_version");
            } else {
                Configuration.browserVersion = PropertiesHandler.getValue("firefox_version");
            }
            // Создаём объект класса DesiredCapabilities, используется как настройка вашей конфигурации с помощью пары ключ-значение
            DesiredCapabilities capabilities = new DesiredCapabilities();
            // Включить поддержку отображения экрана браузера во время выполнения теста
            capabilities.setCapability("enableVNC",
                    Boolean.parseBoolean(PropertiesHandler.getValue("enable_vnc")));
            // Включение записи видео в процессе выполнения тестов
            capabilities.setCapability("enableVideo",
                    Boolean.parseBoolean(PropertiesHandler.getValue("enable_video")));
            // Переопределяем Browser capabilities
            Configuration.browserCapabilities = capabilities;
            Configuration.baseUrl = PropertiesHandler.getValue("url");
            Configuration.timeout = Long.parseLong(PropertiesHandler.getValue("timeout")) * 1000;
        }
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false));
        open(PropertiesHandler.getValue("url"));
        PropertiesHandler.setValue("browser", Configuration.browser);
        login = PropertiesHandler.getValue("login");
        password = PropertiesHandler.getValue("password");
    }

    @After
    public void release() {
        closeWebDriver();
        login = null;
        password = null;
    }

    public Homework(String browser) {
        Configuration.browser = browser;
    }

    @Parameterized.Parameters(name = "browser: {0}")
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
                {"firefox"},
                {"chrome"}
        });
    }

    @Test
    @Description("Выполнение домашнего задания")
    @Epic("Проверки на сайте")
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
