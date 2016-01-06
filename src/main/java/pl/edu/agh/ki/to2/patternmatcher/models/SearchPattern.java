package pl.edu.agh.ki.to2.patternmatcher.models;

public class SearchPattern {
    private String pattern = "";
    private Boolean caseSensitive = false;
    private Boolean synonyms = false;
    private Boolean variants = false;
    private Boolean diminutives = false;

    public SearchPattern() {
    }

    public SearchPattern(String pattern) {
        this.pattern = pattern;
    }

    public SearchPattern(String pattern, Boolean caseSensitive, Boolean synonyms, Boolean variants, Boolean diminutives) {
        this.pattern = pattern;
        this.caseSensitive = caseSensitive;
        this.synonyms = synonyms;
        this.variants = variants;
        this.diminutives = diminutives;
    }

    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Boolean getCaseSensitive() {
        return caseSensitive;
    }
    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public Boolean getSynonyms() {
        return synonyms;
    }
    public void setSynonyms(Boolean synonyms) {
        this.synonyms = synonyms;
    }

    public Boolean getVariants() {
        return variants;
    }
    public void setVariants(Boolean variants) {
        this.variants = variants;
    }

    public Boolean getDiminutives() {
        return diminutives;
    }
    public void setDiminutives(Boolean diminutives) {
        this.diminutives = diminutives;
    }

    public String getDescription() {
        StringBuilder builder = new StringBuilder(pattern);
        builder.append("\n");
        if (caseSensitive)
            builder.append("\t+ wielkie litery\n");
        if (synonyms)
            builder.append("\t+ synonimy\n");
        if (variants)
            builder.append("\t+ odmiany\n");
        if (diminutives)
            builder.append("\t+ skróty\n");

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof SearchPattern)){
            return false;
        }
        SearchPattern that = (SearchPattern) obj;

        return this.pattern.equals(that.pattern)
                && this.caseSensitive.equals(that.caseSensitive)
                && this.synonyms.equals(that.synonyms)
                && this.variants.equals(that.variants)
                && this.diminutives.equals(that.diminutives);
    }

    @Override
    public int hashCode() {
        int result = pattern.hashCode();
        result = 31 * result + caseSensitive.hashCode();
        result = 31 * result + synonyms.hashCode();
        result = 31 * result + variants.hashCode();
        result = 31 * result + diminutives.hashCode();
        return result;
    }
}