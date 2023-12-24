package by.clevertec.servlet;

import by.clevertec.dto.UserDto;
import by.clevertec.pdf.PdfCanvas;
import by.clevertec.pdf.impl.PdfCanvasImpl;
import by.clevertec.service.UserService;
import by.clevertec.service.impl.UserDocumentService;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Сервлет для генерации PDF с информацией о пользователях.
 */
@WebServlet(value = "/pdf")
public class UserPdfServlet extends HttpServlet {
    private UserService userService;

    /**
     * Инициализирует сервлет и получает ссылку на UserService из ServletContext.
     *
     * @param config объект ServletConfig, который содержит информацию о конфигурации сервлета
     * @throws ServletException если происходит ошибка при инициализации сервлета
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        userService = (UserService) config.getServletContext().getAttribute("userService");

    }

    /**
     * Обрабатывает GET-запросы для генерации PDF.
     * Получает список пользователей с помощью UserService и генерирует PDF с информацией о пользователях.
     * Если пользователей нет, отправляет ошибку HTTP 400.
     *
     * @param req  объект HttpServletRequest, который содержит запрос клиента
     * @param resp объект HttpServletResponse, который содержит ответ сервера
     * @throws ServletException если происходит ошибка при обработке GET-запроса
     * @throws IOException      если происходит ошибка ввода-вывода
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page;
        int pageSize;
        try {
            page = Integer.parseInt(req.getParameter("page"));
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат страницы или размера страницы, необходимо задать оба параметра page и pageSize ");
            return;
        }

        List<UserDto> users = userService.getAllUsers(page, pageSize);
        if (users.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Товар не существует");
            return;
        }

        UserDocumentService documentService = new UserDocumentService(users);

        try (InputStream templateStream = req.getServletContext().getResourceAsStream("/WEB-INF/classes/Clevertec_Template.pdf");
             ServletOutputStream outputStream = resp.getOutputStream()) {

            PdfReader reader = new PdfReader(templateStream);
            PdfStamper stamper = new PdfStamper(reader, outputStream);
            PdfCanvas canvas = new PdfCanvasImpl(stamper.getOverContent(1));

            documentService.processDocument(canvas);

            stamper.close();
            reader.close();

            resp.setContentType("application/pdf");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            throw new ServletException("Ошибка при генерации PDF", e);
        }
    }


}