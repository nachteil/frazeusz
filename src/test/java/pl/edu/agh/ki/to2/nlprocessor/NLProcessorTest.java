package pl.edu.agh.ki.to2.nlprocessor;
//import junit.framework.TestCase;

import org.junit.Before;
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
    public void returnsSetOfSynonyms(){
        Set<String> result= nlProcessor.getSynonyms("nuda");
        Set<String> expected_result = new HashSet<String>(Arrays.asList("nuda", "znudzenie"));
        assertEquals(result,expected_result);
    }


    @Test
    public void returnsEmptySetOfSynonyms(){
        Set<String> result= nlProcessor.getSynonyms("toNieJEstSLowo");
        Set<String> expected_result = new HashSet<String>();
        assertEquals(expected_result,result);
    }

    @Test
    public void returnsSetOfDiminutives(){
        Set<String> result= nlProcessor.getDiminutives("dom");
        Set<String> expected_result = new HashSet<String>(Arrays.asList("domina", "domek"));
        assertEquals(expected_result,result);}

    @Test
    public void returnsEmptySetOfDiminutives(){
        Set<String> result= nlProcessor.getDiminutives("toNieJEstSLowo");
        Set<String> expected_result = new HashSet<String>();
        assertEquals(expected_result, result);
    }
    //TODO brak polskich znakow powoduje assert
    @Test
    public void returnSetOfVariants(){
        Set<String> result= nlProcessor.getVariants("Atlantydy");
        Set<String> expected_result = new HashSet<String>(Arrays.asList( "Atlantydy", "Atlantydach", "Atlantydami", "Atlantydom"));
        assertEquals(expected_result, result);
    }

    @Test
    public void returnEmptySetOfVariants(){
        Set<String> result= nlProcessor.getVariants("toNieJEstSLowo");
        Set<String> expected_result = new HashSet<String>();
        assertEquals(expected_result,result);
    }

}