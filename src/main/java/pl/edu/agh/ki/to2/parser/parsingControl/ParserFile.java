package pl.edu.agh.ki.to2.parser.parsingControl;

import pl.edu.agh.ki.to2.parser.exceptions.UnsupportedFileException;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class ParserFile{

    private File file;
    private URL url;
    private int depth;
    
    /*list of all supported files*/
	private static final ArrayList<String> supportedFiles = new ArrayList<String>() {{
        add("html");
        add("doc");
        add("pdf");
    }};

    public ParserFile(File file, URL url, int depth)  throws UnsupportedFileException {
        if(supportedFiles.contains(this.getFileExtension())) {
            this.file = file;
            this.url = url;
            this.depth = depth;
        }
        else {
            throw new UnsupportedFileException();
        }
    }

    public String getFileExtension() {
        String name = this.file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
    
    public File getFile() {
        return file;
    }

    public URL getUrl() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

}
