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
    	byte[] byteContent = parserFile.getContent().getBytes();
    	List<String> sentences = new ArrayList<String>();
    	try {     
            PdfReader reader = new PdfReader(byteContent);
            int n = reader.getNumberOfPages(); 
            String str=PdfTextExtractor.getTextFromPage(reader, 1); //Extracting the content from first page for now.
            //System.out.println(str);

    		for (String sentence : str.split("\\.")){
    			sentences.add(sentence);
    		}
            
            reader.close();
            }
            catch (Exception e) {
                System.out.println(e);
        }
    	
        return sentences;
    }
}
