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
    NLPThreadPol nlpthreadPol = new NLPThreadPol();
    NLPThreadAng nlpthreadAng = new NLPThreadAng();

    protected NLProcessorDataProvider(){
        Thread nlpthreadPol2 = new Thread(nlpthreadPol);
        nlpthreadPol2.start();
        Thread nlpthreadAng2 = new Thread(nlpthreadAng);
        nlpthreadAng2.start();
    }

    public static NLProcessorDataProvider getInstance(){
        if(instance==null){
            instance = new NLProcessorDataProvider();
        }
        return instance;
    }

    public Map<String, String[]> getMap(){
        map = nlpthreadPol.getMap();
        while(map==null){
            map = nlpthreadPol.getMap();
            sleep(1000);
        }
        return map;
    }

    public Dictionary getDictionary(Language l){
        do {
            switch (l) {
                case POL:
                    dictionary_instance = nlpthreadPol.getDictionary();
                    break;
                case ANG:
                    dictionary_instance = nlpthreadAng.getDictionary();
                    break;
            }
        }while(dictionary_instance==null);
        System.out.println(dictionary_instance);
        return dictionary_instance;
    }
}
