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
                // System.out.println("FOUND URL: " + link.attr("abs:href"));
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
		for (Element element : elements)
		{
			if (element.ownText().trim().length() > 1)
			{
				sentences.addAll(Extractor.extractSentences(element.ownText()));
			}
		}
		return sentences;
	}
}