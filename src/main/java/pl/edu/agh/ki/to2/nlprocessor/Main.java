package pl.edu.agh.ki.to2.nlprocessor;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        NLProcessor nlp = new NLProcessor();
        Set<String> dim = nlp.getSynonyms("nuda");
        System.out.println(dim);
        Set<String> var = nlp.getVariants("aberracja");
        System.out.println(var);
    }

}
