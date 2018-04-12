package is.hi.recipeapp.hugbv2.model;

import java.util.UUID;

/**
 * @Date April 2018
 * Klasi sem inniheldur aðgangs hlut (e. Account object)
 * sem hægt að setja í gagnagrunn.
 */



public class Account {

    private String id = "";
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

    public Account() {

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

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNr(String mobileNr) {
        this.mobileNr = mobileNr;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
