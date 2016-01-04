package pl.edu.agh.ki.to2.nlprocessor;
//import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zuch on 2015-12-10.
 */
public class NLProcessorTest {
    NLProcessor nlProcessor;
    @Before
    public void initialize(){
        nlProcessor = new NLProcessor();
    }

    @Test
    @Ignore("This was commented out")
    public void returnsSetOfSynonyms(){
        Set<String> result= nlProcessor.getSynonyms("nuda");
        Set<String> expected_result = new HashSet<String>(Arrays.asList("nuda", "znudzenie"));
        assertEquals(result,expected_result);
    }


    @Test
    @Ignore("This was commented out")
    public void returnsEmptySetOfSynonyms(){
        Set<String> result= nlProcessor.getSynonyms("toNieJEstSLowo");
        Set<String> expected_result = new HashSet<String>();
        assertEquals(expected_result,result);
    }

    @Test
    @Ignore("This was commented out")
    public void returnsSetOfDiminutives(){
        Set<String> result= nlProcessor.getDiminutives("dom");
        Set<String> expected_result = new HashSet<String>(Arrays.asList("domina", "domek"));
        assertEquals(expected_result,result);}

    @Test
    @Ignore("This was commented out")
    public void returnsEmptySetOfDiminutives(){
        Set<String> result= nlProcessor.getDiminutives("toNieJEstSLowo");
        Set<String> expected_result = new HashSet<String>();
        assertEquals(expected_result, result);
    }

    @Ignore("brak polskich znakow powoduje assert") // TODO
    @Test
    public void returnSetOfVariants(){
        Set<String> result= nlProcessor.getVariants("aberracja");
        Set<String> expected_result = new HashSet<>(Arrays.asList( "aberracje", "aberracjach", "aberracji", "aberracjom", "aberracjami", "aberracjo", "aberracją", "aberracja","aberrację"));
        assertEquals(expected_result, result);
    }

    @Test
    @Ignore("Enters endless loop")
    public void returnEmptySetOfVariants(){
        Set<String> result= nlProcessor.getVariants("toNieJEstSLowo");
        Set<String> expected_result = new HashSet<>();
        assertEquals(expected_result,result);
    }
}