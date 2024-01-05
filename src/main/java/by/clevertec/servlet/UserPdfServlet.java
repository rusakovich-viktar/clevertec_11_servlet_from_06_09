package by.clevertec.servlet;

import static by.clevertec.util.Constants.Attributes.APPLICATION_PDF;
import static by.clevertec.util.Constants.Attributes.PAGE;
import static by.clevertec.util.Constants.Attributes.PAGE_SIZE;
import static by.clevertec.util.Constants.Attributes.UTF_8;
import static by.clevertec.util.Constants.Messages.PRODUCT_NOT_EXIST;
import static by.clevertec.util.Constants.Messages.НЕВЕРНЫЙ_ФОРМАТ_СТРАНИЦЫ_ИЛИ_РАЗМЕРА_СТРАНИЦЫ_НЕОБХОДИМО_ЗАДАТЬ_ОБА_ПАРАМЕТРА_PAGE_И_PAGE_SIZE;
import static by.clevertec.util.Constants.Paths.WEB_INF_CLASSES_CLEVERTEC_TEMPLATE_PDF;

import by.clevertec.dto.UserDto;
import by.clevertec.pdf.PdfCanvas;
import by.clevertec.pdf.impl.PdfCanvasImpl;
import by.clevertec.service.UserService;
import by.clevertec.service.impl.UserDocumentService;
import by.clevertec.util.Constants.Messages;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
        super.init(config);
        WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        userService = springContext.getBean(UserService.class);

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
            page = Integer.parseInt(req.getParameter(PAGE));
            pageSize = Integer.parseInt(req.getParameter(PAGE_SIZE));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, НЕВЕРНЫЙ_ФОРМАТ_СТРАНИЦЫ_ИЛИ_РАЗМЕРА_СТРАНИЦЫ_НЕОБХОДИМО_ЗАДАТЬ_ОБА_ПАРАМЕТРА_PAGE_И_PAGE_SIZE);
            return;
        }

        List<UserDto> users = userService.getAllUsers(page, pageSize);
        if (users.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, PRODUCT_NOT_EXIST);
            return;
        }

        UserDocumentService documentService = new UserDocumentService(users);

        try (InputStream templateStream = req.getServletContext().getResourceAsStream(WEB_INF_CLASSES_CLEVERTEC_TEMPLATE_PDF);
             ServletOutputStream outputStream = resp.getOutputStream()) {

            PdfReader reader = new PdfReader(templateStream);
            PdfStamper stamper = new PdfStamper(reader, outputStream);
            PdfCanvas canvas = new PdfCanvasImpl(stamper.getOverContent(1));

            documentService.processDocument(canvas);

            stamper.close();
            reader.close();

            resp.setContentType(APPLICATION_PDF);
            resp.setCharacterEncoding(UTF_8);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            throw new ServletException(Messages.ОШИБКА_ПРИ_ГЕНЕРАЦИИ_PDF, e);
        }
    }
}
