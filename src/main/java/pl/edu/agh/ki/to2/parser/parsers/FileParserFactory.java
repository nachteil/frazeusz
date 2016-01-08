package pl.edu.agh.ki.to2.parser.parsers;

import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

/**
 * Created by lis on 08.01.16.
 * @author lis
 */

// TODO test me
// TODO mabye some switch ???

public class FileParserFactory {

    IFileParser htmlParser;
    IFileParser txtParser;
    IFileParser docParser;
    IFileParser docxParser;
    IFileParser pdfParser;
    IFileParser odtParser;

    public FileParserFactory(){}

    public IFileParser getFileParser(ParserFile parserFile){
        if(parserFile.getFileExtension().contains("text/html")){
            if(htmlParser == null)
                htmlParser = new HTMLParser();
            return htmlParser;
        }
        if(parserFile.getFileExtension().contains("text/plain")){
            if(txtParser == null)
                txtParser = new TXTParser();
            return txtParser;
        }
        else if(parserFile.getFileExtension().contains("application/msword")){
            if(docParser == null)
                docParser = new DOCParser();
            return docParser;
        }
        else if(parserFile.getFileExtension().contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
            if(docxParser == null)
                docxParser = new DOCXParser();
            return docxParser;
        }
        else if(parserFile.getFileExtension().contains("application/pdf")){
            if(pdfParser == null)
                pdfParser = new PDFParser();
            return pdfParser;
        }
        if(parserFile.getFileExtension().contains("application/vnd.oasis.opendocument.text")){
            if(odtParser == null)
                odtParser = new ODTParser();
            return odtParser;
        }
        return null;
    }
}