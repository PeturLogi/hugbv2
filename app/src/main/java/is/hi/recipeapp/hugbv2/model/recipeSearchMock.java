package is.hi.recipeapp.hugbv2.model;



import android.graphics.Bitmap;

/**
 * Created by gunnarmarhardarson on 12/02/2018.
 */

public class recipeSearchMock {

    private String name;
    private String[] ingredients;

    // private Bitmap bitmap;

    public recipeSearchMock(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;

       // this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    /*
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
     */
}