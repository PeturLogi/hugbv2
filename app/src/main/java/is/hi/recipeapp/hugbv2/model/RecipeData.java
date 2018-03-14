package is.hi.recipeapp.hugbv2.model;

/**
 * Created by Pétur Logi Pétursson on 3/12/2018.
 */

public class RecipeData {
    private Attribution mAttribution;
    private Matches[] mMatches;

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
