package Tests;

import Pages.LoginPage;
import Service.PropertiesHandler;
import org.junit.Test;

public class Homework {

    @Test
    public void Execution() {
        new LoginPage().login(PropertiesHandler.getValue("login"), PropertiesHandler.getValue("password"));
    }

}
