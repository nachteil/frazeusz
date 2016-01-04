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
        add("text/html; charset=utf-8");
    }};

    public ParserFile(String content, String fileExtention, URL url, int depth)  throws UnsupportedFileException {
        if(supportedFiles.contains(fileExtention)) {
            this.content = content;
            this.fileExtension = fileExtention;
            this.url = url;
            this.depth = depth;
        }
        else {
            throw new UnsupportedFileException();
        }
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
