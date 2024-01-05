package by.clevertec.servlet;

import static by.clevertec.util.Constants.Attributes.APPLICATION_JSON;
import static by.clevertec.util.Constants.Attributes.PAGE;
import static by.clevertec.util.Constants.Attributes.PAGE_SIZE;

import by.clevertec.dto.UserDto;
import by.clevertec.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Сервлет для обработки запросов, связанных со всеми пользователями.
 * Привязан к "/users".
 */
@WebServlet(value = "/users")
public class UsersGetAllServlet extends HttpServlet {

    private UserService userService;
    private ObjectMapper objectMapper;

    /**
     * Инициализация сервлета.
     * Получает ссылку на UserService из контекста сервлета и инициализирует ObjectMapper.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        userService = springContext.getBean(UserService.class);
        objectMapper = springContext.getBean(ObjectMapper.class);
    }

    /**
     * Обрабатывает HTTP GET запросы.
     * Возвращает информацию обо всех пользователях в формате JSON.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int page = req.getParameter(PAGE) != null ? Integer.parseInt(req.getParameter(PAGE)) : 0;
            int pageSize = req.getParameter(PAGE_SIZE) != null ? Integer.parseInt(req.getParameter(PAGE_SIZE)) : 20;

            List<UserDto> users = userService.getAllUsers(page, pageSize);
            String content = objectMapper.writeValueAsString(users);

            resp.setContentType(APPLICATION_JSON);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(content);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("An error occurred while processing your request: " + e.getMessage());
        }
    }

}
