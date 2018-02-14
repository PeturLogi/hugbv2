package is.hi.recipeapp.hugbv2;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static android.R.layout.simple_list_item_2;

public class SearchActivity extends AppCompatActivity {

    private CheckBox checkBoxCheese, checkBoxHam, checkBoxPasta;
    private Button showRecipe;
    private ListView listView;
    ArrayAdapter<String> adapter;
    EditText editText;

    // Login variables
    private Button mLoginButton;
    private static final int REQUEST_CODE_LOGIN = 0;
    private GestureDetectorCompat gestureObject;


    // Mock ingredients variables
    private String[] ingredientsGrillCheese = {"Cheese", "Ham", "Bread"};
    private String[] ingredientsHotDog = {"Ketchup", "Fried Onion", "Mustard", "Hot Dog", "Bread"};
    private String[] ingredientsPasta = {"Pasta", "Pepperoni", "Ham", "Carrots", "Bacon"};
    private String[] ingredientsCocoPuffs = {"Coco Puffs", "Milk"};


    // ArrayLists
    ArrayList<recipeSearchMock> allRecipies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
      

        checkBoxCheese = (CheckBox) findViewById(R.id.checkBoxCheese);
        checkBoxHam = (CheckBox) findViewById(R.id.checkBoxHam);
        checkBoxPasta = (CheckBox) findViewById(R.id.checkBoxPasta);


        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        showRecipe = findViewById(R.id.showRecipe);
        listView = (ListView) findViewById(R.id.listView);

        recipeSearchMock GrillCheese = new recipeSearchMock("Grill Cheese", ingredientsGrillCheese);
        recipeSearchMock HotDog = new recipeSearchMock("Hot Dog", ingredientsHotDog);
        recipeSearchMock Pasta = new recipeSearchMock("Pasta", ingredientsPasta);
        recipeSearchMock CocoPuffs = new recipeSearchMock("Coco Puffs", ingredientsCocoPuffs);

        allRecipies.add(GrillCheese);
        allRecipies.add(HotDog);
        allRecipies.add(Pasta);
        allRecipies.add(CocoPuffs);


        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> tempIngred = new ArrayList<>();

        for ( recipeSearchMock recipe : allRecipies ) {
            Collections.addAll(tempIngred, recipe.getIngredients());
            if(checkBoxPasta.isSelected()) {
                
            }
        }

        /*
        for ( recipeSearchMock recipe : allRecipies ) {
            temp.add(recipe.getName());
        }
         */

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, temp);


        // Listener to view all recipies
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(adapter);
            }
        };
        showRecipe.setOnClickListener(listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //gesture object class
    class LearnGesture extends GestureDetector.SimpleOnGestureListener {
        //SimpleOnGestureListener is the listener for what we want to do and how we do it

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if(event2.getX() > event1.getX()) {
                //for left to right swipe
                Intent intent = new Intent(
                        SearchActivity.this, LoginActivity.class);
                finish(); //finish is used to stop history for SearchActivity class
                startActivity(intent);
            } else if (event2.getX() < event1.getX()) {
                //for right to left swipe
            }
            return true;
        }
    }
}


