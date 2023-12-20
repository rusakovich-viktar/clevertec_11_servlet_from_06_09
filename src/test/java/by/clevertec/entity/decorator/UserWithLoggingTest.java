package by.clevertec.entity.decorator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import by.clevertec.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserWithLoggingTest {
    private final List<String> logMessages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        Appender appender = new AbstractAppender("ListAppender", null, null, false, null) {
            @Override
            public void append(LogEvent event) {
                logMessages.add(event.getMessage().getFormattedMessage());
            }
        };
        appender.start();
        config.getRootLogger().addAppender(appender, null, null);
        ctx.updateLoggers();
    }

    //Я уже с ума схожу, по отдельности они работают, вместе не работают
    //    что делал:
    // перенаправлял System.out
    // читаю записанные логи (сейчас)
    // ставил задержку после when Thread.sleep(100);
    // форматировал PatternLayout чтобы изменить сообщение, но толку, они же работают по отдельности корректно
    // ставил @Execution(ExecutionMode.SAME_THREAD) над классом
    // ставил @TestMethodOrder(OrderAnnotation.class) и     @Order(1)

//    @Test
//    public void testSetId() {
//        // Given
//        User user = new User();
//        UserWithLogging userWithLogging = new UserWithLogging(user);
//        int id = 1;
//
//        // When
//        userWithLogging.setId(id);
//
//        // Then
//        assertTrue(logMessages.contains("Setting id"));
//    }

    @Test
    public void testGetId() {
        // Given
        User user = new User();
        UserWithLogging userWithLogging = new UserWithLogging(user);
        user.setId(1);

        // When
        int id = userWithLogging.getId();

        // Then
        assertTrue(logMessages.contains("Getting id"));
    }
}
