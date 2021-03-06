package pl.edu.agh.ki.to2.crawler;

import pl.edu.agh.ki.to2.crawler.data.CrawlingData;
import pl.edu.agh.ki.to2.crawler.gui.DataFrame;
import pl.edu.agh.ki.to2.crawler.gui.controllers.DateFrameController;
import pl.edu.agh.ki.to2.crawler.downloader.Crawler;
import pl.edu.agh.ki.to2.monitor.Monitor;
import pl.edu.agh.ki.to2.nlprocessor.NLProcessor;
import pl.edu.agh.ki.to2.parser.Parser;
import pl.edu.agh.ki.to2.patternmatcher.PatternMatcher;
import pl.edu.agh.ki.to2.plotter.Ploter;
import pl.edu.agh.ki.to2.plotter.ViewFrame;

import java.awt.*;
import java.net.MalformedURLException;

public class Main {

    static PatternMatcher patternMatcher;

    public static void main(String args[]) throws InterruptedException, MalformedURLException {
        NLProcessor nlProcessor = new NLProcessor();
        Monitor monitor = Monitor.getInstance();
        patternMatcher = new PatternMatcher(nlProcessor, monitor.getMonitorPubSub());
        DateFrameController dateFrameController = new DateFrameController(patternMatcher);
        EventQueue.invokeLater(() -> new DataFrame(dateFrameController));
    }

    public static void start(CrawlingData crawlingData) throws InterruptedException {
        Crawler crawler = new Crawler(200, crawlingData.getMaxNumberOfFiles(), crawlingData.getMaxDepth(), crawlingData.getUrls());
        Parser parser = new Parser(crawler.getFileQueue(), crawler.getTaskQueue(),
                patternMatcher, 200);
        Ploter ploter = new Ploter();
        ViewFrame viewFrame = new ViewFrame();
        ploter.setViewFrame(viewFrame);
        viewFrame.setPloter(ploter);
        patternMatcher.addListener(ploter);
        crawler.startCrawling();
    }
}