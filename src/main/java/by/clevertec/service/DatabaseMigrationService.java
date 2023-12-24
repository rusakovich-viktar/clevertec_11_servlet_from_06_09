package by.clevertec.service;

public interface DatabaseMigrationService {
    /**
     * Performs database migration to apply necessary schema changes and updates.
     */
    Boolean migrateDatabase();
}
