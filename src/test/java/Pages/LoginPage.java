package Pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement userNameInput = $("#username");

    private final SelenideElement passwordInput = $("#password");

    private  final SelenideElement signInButton = $("button[type=submit]");

    public LoginPage() {
//        refresh(); //TODO: По хорошему лучше refresh, но он странно отрабатывает
    }

    public DashboardPage login(String login, String password) {
        userNameInput.setValue(login);
        passwordInput.setValue(password);
        signInButton.click();
        return new DashboardPage(); //При ошибке будет оставаться на той-же странице, при успехе будет переход на следующую
    }

}
