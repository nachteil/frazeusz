package pl.edu.agh.ki.to2.nlprocessor;

import com.nexagis.jawbone.Dictionary;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Mefju on 2016-01-10.
 */
public class NLPThreadAng extends Thread {
    private Dictionary dictionary_instance;
    boolean finished_dictionary = false;
    String file_sep = File.separator;
    String dict_path =System.getProperty("user.dir") + file_sep+"src"+file_sep+ "main"+file_sep+"java"+ file_sep+"pl"+file_sep+"edu"+file_sep+"agh"+file_sep+"ki"+file_sep+"to2"+file_sep+"nlprocessor"+file_sep+"dictionaryAng";
    Path dictionary_path = Paths.get(dict_path);

    public void run(){

        Dictionary.initialize(String.valueOf(dictionary_path));
        dictionary_instance = Dictionary.getInstance();
        finished_dictionary = true;
    }

    public Dictionary getDictionary(){
        if(finished_dictionary) {
            Dictionary.initialize(String.valueOf(dictionary_path));
            dictionary_instance = Dictionary.getInstance();
            return dictionary_instance;
        }
        else
            return null;
    }

}
