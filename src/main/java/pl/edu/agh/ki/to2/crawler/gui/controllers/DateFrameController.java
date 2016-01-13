package pl.edu.agh.ki.to2.crawler.gui.controllers;


import pl.edu.agh.ki.to2.crawler.Main;
import pl.edu.agh.ki.to2.crawler.data.CrawlingData;
import pl.edu.agh.ki.to2.crawler.gui.DataFrame;
import pl.edu.agh.ki.to2.patternmatcher.PatternMatcher;

/**
 *
 * Created by Rav on 2015-12-02.
 */
public class DateFrameController {

    private CrawlingData crawlingData;
    private DataFrame dataFrame;
    private PatternMatcher patternMatcher;

    public DateFrameController(PatternMatcher patternMatcher) {
        this.crawlingData = new CrawlingData();
        this.patternMatcher = patternMatcher;
    }

    public void deleteUrl(){
        String deletedUrl = (String) dataFrame.getInitialDataPanel().getListOfUrlsPane().getSelectedValue();
        crawlingData.getUrls().remove(deletedUrl);
        dataFrame.getInitialDataPanel().getModel().removeElement(deletedUrl);
    }

    public void addUrl(){
        String toAdd = dataFrame.getInitialDataPanel().getAddUrlField().getText();
        crawlingData.getUrls().add(toAdd);
        dataFrame.getInitialDataPanel().getModel().addElement(toAdd);
    }

    public void start() {
        crawlingData.setMaxDepth(Integer.parseInt(dataFrame.getInitialDataPanel().getMaxDepthField().getText()));
        crawlingData.setMaxNumberOfFiles(Integer.parseInt(dataFrame.getInitialDataPanel().getMaxNumberOfFilesField().getText()));
        crawlingData.setSpeed(Integer.parseInt(dataFrame.getInitialDataPanel().getSpeedField().getText()));
        try {
            Main.start(crawlingData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close(){

    }

    public void setDateFrame(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public CrawlingData getCrawlingData() {
        return crawlingData;
    }

    public PatternMatcher getPatternMatcher() {
        return patternMatcher;
    }

    public String checkErrors() {
        try {
            Integer.parseInt(dataFrame.getInitialDataPanel().getMaxDepthField().getText());
        } catch (NumberFormatException e) {
            return "Wrong format of Max depth field";
        }
        try {
            Integer.parseInt(dataFrame.getInitialDataPanel().getMaxNumberOfFilesField().getText());
        } catch (NumberFormatException e) {
            return "Wrong format of Max number of files field";
        }
        return null;
    }
}
