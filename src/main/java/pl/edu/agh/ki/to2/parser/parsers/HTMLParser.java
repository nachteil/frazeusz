package pl.edu.agh.ki.to2.parser.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.agh.ki.to2.parser.parsingControl.ParserFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Adam on 29.11.2015.
 */
public class HTMLParser implements IFileParser {

    public HTMLParser(){};

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {
    	
    	//TODO Implement getting String from file
    	
    	Document doc = Jsoup.parse(parserFile.getFile().toString()); ///not working
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
    	
    	//TODO getting content from file.
    	Document doc = Jsoup.parse(parserFile.getFile().toString()); //not working
    	Elements elements = doc.body().select("*");
    	List<String> sentences = new ArrayList<String>();
    	for (Element element : elements) 
		{
			if (element.ownText().trim().length() > 1)
			{
				for (String sentence : element.ownText().split("[^.?!]*?[.?!]+")){
					sentences.add(sentence);
				}
			}
		}
        return sentences;
    }
}
