package is.hi.recipeapp.hugbv2.model;

/**
 * Klasi inniheldur attribute, description, value
 * og unit, heldur utan um næringargildi uppskriftana úr API.
 */

public class Nutrition {
    String attribute;
    String description;
    double value;
    String[] unit;

    //getters og setters

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String[] getUnit() {
        return unit;
    }

    public void setUnit(String[] unit) {
        this.unit = unit;
    }
}
