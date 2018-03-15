package is.hi.recipeapp.hugbv2.model;

/**
 * Created by Pétur Logi Pétursson on 3/14/2018.
 */

public class Nutrition {
    String attribute;
    String desctiption;
    double value;
    String[] unit;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
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
