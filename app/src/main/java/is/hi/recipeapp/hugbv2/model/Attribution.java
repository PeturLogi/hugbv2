package is.hi.recipeapp.hugbv2.model;

/**
 * @Date April 2018
 *
 * Attrubution klasi inniheldur html, url, text og logo, heldur utan um
 * höfundarétt API's
 */
public class Attribution {

    private String html;
    private String url;
    private String text;
    private String logo;

    //getters og setters

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
