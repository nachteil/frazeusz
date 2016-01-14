package pl.edu.agh.ki.to2.plotter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.edu.agh.ki.to2.patternmatcher.models.SearchPattern;
import pl.edu.agh.ki.to2.plotter.model.Occurrences;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Nina on 2016-01-09.
 */
public class Xml implements IFile {

    @Override
    public void save(Map<SearchPattern, Occurrences> data, String path) {
        String name = path + ".xml";

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("searches");
            doc.appendChild(rootElement);

            SearchPattern key;
            Map<String, List<String>> map;
            String url;
            List<String> list;

            Element searchPattern;
            Element occurrence;
            Element match;

            for (Map.Entry<SearchPattern, Occurrences> entry : data.entrySet()) {
                key = entry.getKey();
                map = entry.getValue().getUrlSentenceMap();

                // each search pattern
                searchPattern = doc.createElement("search_pattern");
                rootElement.appendChild(searchPattern);

                // set attribute to searchPattern element
                searchPattern.setAttribute("pattern", key.getPattern());
                searchPattern.setAttribute("case_sensitive", String.valueOf(key.getCaseSensitive()));
                searchPattern.setAttribute("synonyms", String.valueOf(key.getSynonyms()));
                searchPattern.setAttribute("variants", String.valueOf(key.getVariants()));
                searchPattern.setAttribute("diminutives", String.valueOf(key.getDiminutives()));

                for (Map.Entry<String, List<String>> lowerEntry : map.entrySet()) {
                    url = lowerEntry.getKey();
                    list = lowerEntry.getValue();

                    if(!list.isEmpty()) {
                        occurrence = doc.createElement("occurrence");
                        searchPattern.appendChild(occurrence);

                        occurrence.setAttribute("url", url);
                        for (String value : list) {

                            match = doc.createElement("match");
                            match.appendChild(doc.createTextNode(value));
                            occurrence.appendChild(match);

                        }
                    }
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(name));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}