package pl.edu.agh.ki.to2.nlprocessor;


import com.nexagis.jawbone.Dictionary;
import com.nexagis.jawbone.*;
import com.nexagis.jawbone.filter.WildcardFilter;

import java.util.*;

public class NLProcessor  implements IWordProvider {
    private Dictionary dictionary_instance;
    public Map<String, String[]> map = new HashMap<String, String[]>();
    NLProcessorDataProvider data_instance;

    public NLProcessor()  {
        data_instance = NLProcessorDataProvider.getInstance();

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
        getDictionary(Language.POL);
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
        Set<String> synonyms = new HashSet<String>();
        for(Language l : Language.values()) {
            getDictionary(l);
            WildcardFilter var2 = new WildcardFilter(word, true);
            Iterator var3 = dictionary_instance.getIndexTermIterator(1, var2);
            while (var3.hasNext()) {
                IndexTerm var4 = (IndexTerm) var3.next();
                Synset[] d = var4.getSynsets();
                for (Synset i : d) {
                    List<WordData> words = i.getWord();
                    for (WordData word_data : words) {
                        String synonym = word_data.getWord();
                        synonyms.add(synonym);
                    }

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

    public void getDictionary(Language l){
            dictionary_instance = data_instance.getDictionary(l);
    }
}

