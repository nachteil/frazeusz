package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.plotter.IFile;

import java.util.Map;

/**
 * Created by Nina on 2016-01-09.
 */
public class Xml implements IFile {

    @Override
    public void save(Map data, String fileName) {
        String name = fileName + ".xml";
    }
}
