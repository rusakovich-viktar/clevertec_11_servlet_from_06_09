package by.clevertec.servlet;

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
        super.init();
        userService = (UserService) config.getServletContext().getAttribute("userService");
        objectMapper = new ObjectMapper();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 0;
            int pageSize = req.getParameter("pageSize") != null ? Integer.parseInt(req.getParameter("pageSize")) : 20;

            List<UserDto> users = userService.getAllUsers(page, pageSize);
            String content = objectMapper.writeValueAsString(users);

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(content);
        } catch (Exception e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("An error occurred while processing your request: " + e.getMessage());
        }
    }


}
