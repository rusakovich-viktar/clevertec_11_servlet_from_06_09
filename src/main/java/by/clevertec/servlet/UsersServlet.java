package by.clevertec.servlet;

import static by.clevertec.util.Constants.Attributes.APPLICATION_JSON;
import static by.clevertec.util.Constants.Attributes.APPLICATION_PDF;
import static by.clevertec.util.Constants.Attributes.USER_SERVICE;
import static by.clevertec.util.Constants.Messages.AN_ERROR_OCCURRED_WHILE_CREATING_THE_USER;
import static by.clevertec.util.Constants.Messages.INVALID_USER_ID;
import static by.clevertec.util.Constants.Messages.MISSING_USER_ID;
import static by.clevertec.util.Constants.Messages.USER_NOT_FOUND;

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

/**
 * Сервлет для обработки запросов, связанных с пользователями.
 * Привязан к "/users/*".
 */
@WebServlet(value = "/users/*")
public class UsersServlet extends HttpServlet {

    private UserService userService;
    private ObjectMapper objectMapper;

    /**
     * Инициализация сервлета.
     * Получает ссылку на UserService из контекста сервлета и инициализирует ObjectMapper.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        userService = (UserService) config.getServletContext().getAttribute(USER_SERVICE);
        objectMapper = new ObjectMapper();

    }

    /**
     * Обрабатывает HTTP GET запросы.
     * Возвращает информацию о пользователе с указанным ID.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, MISSING_USER_ID);
            return;
        }
        String userId = pathInfo.substring(1);

        try {
            UserDto user = userService.getUser(Integer.parseInt(userId));
            if (user == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, USER_NOT_FOUND);
                return;
            }
            String s = objectMapper.writeValueAsString(user);
            resp.setContentType(APPLICATION_JSON);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(s);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALID_USER_ID);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while getting the user: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает HTTP POST запросы.
     * Обновляет пользователя на основе данных, полученных в теле запроса.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            UserDto newUser = objectMapper.readValue(req.getReader(), UserDto.class);
            userService.updateUser(newUser);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, AN_ERROR_OCCURRED_WHILE_CREATING_THE_USER + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, MISSING_USER_ID);
            return;
        }
        String userId = pathInfo.substring(1);

        try {
            userService.deleteUser(Integer.parseInt(userId));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALID_USER_ID);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the user: " + e.getMessage());
        }
    }
}
