package pl.edu.agh.ki.to2.parser.parsers;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Adam on 29.11.2015.
 */
public class PDFParser implements IFileParser{

    public PDFParser() {
    }

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {
        return null;
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
