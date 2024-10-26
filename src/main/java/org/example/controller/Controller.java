package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.util.BoxPdfUtils;
import org.example.util.FileUtils;
import org.example.util.ItextPdfUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@Slf4j
@RestController
@RequestMapping("/api")
public class Controller {

    @PostMapping("/itext/{text}")
    public ResponseEntity<ByteArrayOutputStream> itext(@PathVariable String text) {
        log.debug("Received a request for greeting with name: {}", text);

        ByteArrayOutputStream pdfStream;
        String outputPath = "./itext_test.pdf";

        if (FileUtils.doesPdfExist(outputPath)) {
            FileUtils.deletePdf(outputPath);
            log.debug("PDF '{}' file deleted", outputPath);
        }
        pdfStream = ItextPdfUtils.createNewPdfFile(outputPath, text);
        log.debug("PDF '{}' file created: '{}'", outputPath, text);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=itext_test.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream);
    }

    @PostMapping("/pdfbox/{text}")
    public ResponseEntity<ByteArrayOutputStream> pdfbox(@PathVariable String text) {
        log.debug("Received a request for greeting with name: {}", text);

        ByteArrayOutputStream pdfStream;
        String outputPath = "./pdfbox_test.pdf";

        if (FileUtils.doesPdfExist(outputPath)) {
            FileUtils.deletePdf(outputPath);
            log.debug("PDF '{}' file deleted", outputPath);
        }
        pdfStream = BoxPdfUtils.createNewPdfFile(outputPath, text);
        log.debug("PDF '{}' file created: '{}'", outputPath, text);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pdfbox_test.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream);
    }
}