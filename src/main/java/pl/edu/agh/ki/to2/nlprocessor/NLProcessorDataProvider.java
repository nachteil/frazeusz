package pl.edu.agh.ki.to2.nlprocessor;

import java.util.HashMap;
import java.util.Map;

import static org.jgroups.util.Util.sleep;

/**
 * Created by Mefju on 2015-12-16.
 */
public class NLProcessorDataProvider {
    private static NLProcessorDataProvider instance = null;
    public Map<String, String[]> map = new HashMap<String, String[]>();
    NLPThread nlpthread = new NLPThread();

    protected NLProcessorDataProvider(){
        Thread nlpthread2 = new Thread(nlpthread);
        nlpthread2.start();
    }

    public static NLProcessorDataProvider getInstance(){
        if(instance==null){
            instance = new NLProcessorDataProvider();
        }
        return instance;
    }

    public Map<String, String[]> getMap(){
        map = nlpthread.getMap();
        while(map==null){
            map = nlpthread.getMap();
            sleep(1000);
        }
        return map;
    }
}
