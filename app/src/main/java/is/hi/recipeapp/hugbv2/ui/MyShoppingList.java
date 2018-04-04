package is.hi.recipeapp.hugbv2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.HashSet;
import java.util.Set;
import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.Activity;

import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.TextView;
import android.view.View;

import is.hi.recipeapp.hugbv2.R;

/**
 * Created by Brynjar Árnason on 02/04/2018.
 * HBV601G Hugbúnaðarverkefni 2
 * Háskóli Íslands
 *
 * Sýnir Innkaupalista þar sem notandi getur bætt við og eytt hráefnum
 */
public class MyShoppingList extends AppCompatActivity {

    ArrayList<String> shoppingList = null;
    ArrayAdapter<String> adapter = null;
    ListView lv = null;

    /**
     * Smiður
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshoppinglist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // Sýnir Toolbar
        setSupportActionBar(toolbar); // viðauki við Toolbar

        shoppingList = getArrayVal(getApplicationContext());
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoppingList);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, final int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItem.trim().equals(shoppingList.get(position).trim())) {
                    removeElement(selectedItem, position);
                } else {
                    Toast.makeText(getApplicationContext(),"Error Removing Element", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * heldur utan um viðmót sem gefur aðgang að innkaupalistanum
     * @param menu
     * @return onCreateOptionsMenu(menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menushoppinglist,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Birtir viðmót sem gefur val um að bæta við hráefni á lista
     * þegar smellt er á hnapp
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Item");
            final EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    shoppingList.add(preferredCase(input.getText().toString()));
                    Collections.sort(shoppingList);
                    storeArrayVal(shoppingList, getApplicationContext());
                    lv.setAdapter(adapter);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Skilar hráefni á lista með stórum staf ef hráefni er ritað með litlum staf í upphafi
     * @param original
     * @return Uppercase
     */
    public static String preferredCase(String original)
    {
        if (original.isEmpty())
            return original;

        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }

    /**
     * Sér til þess að hráefni á lista geymist með SharedPreferences
     * og hægt sé að nálgast hann næst þegar opnað er listann
     * @param inArrayList
     * @param context
     */
    public static void storeArrayVal( ArrayList<String> inArrayList, Context context)
    {
        Set<String> WhatToWrite = new HashSet<String>(inArrayList);
        SharedPreferences WordSearchPutPrefs = context.getSharedPreferences("dbArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = WordSearchPutPrefs.edit();
        prefEditor.putStringSet("myArray", WhatToWrite);
        prefEditor.commit();
    }

    /**
     * Birtir hráefni sem geymd hafa verið með SharedPreferences
     * @param dan
     * @return ArrayList<String>(tempSet)
     */
    public static ArrayList getArrayVal( Context dan)
    {
        SharedPreferences WordSearchGetPrefs = dan.getSharedPreferences("dbArrayValues",Activity.MODE_PRIVATE);
        Set<String> tempSet = new HashSet<String>();
        tempSet = WordSearchGetPrefs.getStringSet("myArray", tempSet);
        return new ArrayList<String>(tempSet);
    }

    /**
     * Birtir staðfestingu á hvort það eigi að eyða hlut út af lista
     * þegar ýtt er á hlut. Eyðir hráefni út af lista varanlega ef staðfesting er gefin
     * @param selectedItem
     * @param position
     */
    public void removeElement(String selectedItem, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove " + selectedItem + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shoppingList.remove(position);
                Collections.sort(shoppingList);
                storeArrayVal(shoppingList, getApplicationContext());
                lv.setAdapter(adapter);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
