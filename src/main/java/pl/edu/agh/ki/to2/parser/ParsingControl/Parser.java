package ParsingControl;

import ExteriorInterfaces.IMatcher;
import ExteriorInterfaces.IPutter;
import Parsers.FileParserFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Parser {

    private List<ParserThread> threadsList;

    public Parser(BlockingQueue<ParserFile> fileQueue, IPutter iPutter, IMatcher iMatcher, int threads){
        init(fileQueue, iPutter, iMatcher, threads);
    }

    private void init(BlockingQueue<ParserFile> fileQueue, IPutter iPutter, IMatcher iMatcher, int threads) {
        FileParserFactory factory = new FileParserFactory();
        for (int i = 0; i < threads; i++) {
            ParserThread thread = new ParserThread(fileQueue, iPutter, iMatcher, factory);
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
