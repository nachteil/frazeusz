package pl.edu.agh.ki.to2.crawler.gui.controllers;


import pl.edu.agh.ki.to2.crawler.Main;
import pl.edu.agh.ki.to2.crawler.data.CrawlingData;
import pl.edu.agh.ki.to2.crawler.gui.DataFrame;

/**
 *
 * Created by Rav on 2015-12-02.
 */
public class DateFrameController {

    private CrawlingData crawlingData;
    private DataFrame dataFrame;

    public DateFrameController() {
        this.crawlingData = new CrawlingData();
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
}
