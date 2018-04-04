package is.hi.recipeapp.hugbv2.model;

/**
 * Created by Pétur Logi Pétursson on 3/27/2018.
 */

public class MyRecipesContract {
    public static final class MyRecipesTable {
        public static final String NAME = "recipes";

        public final class Cols {
            public static final String EMAIL = "email";
            public static final String RECIPID = "recipeID";
        }
    }
}
