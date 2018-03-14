package is.hi.recipeapp.hugbv2.model;

/**
 * Created by Pétur Logi Pétursson on 2/13/2018.
 * A class that contains an Account object that can be inserted into a database
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
