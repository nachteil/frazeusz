package pl.edu.agh.ki.to2.nlprocessor;

import com.nexagis.jawbone.Dictionary;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Matt on 2015-12-10.
 */
public class NLPThread extends Thread {

    public Map<String, String[]> map = new HashMap<String, String[]>();
    private Dictionary dictionary_instance;
    boolean finished_map = false;
    boolean finished_dictionary = false;
    public void run() {
        String dict_folder = System.getProperty("user.dir") + "\\src\\main\\java\\pl\\edu\\agh\\ki\\to2\\nlprocessor\\dictionary";
        String zipped = System.getProperty("user.dir") + "\\src\\main\\java\\pl\\edu\\agh\\ki\\to2\\nlprocessor\\dictionary.zip";
        try {
            unzip(zipped, dict_folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path dictionary_path = Paths.get(System.getProperty("user.dir") + "\\src\\main\\java\\pl\\edu\\agh\\ki\\to2\\nlprocessor\\dictionary");
        Dictionary.initialize(String.valueOf(dictionary_path));
        dictionary_instance = Dictionary.getInstance();
        finished_dictionary = true;
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
        finished_map = true;
    }

    public Dictionary getDictionary(){
        if(finished_dictionary)
            return dictionary_instance;
        else
            return null;
    }

    public Map<String, String[]> getMap(){
        if(finished_map)
            return map;
        else
            return null;
    }

    private static final int BUFFER_SIZE = 4096;
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
