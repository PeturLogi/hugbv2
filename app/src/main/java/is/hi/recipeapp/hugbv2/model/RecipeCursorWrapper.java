package is.hi.recipeapp.hugbv2.model;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by Pétur Logi Pétursson on 3/28/2018.
 */

public class RecipeCursorWrapper extends CursorWrapper {
    public RecipeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public String getRecipe() {
        String id = getString(getColumnIndex(MyRecipesContract.MyRecipesTable.Cols.RECIPID));

        return id;
    }
}
