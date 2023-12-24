package by.clevertec.listener;


import by.clevertec.dao.UserDao;
import by.clevertec.dao.impl.UserDaoImpl;
import by.clevertec.mapper.UserMapper;
import by.clevertec.service.UserService;
import by.clevertec.service.impl.UserServiceImpl;
import by.clevertec.validation.UserDtoValidator;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Класс слушателя контекста, инициализирующий зависимости при старте приложения.
 * Реализует интерфейс ServletContextListener.
 */
@WebListener
public class DependencyInitializationContextListener implements ServletContextListener {

    /**
     * Инициализирует зависимости при старте приложения.
     *
     * @param sce Событие контекста сервлета.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDtoValidator validator = new UserDtoValidator();
        UserMapper userMapper = new UserMapper();
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userMapper, validator, userDao);
        sce.getServletContext().setAttribute("userService", userService);
    }
}