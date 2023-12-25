package by.clevertec.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    @UtilityClass
    public class Attributes {
        public static final String PHONE_PATTERN = "^\\+375(17|25|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";

        public static final String PAGE = "page";
        public static final String PAGE_SIZE = "pageSize";
        public static final String APPLICATION_JSON = "application/json";
        public static final String USER_SERVICE = "userService";
        public static final String APPLICATION_YML = "application.yml";
        public static final String UTF_8 = "UTF-8";
        public static final String APPLICATION_PDF = "application/pdf";
        public static final String FLYWAY = "flyway";
        public static final String JDBC_URL = "jdbcUrl";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String CAPACITY = "capacity";
        public static final String CACHE = "cache";
        public static final String MIGRATION_SERVICE = "migrationService";
    }

    @UtilityClass
    public class Messages {
        public static final String ERROR_READING_PROPERTY_FROM_APPLICATION_YML = "Error reading property from application.yml";
        public static final String MISSING_USER_ID = "Missing user ID";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String AN_ERROR_OCCURRED_WHILE_CREATING_THE_USER = "An error occurred while creating the user: ";
        public static final String INVALID_USER_ID = "Invalid user ID";
        public static final String НЕВЕРНЫЙ_ФОРМАТ_СТРАНИЦЫ_ИЛИ_РАЗМЕРА_СТРАНИЦЫ_НЕОБХОДИМО_ЗАДАТЬ_ОБА_ПАРАМЕТРА_PAGE_И_PAGE_SIZE = "Неверный формат страницы или размера страницы, необходимо задать оба параметра page и pageSize ";
        public static final String ОШИБКА_ПРИ_ГЕНЕРАЦИИ_PDF = "Ошибка при генерации PDF";
        public static final String DATATABLE_NOT_CREATED = "База данных не создана, исключение в migrationService.migrateDatabase()";
        public static final String NAME_SHOULD_NOT_BE_MORE_30_CHARACTER = "Name should not be more 30 character";
        public static final String INCORRECT_EMAIL_FORMAT = "Неверный формат электронной почты";
        public static final String ENTER_PHONE_NUMBER = "Введите номер в международном формате: +375291234567";
        public static final String PRODUCT_NOT_EXIST = "Товар не существует";
        public static final String SAVE_USER_ERROR = "Ошибка при сохранении пользователя: ";
    }

    @UtilityClass
    public class Paths {
        public static final String TEMPLATE_PDF = "/WEB-INF/classes/Clevertec_Template.pdf";
        public static final String OUTPUT_PATH = "resources/";
        public static final String WEB_INF_CLASSES_CLEVERTEC_TEMPLATE_PDF = "/WEB-INF/classes/Clevertec_Template.pdf";
    }

}
