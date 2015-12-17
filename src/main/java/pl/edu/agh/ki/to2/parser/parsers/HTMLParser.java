package pl.edu.agh.ki.to2.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.edu.agh.ki.to2.parser.parsers.IFileParser;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.nio.file.Files;
import java.nio.charset.Charset;

/**
 * Created by Adam on 29.11.2015.
 */
public class HTMLParser implements IFileParser {

    public HTMLParser(){};

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {

		Document doc = Jsoup.parse(parserFile.getContent());
    	doc.setBaseUri(parserFile.getUrl().toString());
    	Elements links = doc.select("a");
    	Set<URL> urls = new HashSet<URL>();
    	for(Element link: links){
    		//System.out.println("Url: " + link.attr("abs:href"));
    		try {
				urls.add(new URL(link.attr("abs:href")));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
        return urls;
    }

    @Override
    public List<String> getSentences(ParserFile parserFile) {

		Document doc = Jsoup.parse(parserFile.getContent());
    	Elements elements = doc.body().select("*");
    	List<String> sentences = new ArrayList<String>();
    	for (Element element : elements) 
		{
			if (element.ownText().trim().length() > 1)
			{
				for (String sentence : element.ownText().split("\\.")){
					sentences.add(sentence.trim());
				}
			}
		}
        return sentences;
    }
}
