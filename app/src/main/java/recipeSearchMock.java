/**
 * Created by gunnarmarhardarson on 12/02/2018.
 */

public class recipeSearchMock {

    private String name;
    private String[] ingredients;

    public recipeSearchMock(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
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
}