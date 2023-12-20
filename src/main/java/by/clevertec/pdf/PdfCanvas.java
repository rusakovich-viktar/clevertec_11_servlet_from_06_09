package by.clevertec.pdf;

import com.itextpdf.text.Phrase;

public interface PdfCanvas {
    void showTextAligned(int alignment, Phrase phrase, float x, float y, float rotation);
}