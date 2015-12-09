package pl.edu.agh.ki.to2.monitor.contract;

public enum EventType {

    PAGES_CRAWLED,
    KILOBYTES_DOWNLOADED,
    SENTENCES_MATCHED;

    public String value() {
        return name();
    }

    public static EventType fromValue(String v) {
        return valueOf(v);
    }

}