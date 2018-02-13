package is.hi.recipeapp.hugbv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SearchActivity extends AppCompatActivity {

    // Login variables
    private Button mLoginButton;
    private static final int REQUEST_CODE_LOGIN = 0;

    // Mock ingredients variables
    private String[] ingredientsGrillCheese = {"Cheese", "Ham"};
    private String[] ingredientsHotDog = {"Ketchup", "Fried Onion", "Mustard"};
    private String[] ingredientsPasta = {"Pasta", "Pepperoni", "Ham", "Carrots", "Bacon"};
    private String[] ingredientsCocoPuffs = {"Coco Puffs", "Milk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent = LoginActivity.newIntent(SearchActivity.this);
                startActivity(intent);
            }
        };

        recipeSearchMock GrillCheese = new recipeSearchMock("Grill Cheese", ingredientsGrillCheese);
        recipeSearchMock HotDog = new recipeSearchMock("Hot Dog", ingredientsHotDog);
        recipeSearchMock Pasta = new recipeSearchMock("Pasta", ingredientsPasta);
        recipeSearchMock CocoPuffs = new recipeSearchMock("Coco Puffs", ingredientsCocoPuffs);
    }
}
