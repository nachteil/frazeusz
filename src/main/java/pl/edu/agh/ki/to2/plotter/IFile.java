package pl.edu.agh.ki.to2.plotter;

import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import java.util.Map;

/**
 * Created by Nina on 2016-01-09.
 */
public interface IFile {
    void save(Map<SearchPattern, Occurrences> data, String path);
}