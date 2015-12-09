package pl.edu.agh.ki.to2.crawler.model;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class DownloadTask implements Runnable{

    private URL url;
    private int depth;
    private BlockingQueue<ParserFile> fileQueue;
    private Counter counter;

    public DownloadTask(URL url, int depth, BlockingQueue<ParserFile> fileQueue,
                        Counter counter) {
        this.url = url;
        this.depth = depth;
        this.fileQueue = fileQueue;
        this.counter = counter;
    }

    public URL getURL() {
        return url;
    }

    @Override
    public void run() {
        try {
            String content = getPage(url);

            fileQueue.put(new ParserFile(
                    new MimetypesFileTypeMap().getContentType(url.getFile()),
                    content, depth + 1, url));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter.increase();
    }

    public static String getPage(URL url) {

        // Get stream of the response.
        int chunk = 1024;
        int c;
        InputStream in = null;

        // Store results in StringBuilder.
        StringBuilder builder = new StringBuilder();
        byte[] data = new byte[chunk];
        try {
            in = url.openStream();

            // Read in the response into the buffer.
            // ... Read many bytes each iteration.
            while ((c = in.read(data, 0, chunk)) != -1) {
                builder.append(new String(data, 0, c));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return String.
        return builder.toString();
    }
}
