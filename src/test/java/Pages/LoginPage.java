package Pages;

import Service.DriverHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    @FindBy(css = "#username")
    private WebElement userNameInput;

    @FindBy(css = "#password")
    private WebElement passwordInput;

    @FindBy(css = "button[type=submit]")
    private  WebElement signInButton;

    public LoginPage() {
        driver = DriverHandler.getDriver();
        PageFactory.initElements(driver, this);
    }

    public DashboardPage login(String login, String password) {
        userNameInput.sendKeys(login);
        passwordInput.sendKeys(password);
        signInButton.click();
        return new DashboardPage(); //При ошибке будет оставаться на той-же странице, при успехе будет переход на следующую
    }

}
