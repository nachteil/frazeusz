package pl.edu.agh.ki.to2.crawler.model;

import java.net.URL;

public class ParserFile {

    private String mime;
    private String content;
    private int depth;
    private URL url;

    public ParserFile(String mime, String content, int depth, URL url) {
        this.mime = mime;
        this.content = content;
        this.depth = depth;
        this.url = url;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

}

