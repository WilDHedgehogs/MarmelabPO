package Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {

    private static final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final String envPath = rootPath + "environment.properties";
    private static final Properties properties = new Properties();

    static
    {
        try {
            properties.load(new FileInputStream(envPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getValue(String name) {
        return properties.getProperty(name);
    }

    public static void setValue(String name, String value) {
        properties.setProperty(name, value);
    }
}
