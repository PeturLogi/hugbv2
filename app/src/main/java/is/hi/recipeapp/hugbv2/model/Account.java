package is.hi.recipeapp.hugbv2.model;

/**
 * @Date April 2018
 * Klasi sem inniheldur aðgangs hlut (e. Account object)
 * sem hægt að setja í gagnagrunn.
 */



public class Account {

    private String name;
    private String adress;
    private String email;
    private String mobileNr;
    private String password;

    public Account (String accName, String addrs, String mail, String mobile, String pass) {
        this.name = accName;
        this.adress = addrs;
        this.email = mail;
        this.mobileNr = mobile;
        this.password = pass;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNr() {
        return mobileNr;
    }

    public String getPassword() {
        return password;
    }
}
