package pl.edu.agh.ki.to2.parser.parsingControl;

import pl.edu.agh.ki.to2.crawler.IPutter;
import pl.edu.agh.ki.to2.parser.parsers.FileParserFactory;
import pl.edu.agh.ki.to2.parser.parsers.IFileParser;
import pl.edu.agh.ki.to2.patternmatcher.IPatternMatcher;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by Adam on 29.11.2015.
 * @author Adam
 */

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
            try {
                // Getting parserFile from fileQueue
                file = fileQueue.poll(500, TimeUnit.MILLISECONDS);
//                if(file!=null) {
//                    System.out.println("POPPED FILE: " + file.getUrl().toString());
//                    System.out.println("======================================================");
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(file != null) {
                // Extracting urls and sentences from parserFile
                IFileParser parser = factory.getFileParser(file);
                Set<URL> urls = parser.getUrls(file);
                List<String> sentences = parser.getSentences(file);
                // Sending urls and sentences further
                for (URL url : urls) {
                    iPutter.put(url, file.getDepth() + 1);
                }
                iPatternMatcher.match(sentences, file.getUrl().toString());
                System.out.println(sentences);
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