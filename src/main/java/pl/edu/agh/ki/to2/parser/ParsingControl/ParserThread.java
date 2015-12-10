package ParsingControl;

import ExteriorInterfaces.IMatcher;
import ExteriorInterfaces.IPutter;
import Parsers.FileParserFactory;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class ParserThread implements Runnable {

    boolean isWorking;
    private BlockingQueue<ParserFile> fileQueue;
    private IPutter iPutter;
    private IMatcher iMatcher;
    private FileParserFactory factory;

    protected ParserThread(BlockingQueue<ParserFile> fileQueue, IPutter iPutter, IMatcher iMatcher, FileParserFactory factory){
        this.isWorking = true;
        this.fileQueue = fileQueue;
        this.iPutter = iPutter;
        this.iMatcher = iMatcher;
        this.factory = factory;
    }

    public void run(){
        ParserFile file = null;
        while(isWorking){
            Set<URL> urls; // no need to initialize this here
            List<String> sentences;

            try {
                file = fileQueue.poll(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(file != null) {
                urls = factory.getFileParser(file).getUrls(file);
                sentences = factory.getFileParser(file).getSentences(file);
                //last steps:
                for (URL url : urls) {
                    iPutter.put(url, file.getDepth() + 1);
                }
                iMatcher.match(file.getUrl(), sentences);
                iMatcher.matchVer2(sentences); //unintegrated interface?
            }
            else{
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    protected void stop(){
        this.isWorking = false;
    }

    protected boolean inProgress(){
        /* TODO - when thread stops?*/
        return isWorking;
    }
}
