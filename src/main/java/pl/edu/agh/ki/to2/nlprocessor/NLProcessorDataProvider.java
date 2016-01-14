package pl.edu.agh.ki.to2.nlprocessor;

import com.nexagis.jawbone.Dictionary;

import java.util.HashMap;
import java.util.Map;

import static org.jgroups.util.Util.sleep;

/**
 * Created by Mefju on 2015-12-16.
 */
public class NLProcessorDataProvider {
    private static NLProcessorDataProvider instance = null;
    public Map<String, String[]> map = new HashMap<String, String[]>();
    private Dictionary dictionary_instance = null;
    NLPThreadPol nlpThreadPol = new NLPThreadPol();
    NLPThreadEng nlpThreadEng = new NLPThreadEng();

    protected NLProcessorDataProvider(){
        Thread nlpPol = new Thread(nlpThreadPol);
        nlpPol.start();
        Thread nlpEng = new Thread(nlpThreadEng);
        nlpEng.start();
    }

    public static NLProcessorDataProvider getInstance(){
        if(instance==null){
            instance = new NLProcessorDataProvider();
        }
        return instance;
    }

    public Map<String, String[]> getMap(){
        map = nlpThreadPol.getMap();
        while(map==null){
            map = nlpThreadPol.getMap();
            sleep(100);
        }
        return map;
    }

    public Dictionary getDictionary(Language l){
        do {
            switch (l) {
                case POL:
                    dictionary_instance = nlpThreadPol.getDictionary();
                    break;
                case ENG:
                    dictionary_instance = nlpThreadEng.getDictionary();
                    break;
            }
            sleep(100);
        }while(dictionary_instance==null);
        return dictionary_instance;
    }
}
