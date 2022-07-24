package Pages;

import Elements.MainMenu;
public class DashboardPage {

    public DashboardPage() {
//        refresh(); //TODO: По хорошему лучше refresh, но он странно отрабатывает
    }

    public DashboardPage returnToDashboard() {
        new MainMenu().dashboardClick();
        return this;
    }

}
