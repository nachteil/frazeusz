package pl.edu.agh.ki.to2.nlprocessor;


import java.util.Set;

public interface IWordProvider {
    public Set<String> getVariants(String word);
    public Set<String> getDiminutives(String word);
    public Set<String> getSynonyms(String word);
}
