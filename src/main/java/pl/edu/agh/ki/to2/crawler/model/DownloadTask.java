package pl.edu.agh.ki.to2.crawler.model;

import pl.edu.agh.ki.to2.parser.exceptions.UnsupportedFileException;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class DownloadTask implements Runnable{

    private URL url;
    private int depth;
    private BlockingQueue<ParserFile> fileQueue;
    private Counter counter;
    private String tempDir;
    private final int BUFFER_SIZE = 4096;

    public DownloadTask(URL url, int depth, BlockingQueue<ParserFile> fileQueue,
                        Counter counter, String tempDir) {
        this.url = url;
        this.depth = depth;
        this.fileQueue = fileQueue;
        this.counter = counter;
        this.tempDir = tempDir;
    }

    public URL getURL() {
        return url;
    }

    @Override
    public void run() {
        try {
            File file = downloadFile();
            if(file == null)
                return;
            fileQueue.put(new ParserFile(file, url, depth + 1));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedFileException e) {
            e.printStackTrace();
        }
        counter.increase();
    }

    private File downloadFile() throws IOException {

        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        String fileName = "";
        String urlPath = url.toString();
        String saveFilePath;

        // check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = httpConn.getHeaderField("Content-Disposition");
            //String contentType = httpConn.getContentType();
            //int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1,
                        urlPath.length());
            }

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            saveFilePath = tempDir + File.separator + fileName;


            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

        } else {
            httpConn.disconnect();
            return null;
        }
        httpConn.disconnect();
        return new File(saveFilePath);
    }
}
