package pl.edu.agh.ki.to2.nlprocessor;

import com.nexagis.jawbone.Dictionary;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 2015-12-10.
 */
public class NLPThreadPol extends Thread {

    public Map<String, String[]> map = new HashMap<String, String[]>();
    private Dictionary dictionary_instance;
    boolean finished_map = false;
    boolean finished_dictionary = false;
    String file_sep = File.separator;
    String dict_path =System.getProperty("user.dir") + file_sep+"src"+file_sep+ "main"+file_sep+"java"+ file_sep+"pl"+file_sep+"edu"+file_sep+"agh"+file_sep+"ki"+file_sep+"to2"+file_sep+"nlprocessor"+file_sep+"dictionaryPol";
    Path dictionary_path = Paths.get( dict_path );

    public void run() {
        Dictionary.initialize(String.valueOf(dictionary_path));
        dictionary_instance = Dictionary.getInstance();
        finished_dictionary = true;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(dict_path+ file_sep + "kurnikOdmiana_CP1250.txt"), "CP1250"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(", ");
                map.put(parts[0], parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finished_map = true;
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

    public Map<String, String[]> getMap(){
        if(finished_map)
            return map;
        else
            return null;
    }


}
