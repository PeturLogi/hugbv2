package is.hi.recipeapp.hugbv2.model;

/**
 * @Date
 */

public class Recipe {
    String[] ingredientLines;
    Nutrition[] mNutrition;
    String image;
    String name;
    String yield;
    String totalTime;
    String[] cuisine;
    double rating;
    String numberOfServings;
    String sourceUrl;


    public String[] getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(String[] ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public Nutrition[] getNutrition() {
        return mNutrition;
    }

    public void setNutrition(Nutrition[] nutrition) {
        mNutrition = nutrition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String[] getCuisine() {
        return cuisine;
    }

    public void setCuisine(String[] cuisine) {
        this.cuisine = cuisine;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(String numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
