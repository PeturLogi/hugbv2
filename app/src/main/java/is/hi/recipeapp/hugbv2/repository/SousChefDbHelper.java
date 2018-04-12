package is.hi.recipeapp.hugbv2.repository;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Pétur Logi Pétursson on 3/27/2018.
 */

public class SousChefDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SousChef.db";

    public SousChefDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("Gagnagrunnur", "onCreate");

        database.execSQL("create table " + MyRecipesContract.MyRecipesTable.NAME + "(" +
        //"_id integer primary key autoincrement, " +
                MyRecipesContract.MyRecipesTable.Cols.RECIPID + " TEXT," +
                MyRecipesContract.MyRecipesTable.Cols.EMAIL  + " TEXT," +
                " primary key(" + MyRecipesContract.MyRecipesTable.Cols.RECIPID  + "," +
                MyRecipesContract.MyRecipesTable.Cols.EMAIL + "))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldversion, int newVersion) {

    }
}
