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
        System.out.println("lololoo1");
        System.out.println("lololoo1 "+crawlingData.getUrls());
        Crawler crawler = new Crawler(1, crawlingData.getMaxNumberOfFiles(), crawlingData.getMaxDepth(), crawlingData.getUrls());
        System.out.println("lololoo1.5");
        System.out.println(crawler.getFileQueue());
        System.out.println("111");
        System.out.println(crawlingData.getMaxDepth());
        System.out.println(patternMatcher);
        System.out.println("112");
        System.out.println(crawler.getTaskQueue());
        System.out.println("113");
        Parser parser = new Parser(crawler.getFileQueue(),
                crawler.getTaskQueue(), patternMatcher, 1);
        System.out.println("lololoo2");
        Ploter ploter = new Ploter();
        System.out.println("lololoo3");
        ViewFrame viewFrame = new ViewFrame();
        System.out.println("lololoo4");
        ploter.setViewFrame(viewFrame);
        System.out.println("lololoo5");
        viewFrame.setPloter(ploter);
        System.out.println("lololoo6");
        patternMatcher.addListener(ploter);
        System.out.println("lololoo7");
        crawler.startCrawling();
        System.out.println("lololoo8");
    }
}