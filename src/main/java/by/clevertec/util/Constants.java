package by.clevertec.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    @UtilityClass
    public class Attributes {
        public static final String PHONE_PATTERN = "^\\+375(17|25|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";

    }

    @UtilityClass
    public class Messages {
        public static final String ERROR_READING_PROPERTY_FROM_APPLICATION_YML = "Error reading property from application.yml";

    }

    @UtilityClass
    public class Paths {
        public static final String TEMPLATE_PDF = "/WEB-INF/classes/Clevertec_Template.pdf";
        public static final String OUTPUT_PATH = "resources/";
    }

}
