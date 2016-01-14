package pl.edu.agh.ki.to2.parser.parsers;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by lis on 08.01.16.
 * @author lis
 */

public class PDFParser implements IFileParser{

    public PDFParser() {}

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {
        Set<URL> urls = new HashSet<>();
        try {
            // Going through pages
            PdfReader reader = new PdfReader(parserFile.getUrl().openStream());
            int n = reader.getNumberOfPages();
            for(int i=1;i<=n;i++) {
                // Getting all nonempty annotations for page
                PdfArray annots = reader.getPageN(i).getAsArray(PdfName.ANNOTS);
                //Loop through each annotation
                if (annots != null) {
                    ListIterator<PdfObject> annotIterator = annots.listIterator();
                    while( annotIterator.hasNext() ) {
                        // Checking IF external link with action
                        PdfObject annot = annotIterator.next();
                        PdfDictionary annotationDictionary = (PdfDictionary) PdfReader.getPdfObject(annot);
                        if (!annotationDictionary.get(PdfName.SUBTYPE).equals(PdfName.LINK))
                            continue;
                        if (annotationDictionary.get(PdfName.A) == null)
                            continue;
                        // Extracting links
                        PdfDictionary AnnotationAction = annotationDictionary.getAsDict(PdfName.A);
                        if (AnnotationAction.get(PdfName.S).equals(PdfName.URI)) {
                            PdfString Destination = AnnotationAction.getAsString(PdfName.URI);
                            String url = Destination.toString();
                            try {
                                urls.add(new URL(url));
                                // System.out.println("FOUND URL: " + url);
                            } catch (MalformedURLException e) {
                                // e.printStackTrace();
                            }
                        }
                    }
                }
            }
            reader.close();
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        return urls;
    }

    @Override
    public List<String> getSentences(ParserFile parserFile) {
        List<String> sentences = new ArrayList<>();
        try {
            // Get through pages
            PdfReader reader = new PdfReader(parserFile.getUrl().openStream());
            int n = reader.getNumberOfPages();
            for(int i=1;i<=n;i++) {
                // Extract sentences from page
                sentences.addAll(Extractor.extractSentences(PdfTextExtractor.getTextFromPage(reader,i)));
            }
            reader.close();
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        return sentences;
    }
}