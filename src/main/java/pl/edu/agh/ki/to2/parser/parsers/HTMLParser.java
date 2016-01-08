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
 * Created by lis on 08.01.16.
 * @author lis
 */

//TODO test me

public class HTMLParser implements IFileParser {

    public HTMLParser(){}

    @Override
    public Set<URL> getUrls(ParserFile parserFile) {

        Set<URL> urls = new HashSet<>();

        // Getting document from string content
		Document doc = Jsoup.parse(parserFile.getContent());
    	doc.setBaseUri(parserFile.getUrl().toString());

        //Extracting links
        Elements links = doc.select("a");
    	for(Element link: links){
    		try {
				urls.add(new URL(link.attr("abs:href")));
                System.out.println("HTML FOUND URL: " + link.attr("abs:href"));
			} catch (MalformedURLException e) {
                // e.printStackTrace();
			}
    	}
    	
        return urls;
    }

    @Override
    public List<String> getSentences(ParserFile parserFile) {

        List<String> sentences = new ArrayList<>();

        // Getting all html content
		Document doc = Jsoup.parse(parserFile.getContent());
    	Elements elements = doc.body().select("*");

        // Extracting sentences
        // TODO smarter way to extract sentences
        for (Element element : elements)
		{
			if (element.ownText().trim().length() > 1)
			{
				for (String sentence : element.ownText().split("\\.")){
                    if(!(sentence = sentence.trim()).isEmpty()) {
                        sentences.add(sentence);
                        System.out.println("HTML FOUND SENTENCE: " + sentence);
                    }
				}
			}
		}
        return sentences;
    }
}
