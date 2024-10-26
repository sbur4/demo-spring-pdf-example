package org.example.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;

@Slf4j
@UtilityClass
public class ItextPdfUtils {
    public static ByteArrayOutputStream createNewPdfFile(final String outputPath, @NonNull final String text) {
        Assert.notNull(outputPath, "Output path must not be null");
        Assert.hasText(text, "Text must not be empty");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Paragraph paragraph = new Paragraph("User:" + text, font);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            document.add(paragraph);
            document.add(new Paragraph("\n"));

        } catch (DocumentException ex) {
            throw new RuntimeException(ex);
        } finally {
            document.close();
            FileUtils.savePdf(outputStream, outputPath);
        }

        return outputStream;
    }
}