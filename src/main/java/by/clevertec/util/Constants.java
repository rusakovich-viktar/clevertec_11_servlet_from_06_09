package by.clevertec.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    @UtilityClass
    public class Attributes {
        public static final String PHONE_PATTERN = "^\\+375(17|25|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";

    }

    @UtilityClass
    public class Paths {
        public static final String TEMPLATE_PDF = "app/src/main/resources/Clevertec_Template.pdf";
        public static final String OUTPUT_PATH = "app/src/main/resources/";
    }
}
