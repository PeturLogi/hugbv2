package is.hi.recipeapp.hugbv2;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

import android.widget.Toast;


import static android.R.layout.simple_list_item_2;

public class SearchActivity extends AppCompatActivity {

    private CheckBox checkBoxCheese, checkBoxHam, checkBoxPasta;
    private Button showRecipe;
    private ListView listView;
    ArrayList<String> t = new ArrayList<>();
    CustomListAdapter adapter;
    //ArrayAdapter<String> adapter;
    EditText editText;

    // Login variables
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

        for (recipeSearchMock item : allRecipies) {
            t.add(item.getName());
        }

        adapter = new CustomListAdapter(this, t, R.drawable.cashking);
        showRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedItem = t.get(position);
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.MyShoppingList) {
            Intent intent = new Intent(SearchActivity.this, MyShoppingList.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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


