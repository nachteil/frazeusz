package pl.edu.agh.ki.to2.nlprocessor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 2015-12-10.
 */
public class NLPThread extends Thread {

    public Map<String, String[]> map = new HashMap<String, String[]>();
    boolean finished = false;

    public void run() {

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\pl\\edu\\agh\\ki\\to2\\nlprocessor\\dictionary\\kurnikOdmiana_CP1250.txt"), "CP1250"));
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
        finished = true;
    }

    public Map<String, String[]> getMap(){
        if(finished)
            return map;
        else
            return null;
    }
}
