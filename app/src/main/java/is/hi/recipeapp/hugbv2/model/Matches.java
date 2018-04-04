package is.hi.recipeapp.hugbv2.model;

/**
 *
 * @Date april 2018
 *
 * Klasi sem inniheldur cures, cuisine, rating, id
 * smallImageUrls, sourceDisplayName, totalTimeSeconds,
 * ingredients, recipename, heldur utan um
 * uppbyggingu á leitarniðurstöðum úr API
 *
 */

public class Matches {

    private String[] course;
    private String[] cuisine;
    private Double rating;
    private String id;
    private String smallImageUrls[];
    private String sourceDisplayName;
    private int totalTimeSeconds;
    private String[] ingredients;
    private String recipeName;

//getters og setters

    public String[] getCourse() {
        return course;
    }

    public void setCourse(String[] course) {
        this.course = course;
    }

    public String[] getCuisine() {
        return cuisine;
    }

    public void setCuisine(String[] cuisine) {
        this.cuisine = cuisine;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getSmallImageUrls() {
        return smallImageUrls;
    }

    public void setSmallImageUrls(String[] smallImageUrls) {
        this.smallImageUrls = smallImageUrls;
    }

    public String getSourceDisplayName() {
        return sourceDisplayName;
    }

    public void setSourceDisplayName(String sourceDisplayName) {
        this.sourceDisplayName = sourceDisplayName;
    }

    public int getTotalTimeSeconds() {
        return totalTimeSeconds;
    }

    public void setTotalTimeSeconds(int totalTimeSeconds) {
        this.totalTimeSeconds = totalTimeSeconds;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
