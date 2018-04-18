package is.hi.recipeapp.hugbv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import is.hi.recipeapp.hugbv2.ui.MyFavoriteRecipe;
import is.hi.recipeapp.hugbv2.ui.MyShoppingList;
import is.hi.recipeapp.hugbv2.ui.MyWeekMenu;

/**
 *
 * @date apríl 2018
 *
 * Klasi sem heldur utan umprófíl notanda,
 * virkjar önnur activity
 *
 */

public class MyProfile extends AppCompatActivity {

    @BindView(R.id.currentUser)
    TextView mAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile2);
        ButterKnife.bind(this);

        mAccountName.setText(LoginActivity.getAccountService().getCurrentAccount().getName());

        //virkjar klasann MyShoppingList
        CardView cardView = (CardView) findViewById(R.id.shoppinglist);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyShoppingList.class);
                startActivity(intent);
            }
        });

        //virkjar klasann MyFavouriteRecipe
        CardView cardView2 = (CardView) findViewById(R.id.myfavoriterecipe);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyFavoriteRecipe.class);
                startActivity(intent);
            }
        });

        //virkjar myWeekMenu klasann
        CardView cardView3 = (CardView) findViewById(R.id.weekmenu);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MyWeekMenu.class);
                startActivity(intent);
            }
        });

        CardView cardView4 = (CardView) findViewById(R.id.log_out);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.getAccountService().logout();
                Intent intent = new Intent(MyProfile.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
