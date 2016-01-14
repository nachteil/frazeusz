package pl.edu.agh.ki.to2.nlprocessor;


import com.nexagis.jawbone.Dictionary;
import com.nexagis.jawbone.*;
import com.nexagis.jawbone.filter.WildcardFilter;

import java.util.*;

import static java.lang.Thread.sleep;

public class NLProcessor  implements IWordProvider {
    NLProcessorDataProvider data_instance;

    public NLProcessor()  {
        data_instance = NLProcessorDataProvider.getInstance();
        return;
    }
    public Set<String> getVariants(String word) {
        Map<String, String[]> map = data_instance.getMap();
        Set<String> variants = new HashSet<String>();
        String[] result = map.get(word);
        if(result==null)
            return variants;
        for(int i=0; i<result.length-1; i++){
            variants.add(result[i]);
        }
        return variants;
    }

    public Set<String> getDiminutives(String word) {
        Set<String> diminutives =new HashSet<String>();
        for(Language language : Language.values()) {
            Dictionary dictionary_instance = data_instance.getDictionary(language);
            WildcardFilter wording = new WildcardFilter(word, true);
            Iterator terms = dictionary_instance.getIndexTermIterator(1, wording);
            while (terms.hasNext()) {
                IndexTerm term = (IndexTerm) terms.next();
                Synset[] synsets = term.getSynsets();
                Synset key_synset = synsets[0];
                List<Pointer> pointers = key_synset.getPointers();
                for (Pointer pointer : pointers) {
                    String pointer_symbol = pointer.getPointerSymbol();
                    if (pointer_symbol.equals("nd") || pointer_symbol.equals("diminaa")) {
                        List<WordData> words = pointer.getSynset().getWord();
                        for (WordData w : words) {
                            String diminutive = w.getWord();
                            diminutives.add(diminutive);
                        }
                    }
                }
            }
        }
        return diminutives;
    }

    public Set<String> getSynonyms(String word) {
        Set<String> synonyms = new HashSet<String>();
        for(Language language : Language.values()) {
            Dictionary dictionary_instance = data_instance.getDictionary(language);
            WildcardFilter wording = new WildcardFilter(word, true);
            Iterator terms = dictionary_instance.getIndexTermIterator(1, wording);
            while (terms.hasNext()) {
                IndexTerm term = (IndexTerm) terms.next();
                Synset[] synsets = term.getSynsets();
                for (Synset synset : synsets) {
                    List<WordData> words = synset.getWord();
                    for (WordData word_data : words) {
                        String synonym = word_data.getWord();
                        synonyms.add(synonym);
                    }

                }
            }
        }
        return synonyms;
    }

}

