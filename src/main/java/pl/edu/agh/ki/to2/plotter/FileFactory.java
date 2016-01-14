package pl.edu.agh.ki.to2.plotter;

/**
 * Created by Nina on 2016-01-09.
 */
public class FileFactory {
    public FileFactory() {
    }

    public IFile getFile(String fileType) {
        if (fileType.equals(".txt files")) {
            return new Txt();
        } else if (fileType.equals(".xml files")) {
            return new Xml();
        }
        return null;
    }
}