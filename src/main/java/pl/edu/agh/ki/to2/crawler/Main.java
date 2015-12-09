package pl.edu.agh.ki.to2.crawler;

import pl.edu.agh.ki.to2.crawler.data.CrawlingData;
import pl.edu.agh.ki.to2.crawler.gui.DataFrame;
import pl.edu.agh.ki.to2.crawler.gui.controllers.DateFrameController;
import pl.edu.agh.ki.to2.crawler.model.Crawler;
import pl.edu.agh.ki.to2.nlprocessor.NLProcessor;

import java.awt.*;
import java.net.MalformedURLException;

public class Main {

    static PatternMatcher patternMatcher;

    public static void main(String args[]) throws InterruptedException, MalformedURLException {
        NLProcessor nlProcessor = new NLProcessor();
        patternMatcher = new PatternMatcher(nlProcessor);
        Plotter plotter = new Plotter();
        patternMatcher.addObserver(plotter);
        DateFrameController dateFrameController = new DateFrameController();
        EventQueue.invokeLater(() -> new DataFrame(dateFrameController));
    }

    public static void start(CrawlingData crawlingData) throws InterruptedException {

        Crawler crawler = new Crawler(100, 500);
        Parser parser = new Parser(crawler.getFileQueue(),
                crawler.getTaskQueue(), patternMatcher, crawlingData.getMaxDepth());
        parser.start();
        crawler.startCrawling();
    }
}