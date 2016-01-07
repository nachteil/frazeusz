package pl.edu.agh.ki.to2.parser.parsingControl;

import pl.edu.agh.ki.to2.parser.exceptions.UnsupportedFileException;

import java.net.URL;
import java.util.ArrayList;

public class ParserFile{

    private String content;
    private URL url;
    private int depth;
    private String fileExtension;
    
    /*list of all supported files*/
	private static final ArrayList<String> supportedFiles = new ArrayList<String>() {{
        add("text/html");
        add("application/pdf");
    }};

    public ParserFile(String content, String fileExtention, URL url, int depth)  throws UnsupportedFileException {
        if(isFileExtentionSupported(fileExtention)) {
            this.content = content;
            this.fileExtension = fileExtention;
            this.url = url;
            this.depth = depth;
        }
        else {
            throw new UnsupportedFileException();
        }
    }

    public Boolean isFileExtentionSupported(String fileExtention) {
        for (String ext : supportedFiles) {
            if(fileExtention.contains(ext)){
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
