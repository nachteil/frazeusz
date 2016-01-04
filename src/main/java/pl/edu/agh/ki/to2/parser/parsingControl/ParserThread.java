package pl.edu.agh.ki.to2.parser.parsingControl;

import pl.edu.agh.ki.to2.crawler.IPutter;
import pl.edu.agh.ki.to2.parser.parsers.FileParserFactory;
import pl.edu.agh.ki.to2.patternmatcher.IPatternMatcher;

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
        System.out.println("run1");
        ParserFile file = null;
        System.out.println("run2");
        while(isWorking){
            System.out.println("run3");
            Set<URL> urls; // no need to initialize this here
            List<String> sentences;
            System.out.println("run5");
            try {
                file = fileQueue.poll(500, TimeUnit.MILLISECONDS);
                System.out.println("leo");
                if(file!=null)
                    System.out.println(file.getUrl().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run6");
            if(file != null) {
                System.out.println("run7");
                urls = factory.getFileParser(file).getUrls(file);
                sentences = factory.getFileParser(file).getSentences(file);
                System.out.println("run111");
                //last steps:
                for (URL url : urls) {
                    System.out.println("run8");
                    iPutter.put(url, file.getDepth() + 1);
                }
                iPatternMatcher.match(sentences, file.getUrl().toString()); //url or string in IPatternMatcher?????
            }
            else{
                try {
                    System.out.println("run99");
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("run12");
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
