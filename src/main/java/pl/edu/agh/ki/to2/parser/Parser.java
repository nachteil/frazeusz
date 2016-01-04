package pl.edu.agh.ki.to2.parser;

import pl.edu.agh.ki.to2.crawler.IPutter;
import pl.edu.agh.ki.to2.parser.parsers.FileParserFactory;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserThread;
import pl.edu.agh.ki.to2.patternmatcher.IPatternMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Parser {

    private List<ParserThread> threadsList;

    public Parser(BlockingQueue<ParserFile> fileQueue, IPutter iPutter, IPatternMatcher iPatternMatcher, int threads){
        threadsList = new ArrayList<>();
        init(fileQueue, iPutter, iPatternMatcher, threads);
    }

    private void init(BlockingQueue<ParserFile> fileQueue, IPutter iPutter, IPatternMatcher iPatternMatcher, int threads) {
        FileParserFactory factory = new FileParserFactory();
        System.out.println("1188");
//        try {
            System.out.println("118811111");
//            System.out.println(fileQueue.take());
            System.out.println("1188222");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int i = 0; i < threads; i++) {
            System.out.println("11881");
            ParserThread thread = new ParserThread(fileQueue, iPutter, iPatternMatcher, factory);
            System.out.println("11882");
            new Thread(thread).start();
            System.out.println("11883");
            threadsList.add(thread);
            System.out.println("11884");
        }
    }

    public void close(){
        for(ParserThread p : threadsList){
            p.stop();
        }
    }

    public boolean isWorking(){
        for(ParserThread p : threadsList){
            if(p.inProgress() == true){
                return true;
            }
        }
        return false;
    }

}
