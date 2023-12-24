package by.clevertec.util;

import static by.clevertec.util.Constants.Messages.ERROR_READING_PROPERTY_FROM_APPLICATION_YML;

import by.clevertec.config.FlywayConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class YamlReader {


    public static String chooseCacheType() {
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return String.valueOf(data.get("cache"));
        } catch (IOException e) {
            throw new RuntimeException(ERROR_READING_PROPERTY_FROM_APPLICATION_YML, e);
        }
    }

    public static String chooseCacheCapacity() {
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return String.valueOf(data.get("capacity"));
        } catch (IOException e) {
            throw new RuntimeException(ERROR_READING_PROPERTY_FROM_APPLICATION_YML, e);
        }
    }

    public static FlywayConfig getFlywayConfig() {
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, String>> configMap = yaml.load(inputStream);
            System.out.println("Loaded configuration from property.yml:");
            System.out.println(configMap);
            Map<String, String> flywayMap = configMap.get("flyway");

            FlywayConfig flywayConfig = new FlywayConfig();
            flywayConfig.setUrl(flywayMap.get("jdbcUrl"));
            flywayConfig.setUser(flywayMap.get("username"));
            flywayConfig.setPassword(flywayMap.get("password"));

            return flywayConfig;
        } catch (IOException e) {
            throw new RuntimeException(ERROR_READING_PROPERTY_FROM_APPLICATION_YML, e);
        }
    }


}
