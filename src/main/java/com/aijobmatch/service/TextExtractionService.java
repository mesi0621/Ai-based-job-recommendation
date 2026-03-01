package com.aijobmatch.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TextExtractionService {
    
    public String extractText(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        
        if ("application/pdf".equals(contentType)) {
            return extractTextFromPdf(file);
        } else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
            return extractTextFromDocx(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type for text extraction");
        }
    }
    
    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = org.apache.pdfbox.Loader.loadPDF(inputStream.readAllBytes())) {
            
            if (document.isEncrypted()) {
                throw new IllegalArgumentException("Encrypted PDF files are not supported");
            }
            
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            
            if (text == null || text.trim().isEmpty()) {
                throw new IllegalArgumentException("No text content found in PDF");
            }
            
            return text.trim();
        } catch (IOException e) {
            throw new IOException("Failed to extract text from PDF: " + e.getMessage(), e);
        }
    }
    
    private String extractTextFromDocx(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             XWPFDocument document = new XWPFDocument(inputStream)) {
            
            StringBuilder text = new StringBuilder();
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            
            for (XWPFParagraph paragraph : paragraphs) {
                String paragraphText = paragraph.getText();
                if (paragraphText != null && !paragraphText.trim().isEmpty()) {
                    text.append(paragraphText).append("\n");
                }
            }
            
            String extractedText = text.toString().trim();
            
            if (extractedText.isEmpty()) {
                throw new IllegalArgumentException("No text content found in DOCX");
            }
            
            return extractedText;
        } catch (IOException e) {
            throw new IOException("Failed to extract text from DOCX: " + e.getMessage(), e);
        }
    }
}
