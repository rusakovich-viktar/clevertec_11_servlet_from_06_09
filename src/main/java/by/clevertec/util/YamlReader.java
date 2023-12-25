package by.clevertec.util;

import static by.clevertec.util.Constants.Attributes.APPLICATION_YML;
import static by.clevertec.util.Constants.Attributes.CACHE;
import static by.clevertec.util.Constants.Attributes.CAPACITY;
import static by.clevertec.util.Constants.Attributes.FLYWAY;
import static by.clevertec.util.Constants.Attributes.JDBC_URL;
import static by.clevertec.util.Constants.Attributes.PASSWORD;
import static by.clevertec.util.Constants.Attributes.USERNAME;
import static by.clevertec.util.Constants.Messages.ERROR_READING_PROPERTY_FROM_APPLICATION_YML;

import by.clevertec.config.FlywayConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;

@UtilityClass
public class YamlReader {

    public static String chooseCacheType() {
        return getString(CACHE);
    }

    public static String chooseCacheCapacity() {
        return getString(CAPACITY);
    }

    public static FlywayConfig getFlywayConfig() {
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream(APPLICATION_YML)) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, String>> configMap = yaml.load(inputStream);
            System.out.println(configMap);
            Map<String, String> flywayMap = configMap.get(FLYWAY);

            FlywayConfig flywayConfig = new FlywayConfig();
            flywayConfig.setUrl(flywayMap.get(JDBC_URL));
            flywayConfig.setUser(flywayMap.get(USERNAME));
            flywayConfig.setPassword(flywayMap.get(PASSWORD));

            return flywayConfig;
        } catch (IOException e) {
            throw new RuntimeException(ERROR_READING_PROPERTY_FROM_APPLICATION_YML, e);
        }
    }

    private static String getString(String cache) {
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream(APPLICATION_YML)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return String.valueOf(data.get(cache));
        } catch (IOException e) {
            throw new RuntimeException(ERROR_READING_PROPERTY_FROM_APPLICATION_YML, e);
        }
    }

}
