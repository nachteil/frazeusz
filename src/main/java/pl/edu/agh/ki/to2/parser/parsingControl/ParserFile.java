package pl.edu.agh.ki.to2.parser.parsingControl;

import pl.edu.agh.ki.to2.parser.exceptions.UnsupportedFileException;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Adam on 29.11.2015.
 * @author Adam
 */

public class ParserFile{

    private String content;
    private URL url;
    private int depth;
    private String fileExtension;

    /*list of all supported files*/
    private static final ArrayList<String> supportedFiles = new ArrayList<String>() {{
        add("text/html");
        add("application/pdf");
        add("application/msword");
        add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        add("text/plain");
        add("application/vnd.oasis.opendocument.text");
    }};

    public ParserFile(String content, String fileExtension, URL url, int depth)  throws UnsupportedFileException {
        if(isFileExtensionSupported(fileExtension)) {
            this.content = content;
            this.fileExtension = fileExtension;
            this.url = url;
            this.depth = depth;
        }
        else {
            throw new UnsupportedFileException();
        }
    }

    public static Boolean isFileExtensionSupported(String fileExtension) {
        for (String ext : supportedFiles) {
            if(fileExtension.contains(ext)){
                return true;
            }
        }
        return false;
    }

    public String getFileExtension(){
        return fileExtension;
    }

    public URL getUrl() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

    public String getContent() {
        return content;
    }
}