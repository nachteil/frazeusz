package pl.edu.agh.ki.to2.parser.parsingControl;

import pl.edu.agh.ki.to2.crawler.IPutter;
import pl.edu.agh.ki.to2.patternmatcher.IPatternMatcher;
import pl.edu.agh.ki.to2.parser.parsers.FileParserFactory;

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
    private IPatternMatcher iPatternMatcher;
    private FileParserFactory factory;

    public ParserThread(BlockingQueue<ParserFile> fileQueue, IPutter iPutter, IPatternMatcher iPatternMatcher, FileParserFactory factory){
        this.isWorking = true;
        this.fileQueue = fileQueue;
        this.iPutter = iPutter;
        this.iPatternMatcher = iPatternMatcher;
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
                iPatternMatcher.match(sentences, file.getUrl().getPath());
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

    public void stop(){
        this.isWorking = false;
    }

    public boolean inProgress(){
        /* TODO - when thread stops?*/
        return isWorking;
    }
}
