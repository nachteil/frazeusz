package pl.edu.agh.ki.to2.parser;

import pl.edu.agh.ki.to2.crawler.IPutter;
import pl.edu.agh.ki.to2.patternmatcher.IPatternMatcher;
import pl.edu.agh.ki.to2.parser.parsers.FileParserFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Parser {

    private List<ParserThread> threadsList;

    public Parser(BlockingQueue<ParserFile> fileQueue, IPutter iPutter, IPatternMatcher iPatternMatcher, int threads){
        init(fileQueue, iPutter, iPatternMatcher, threads);
    }

    private void init(BlockingQueue<ParserFile> fileQueue, IPutter iPutter, IPatternMatcher iPatternMatcher, int threads) {
        FileParserFactory factory = new FileParserFactory();
        for (int i = 0; i < threads; i++) {
            ParserThread thread = new ParserThread(fileQueue, iPutter, iPatternMatcher, factory);
            thread.run();
            threadsList.add(thread);
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
