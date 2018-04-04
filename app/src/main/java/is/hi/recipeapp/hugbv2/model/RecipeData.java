package is.hi.recipeapp.hugbv2.model;

/**
 * @date april 2018
 *
 * Klasi sem heldur utan um klasana Attribution og Matches
 */

public class RecipeData {
    private Attribution mAttribution;
    private Matches[] mMatches;

    //getters og setters

    public Attribution getAttribution() {
        return mAttribution;
    }

    public void setAttribution(Attribution attribution) {
        mAttribution = attribution;
    }

    public Matches[] getMatches() {
        return mMatches;
    }

    public void setMatches(Matches[] matches) {
        mMatches = matches;
    }
}
