package is.hi.recipeapp.hugbv2.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pétur Logi Pétursson on 3/27/2018.
 */

public class SousChefRepository {
    private static SousChefRepository sRepo;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static SousChefRepository get(Context context) {
        if (sRepo == null) {
            sRepo = new SousChefRepository(context);
        }
        return sRepo;
    }

    private SousChefRepository(Context context) {
        mContext = context.getApplicationContext();

        /**
         * Hér er skráin /data/data/hugbv2/databases/SousChef.db búin til
         * Þegar kallað er á onCreate (SQLiteDatabase) þá er fyrsta uppskriftin vistuð
         * annars ef fjöldi uppskrifta í SousChefHelper er hærra þá þarf að kalla á onUpgrade
         */
        Log.i("SousChefRepository", "SousChefDbHelper");
        mDatabase = new SousChefDbHelper(mContext).getWritableDatabase();
    }

    public void addRecipe(String email, String id) {
        ContentValues values = getContentValues(email, id);
        mDatabase.insert(MyRecipesContract.MyRecipesTable.NAME, null, values);
    }

    public List<String> getRecipes() {
        List<String> recipes = new ArrayList<>();
        RecipeCursorWrapper cursor = queryRecipes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                recipes.add(cursor.getRecipe());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return recipes;
    }

    public void removeRecipe(String id) {
        mDatabase.delete(MyRecipesContract.MyRecipesTable.NAME,
                MyRecipesContract.MyRecipesTable.Cols.RECIPID + " = ?",
                new String[] {id});
    }

    private RecipeCursorWrapper queryRecipes (String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MyRecipesContract.MyRecipesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new RecipeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(String mail, String id) {
        ContentValues values = new ContentValues();
        values.put(MyRecipesContract.MyRecipesTable.Cols.EMAIL, mail);
        values.put(MyRecipesContract.MyRecipesTable.Cols.RECIPID, id);

        return values;
    }
}
