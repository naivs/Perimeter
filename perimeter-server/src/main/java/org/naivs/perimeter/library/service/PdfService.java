package org.naivs.perimeter.library.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class PdfService {

    public BufferedImage getCover(File doc) {
        try {
            //Loading an existing PDF document
            PDDocument document = PDDocument.load(doc);

            //Instantiating the PDFRenderer class
            PDFRenderer renderer = new PDFRenderer(document);

            //Closing the document
            document.close();

            //Rendering an image from the PDF document
            return renderer.renderImage(0);
        } catch (InvalidPasswordException e) {
            System.err.println("PDF Document is password secured. Can't read cover.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Can't read PDF file cover. I/O Error.");
            e.printStackTrace();
        }

        return null;
    }
}
