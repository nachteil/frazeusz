package pl.edu.agh.ki.to2.nlprocessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 2015-12-10.
 */
public class NLPThread extends Thread {

    public Map<String, String[]> map = new HashMap<String, String[]>();

    public void run() {

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\pl\\edu\\agh\\ki\\to2\\nlprocessor\\dictionary\\kurnikOdmiana_CP1250.txt"));
        } catch (FileNotFoundException e) {
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
    }

    public Map<String, String[]> getMap(){
        return map;
    }
}
