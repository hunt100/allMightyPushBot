package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

    private PropertyUtils() {
    }

    public static Properties getProperties() {
        try (InputStream input = PropertyUtils.class.getClassLoader().getResourceAsStream("local.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                System.out.println("Unable to find .properties file");
            }

            properties.load(input);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPropertyByKey(String key) {
        return getProperties().getProperty(key);
    }
}
