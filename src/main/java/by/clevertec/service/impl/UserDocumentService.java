package by.clevertec.service.impl;

import by.clevertec.dto.UserDto;
import by.clevertec.pdf.PdfCanvas;
import by.clevertec.service.DocumentService;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import java.util.List;

/**
 * Сервис для обработки документов с информацией о пользователях.
 */
public class UserDocumentService implements DocumentService {
    private final List<UserDto> users;

    /**
     * Конструктор класса UserDocumentService.
     *
     * @param users Список пользователей для обработки.
     */
    public UserDocumentService(List<UserDto> users) {
        this.users = users;
    }

    /**
     * Обрабатывает документ, добавляя информацию о пользователях на PDF холст.
     *
     * @param canvas Холст для отображения информации о пользователях.
     */
    @Override
    public void processDocument(PdfCanvas canvas) {
        final int[] yPosition = {560};
        users.forEach(user -> {
            String text = "ID: " + user.getId() + ", Имя: " + user.getName() + ", Email: " + user.getEmail() + ", Телефон: " + user.getPhoneNumber();
            canvas.showTextAligned(Element.ALIGN_LEFT, new Phrase(text), 50, yPosition[0], 0);
            yPosition[0] -= 20;
        });
    }
}
