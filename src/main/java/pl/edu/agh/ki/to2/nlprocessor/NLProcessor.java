package pl.edu.agh.ki.to2.nlprocessor;


import com.nexagis.jawbone.Dictionary;
import com.nexagis.jawbone.*;
import com.nexagis.jawbone.filter.WildcardFilter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class NLProcessor  implements IWordProvider {
    private Dictionary dictionary_instance;
    public Map<String, String[]> map = new HashMap<String, String[]>();
    NLProcessorDataProvider data_instance;

    public NLProcessor()  {
        data_instance = NLProcessorDataProvider.getInstance();
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
        return;
    }
    public Set<String> getVariants(String word) {
        checkMap();
        Set<String> variants =new HashSet<String>();
        String[] result = map.get(word);
        if(result==null)
            return variants;
        for(int i=0; i<result.length-1; i++){
            variants.add(result[i]);
        }
        return variants;
    }

    public Set<String> getDiminutives(String word) {
        WildcardFilter var2 = new WildcardFilter(word, true);
        Iterator var3 = dictionary_instance.getIndexTermIterator( 1, var2);
        Set<String> diminutives =new HashSet<String>();
        while(var3.hasNext()) {
            IndexTerm var4 = (IndexTerm) var3.next();
            Synset[] o = var4.getSynsets();
            Synset d =o[0];
            List<Pointer> a = d.getPointers();
            for (Pointer t : a) {
                String pointer = t.getPointerSymbol();
                if (pointer.equals("nd") || pointer.equals("diminaa") ) {
                    List<WordData> words = t.getSynset().getWord();
                    for (WordData w : words) {
                        String diminutive = w.getWord();
                        diminutives.add(diminutive);
                    }
                }
            }
        }
        return diminutives;
    }

    public Set<String> getSynonyms(String word) {
        WildcardFilter var2 = new WildcardFilter(word, true);
        Iterator var3 = dictionary_instance.getIndexTermIterator( 1, var2);
        Set<String> synonyms =new HashSet<String>();
        while (var3.hasNext()) {
            IndexTerm var4 = (IndexTerm) var3.next();
            Synset[] d = var4.getSynsets();
            for(Synset i : d){
                List<WordData> words = i.getWord();
                for (WordData word_data : words) {
                    String synonym = word_data.getWord();
                    synonyms.add(synonym);
                }

            }
        }
        return synonyms;
    }

    public void checkMap(){
        if(map.isEmpty()){
            map = data_instance.getMap();
        }
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

