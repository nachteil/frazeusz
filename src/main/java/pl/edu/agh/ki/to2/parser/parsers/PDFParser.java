package pl.edu.agh.ki.to2.parser.parsers;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by Adam on 29.11.2015.
 */
public class PDFParser implements IFileParser{

    public PDFParser() {
    }

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {

        Set<URL> urls = new HashSet<>();

        try {
            // ByteContent wont work :/ dk why
            PdfReader reader = new PdfReader(parserFile.getUrl().openStream());
            //PdfReader reader = new PdfReader(byteContent);
            int n = reader.getNumberOfPages();

            for(int i=1;i<=n;i++) {

                //Get the current page
                PdfDictionary pageDictionary = reader.getPageN(i);
                //Get all of the annotations for the current page
                PdfArray annots = pageDictionary.getAsArray(PdfName.ANNOTS);
                //Make sure we have something
                if ((annots == null) || (annots.size() == 0)) {
                    //System.out.println("ANNOTS EMPTY");
                }

                //Loop through each annotation
                if (annots != null) {
                    for (PdfObject annot : annots.getArrayList()) {
                        //Convert the itext-specific object as a generic PDF object
                        PdfDictionary annotationDictionary =
                                (PdfDictionary) PdfReader.getPdfObject(annot);
                        //Make sure this annotation has a link
                        if (!annotationDictionary.get(PdfName.SUBTYPE).equals(PdfName.LINK))
                            continue;
                        //Make sure this annotation has an ACTION
                        if (annotationDictionary.get(PdfName.A) == null)
                            continue;
                        //Get the ACTION for the current annotation
                        PdfDictionary AnnotationAction =
                                annotationDictionary.getAsDict(PdfName.A);
                        // Test if it is a URI action (There are tons of other types of actions,
                        // some of which might mimic URI, such as JavaScript,
                        // but those need to be handled seperately)
                        if (AnnotationAction.get(PdfName.S).equals(PdfName.URI)) {
                            PdfString Destination = AnnotationAction.getAsString(PdfName.URI);
                            String url1 = Destination.toString();
                            //System.out.println("FOUND URL: " + url1);
                            try {
                                urls.add(new URL(url1));
                            } catch (MalformedURLException e) {
                                // TODO Auto-generated catch block
                                System.out.println("Caught malformed url!");
                            }
                        }
                    }
                }
            }

        reader.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return urls;
    }

    @Override
    public List<String> getSentences(ParserFile parserFile) {
    	//byte[] byteContent = parserFile.getContent().getBytes();
    	List<String> sentences = new ArrayList<String>();
    	try {
            // ByteContent wont work :/ dk why
            PdfReader reader = new PdfReader(parserFile.getUrl().openStream());
            //PdfReader reader = new PdfReader(byteContent);
            int n = reader.getNumberOfPages();

            for(int i=1;i<=n;i++) {

                String str = PdfTextExtractor.getTextFromPage(reader, i);
                //System.out.println(str);

                for (String sentence : str.split("\\.")) {
                    if(!sentence.trim().isEmpty()) {
                        sentences.add(sentence);
                        //System.out.println(sentence);
                    }
                }
            }
            
            reader.close();
            }
            catch (Exception e) {
                System.out.println(e);
        }
    	
        return sentences;
    }
}
