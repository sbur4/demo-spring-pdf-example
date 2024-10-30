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

    //    @PostMapping(path = "/itext/{text}", produces = "application/pdf")
    @PostMapping(path = "/itext/{text}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> itext(@PathVariable String text) {
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
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_ENCODING, "Binary")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"itext_test.pdf\"")
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"itext_test.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.toByteArray());
    }

    @PostMapping("/pdfbox/{text}")
    public ResponseEntity<byte[]> pdfbox(@PathVariable String text) {
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
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_ENCODING, "Binary")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"pdfbox_test.pdf\"")
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"pdfbox_test.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.toByteArray());
    }
}