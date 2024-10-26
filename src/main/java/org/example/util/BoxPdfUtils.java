package org.example.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@UtilityClass
public class BoxPdfUtils {
    public static ByteArrayOutputStream createNewPdfFile(final String outputPath, @NonNull final String text) {
        Assert.notNull(outputPath, "Output path must not be null");
        Assert.hasText(text, "Text must not be empty");

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("User: " + text);
                contentStream.endText();
            } catch (IOException e) {
                log.error("Error writing to PDF content stream.", e);
                throw new RuntimeException("Failed to write to PDF content stream", e);
            }

            document.save(outputStream);
            document.save(outputPath);

            return outputStream;
        } catch (IOException e) {
            log.error("Error creating or saving PDF", e);
            throw new RuntimeException("Failed to create or save PDF", e);
        }
    }
}