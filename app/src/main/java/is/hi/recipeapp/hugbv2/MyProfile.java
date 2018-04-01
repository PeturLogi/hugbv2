package is.hi.recipeapp.hugbv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import is.hi.recipeapp.hugbv2.ui.MyFavoriteRecipe;
import is.hi.recipeapp.hugbv2.ui.MyShoppingList;
import is.hi.recipeapp.hugbv2.ui.MyWeekMenu;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile2);

        CardView cardView = (CardView) findViewById(R.id.shoppinglist);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyShoppingList.class);
                startActivity(intent);
            }
        });

        CardView cardView2 = (CardView) findViewById(R.id.myfavoriterecipe);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyFavoriteRecipe.class);
                startActivity(intent);
            }
        });

        CardView cardView3 = (CardView) findViewById(R.id.weekmenu);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyWeekMenu.class);
                startActivity(intent);
            }
        });

    }
}
