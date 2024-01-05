package by.clevertec.config;

import by.clevertec.dao.UserDao;
import by.clevertec.dao.impl.UserDaoImpl;
import by.clevertec.mapper.UserMapper;
import by.clevertec.service.DatabaseMigrationService;
import by.clevertec.service.UserService;
import by.clevertec.service.impl.DatabaseMigrationServiceImpl;
import by.clevertec.service.impl.UserServiceImpl;
import by.clevertec.validation.UserDtoValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SpringConfig {

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public UserDtoValidator validator() {
        return new UserDtoValidator();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userMapper(), validator(), userDao());
    }

    @Bean
    public DatabaseMigrationService migrationService() {
        return new DatabaseMigrationServiceImpl();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
