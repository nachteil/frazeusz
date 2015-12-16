package pl.edu.agh.ki.to2.crawler.downloader;

import pl.edu.agh.ki.to2.parser.exceptions.UnsupportedFileException;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class DownloadTask implements Runnable {

    private URL url;
    private int depth;
    private BlockingQueue<ParserFile> fileQueue;
    private Counter counter;
    private final int BUFFER_SIZE = 4096;
    private int maxStreamSize;

    public DownloadTask(Counter counter, int depth,
                        BlockingQueue<ParserFile> fileQueue,
                        int maxStreamSize, URL url) {
        this.counter = counter;
        this.depth = depth;
        this.fileQueue = fileQueue;
        this.maxStreamSize = maxStreamSize;
        this.url = url;
    }

    public URL getURL() {
        return url;
    }

    @Override
    public void run() {
        try {
            ParserFile parserFile = getContent();
            if (parserFile == null)
                return;
            fileQueue.put(parserFile);
        } catch (IOException | InterruptedException | UnsupportedFileException e) {
            e.printStackTrace();
        }
        counter.increasePagesCounter();
    }

    private ParserFile getContent() throws IOException, UnsupportedFileException {

        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        String contentType;
        StringBuilder stringBuilder;
        byte[] buf;
        InputStream inputStream;
        int size, contentLength, downloadedSize;

        // check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            contentType = httpConn.getContentType();
            contentLength = httpConn.getContentLength();
            downloadedSize = 0;

            // opens input stream from the HTTP connection
            inputStream = httpConn.getInputStream();

            // convert InputStream to String
            stringBuilder = new StringBuilder();
            buf = new byte[BUFFER_SIZE];
            while ((size = inputStream.read(buf)) != -1 &&
                    downloadedSize < maxStreamSize - BUFFER_SIZE) {
                stringBuilder.append(new String(buf, 0, size));
                downloadedSize += size;
            }

            counter.updateDataCounter(downloadedSize);
            inputStream.close();

        } else {
            httpConn.disconnect();
            return null;
        }

        httpConn.disconnect();
        return new ParserFile(contentType, stringBuilder.toString(), url,
                depth + 1);
    }
}
