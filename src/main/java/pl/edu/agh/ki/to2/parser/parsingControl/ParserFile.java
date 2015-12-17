package pl.edu.agh.ki.to2.parser.parsingControl;

import pl.edu.agh.ki.to2.parser.exceptions.UnsupportedFileException;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class ParserFile{

    private File file;
    private URL url;
    private int depth;
    private String fileExtension;
    
    /*list of all supported files*/
	private static final ArrayList<String> supportedFiles = new ArrayList<String>() {{
        add("html");
    }};

    public ParserFile(File file, URL url, int depth)  throws UnsupportedFileException {
    	this.fileExtension = getExtension(file.getName());
        if(supportedFiles.contains(this.fileExtension)) {
            this.file = file;
            this.url = url;
            this.depth = depth;
        }
        else {
            throw new UnsupportedFileException();
        }
    }

    //public String getFileExtension() {
    //    String name = this.file.getName();
    //    try {
    //        return name.substring(name.lastIndexOf(".") + 1);
    //    } catch (Exception e) {
    //        return "";
    //    }
    //}
    
    public File getFile() {
        return file;
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

}
