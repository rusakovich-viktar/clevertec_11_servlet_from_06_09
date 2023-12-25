package by.clevertec.service.impl;

import by.clevertec.config.FlywayConfig;
import by.clevertec.service.DatabaseMigrationService;
import by.clevertec.util.YamlReader;
import org.flywaydb.core.Flyway;

/**
 * Implementation of the {@link DatabaseMigrationService} interface that provides database migration services.
 */
public class DatabaseMigrationServiceImpl implements DatabaseMigrationService {

    public Boolean migrateDatabase() {
        try {
            FlywayConfig flywayConfig = YamlReader.getFlywayConfig();

            String flywayUrl = flywayConfig.getUrl();
            String flywayUser = flywayConfig.getUser();
            String flywayPassword = flywayConfig.getPassword();

            Flyway flyway = Flyway.configure()
                    .dataSource(flywayUrl, flywayUser, flywayPassword)
                    .baselineVersion("0")
                    .load();
            flyway.baseline();
            flyway.migrate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
