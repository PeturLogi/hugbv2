package is.hi.recipeapp.hugbv2;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
    private GestureDetectorCompat gestureObject;


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


        gestureObject = new GestureDetectorCompat(this, new LearnGesture());
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

/*
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

        recipeSearchMock GrillCheese = new recipeSearchMock("Grill Cheese", ingredientsGrillCheese);
        recipeSearchMock HotDog = new recipeSearchMock("Hot Dog", ingredientsHotDog);
        recipeSearchMock Pasta = new recipeSearchMock("Pasta", ingredientsPasta);
        recipeSearchMock CocoPuffs = new recipeSearchMock("Coco Puffs", ingredientsCocoPuffs);

        allRecipies.add(GrillCheese);
        allRecipies.add(HotDog);
        allRecipies.add(Pasta);
        allRecipies.add(CocoPuffs);
*/

    }
}


