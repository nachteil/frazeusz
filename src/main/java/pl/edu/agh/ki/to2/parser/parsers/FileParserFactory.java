package pl.edu.agh.ki.to2.parser.parsers;

import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;
/**
 * Created by Adam on 29.11.2015.
 */
public class FileParserFactory {

    IFileParser htmlParser;
    IFileParser pdfParser;
    IFileParser docParser;

    public FileParserFactory(){}

    public IFileParser getFileParser(ParserFile parserFile){
        if(parserFile.getFileExtension().contains("text/html")){
            if(htmlParser == null)
                htmlParser = new HTMLParser();
            return htmlParser;
        }
        else if(parserFile.getFileExtension().contains("application/msword")){
            if(docParser == null)
                docParser = new DOCParser();
            return docParser;
        }
        else if(parserFile.getFileExtension().contains("application/pdf")){
            if(pdfParser == null)
                pdfParser = new PDFParser();
            return pdfParser;
        }
        return null;
    }
}
