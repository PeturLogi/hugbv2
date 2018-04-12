package is.hi.recipeapp.hugbv2.repository;

import java.io.Serializable;
import java.util.ArrayList;

import is.hi.recipeapp.hugbv2.model.Account;

/**
 * Klasi sem geymir Account breytur
 *
 * Created by Pétur Logi Pétursson on 4/11/2018.
 */

public class AccountRepo extends Account implements Serializable{
    // Listi yfir alla Accounts
    private ArrayList<Account> accountList = new ArrayList<>();

    // Bætir við aðgangi í kerfið
    public void addAccount(Account acc) {
        accountList.add(acc);
    }

    // Uppfærir aðgang
    public void updateAccount(Account acc) {
        //TODO
    }

    // Eyðir aðgangi
    public void deleteAccount(Account u) {
        //TODO
    }

    public ArrayList<Account> getAccounts() {
        return accountList;
    }



}
