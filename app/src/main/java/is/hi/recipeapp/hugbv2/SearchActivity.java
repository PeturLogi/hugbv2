package is.hi.recipeapp.hugbv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import java.util.ArrayList;
import static android.R.layout.simple_list_item_2;

public class SearchActivity extends AppCompatActivity {

    // private CheckBox checkBoxCicken, checkBoxBeef, checkBoxfish;
    private Button showRecipe;
    private ListView listView;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    EditText editText;

    // Login variables
    private Button mLoginButton;
    private static final int REQUEST_CODE_LOGIN = 0;

    // Mock ingredients variables
    private String[] ingredientsGrillCheese = {"Cheese", "Ham"};
    private String[] ingredientsHotDog = {"Ketchup", "Fried Onion", "Mustard"};
    private String[] ingredientsPasta = {"Pasta", "Pepperoni", "Ham", "Carrots", "Bacon"};
    private String[] ingredientsCocoPuffs = {"Coco Puffs", "Milk"};

    // ArrayLists
    ArrayList<recipeSearchMock> allRecipies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
      
        /*
        checkBoxCicken = (CheckBox) findViewById(R.id.checkBoxChicken);
        checkBoxBeef = (CheckBox) findViewById(R.id.checkBoxBeef);
        checkBoxfish = (CheckBox) findViewById(R.id.checkBoxfish);
        */


        showRecipe = findViewById(R.id.showRecipe);
        listView = (ListView) findViewById(R.id.listView);
        listItems = new ArrayList<String>();

        listItems.add("First Item - added on Activity Create");
        listItems.add("Second Item - added on Activity Create");
        listItems.add("Third Item - added on Activity Create");

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);

        //listView.setAdapter(adapter);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(adapter);
            }
        };
        showRecipe.setOnClickListener(listener);
      
        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start LoginActivity
                mLoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = LoginActivity.newIntent(SearchActivity.this);

                        startActivityForResult(intent, REQUEST_CODE_LOGIN);
                    }
                });
            }
        });

        recipeSearchMock GrillCheese = new recipeSearchMock("Grill Cheese", ingredientsGrillCheese);
        recipeSearchMock HotDog = new recipeSearchMock("Hot Dog", ingredientsHotDog);
        recipeSearchMock Pasta = new recipeSearchMock("Pasta", ingredientsPasta);
        recipeSearchMock CocoPuffs = new recipeSearchMock("Coco Puffs", ingredientsCocoPuffs);

        allRecipies.add(GrillCheese);
        allRecipies.add(HotDog);
        allRecipies.add(Pasta);
        allRecipies.add(CocoPuffs);

        //for (int i = 0; i < allRecipies.size(); i++) {
        //    Log.i(i.toString());
        //}
    }
}


