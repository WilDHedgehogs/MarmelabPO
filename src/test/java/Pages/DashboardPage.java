package Pages;

import Elements.MainMenu;
import io.qameta.allure.Step;

public class DashboardPage {

    public DashboardPage() {
//        refresh(); //TODO: По хорошему лучше refresh, но он странно отрабатывает
    }

    @Step("Переход на Dashboard")
    public DashboardPage returnToDashboard() {
        new MainMenu().dashboardClick();
        return this;
    }

}
