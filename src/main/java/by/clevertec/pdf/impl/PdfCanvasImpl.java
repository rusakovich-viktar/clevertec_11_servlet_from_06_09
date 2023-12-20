package by.clevertec.pdf.impl;

import by.clevertec.pdf.PdfCanvas;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;

/**
 * Реализация интерфейса PdfCanvas с использованием библиотеки iText.
 */
public class PdfCanvasImpl implements PdfCanvas {
    private final PdfContentByte canvas;

    /**
     * Конструктор класса PdfCanvasImpl.
     *
     * @param canvas Объект PdfContentByte, представляющий PDF холст.
     */
    public PdfCanvasImpl(PdfContentByte canvas) {
        this.canvas = canvas;
    }

    /**
     * Отображает текст, выровненный по заданным параметрам, на PDF холсте.
     *
     * @param alignment Выравнивание текста.
     * @param phrase    Фраза для отображения.
     * @param x         Координата x на холсте.
     * @param y         Координата y на холсте.
     * @param rotation  Угол поворота текста.
     */
    @Override
    public void showTextAligned(int alignment, Phrase phrase, float x, float y, float rotation) {
        ColumnText.showTextAligned(canvas, alignment, phrase, x, y, rotation);
    }
}
