package by.clevertec.service.impl;

import by.clevertec.dto.UserDto;
import by.clevertec.pdf.PdfCanvas;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserDocumentServiceTest {

    @Test
    public void testProcessDocument() {
        // Given
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto(1, "Пользователь 1", "user1@example.com", "+375291234567"));
        users.add(new UserDto(2, "Пользователь 2", "user2@example.com", "+375441234568"));
        users.add(new UserDto(3, "Пользователь 3", "user3@example.com", "+375331234569"));

        UserDocumentService userDocumentService = new UserDocumentService(users);
        PdfCanvas canvas = Mockito.mock(PdfCanvas.class);

        // When
        userDocumentService.processDocument(canvas);

        // Then
        for (UserDto user : users) {
            Mockito.verify(canvas, Mockito.times(users.size())).showTextAligned(Mockito.anyInt(), Mockito.any(), Mockito.anyFloat(), Mockito.anyFloat(), Mockito.anyFloat());
        }
    }

}
