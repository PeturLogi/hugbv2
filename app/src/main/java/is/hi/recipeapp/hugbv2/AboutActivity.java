package is.hi.recipeapp.hugbv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import is.hi.recipeapp.hugbv2.model.Recipe;
import is.hi.recipeapp.hugbv2.repository.SousChefRepository;
import is.hi.recipeapp.hugbv2.ui.AlertDialogFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AboutActivity extends AppCompatActivity {
    private String recipId;
    private Recipe mRecipe;
    public static final String TAG = AboutActivity.class.getSimpleName();

    @BindView(R.id.myProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.ingredLines)
    TextView mIngredLines;
    @BindView(R.id.recipeImage)
    ImageView mRecipeImage;
    @BindView(R.id.addFavorite)
    Button mAddFavorite;
    @BindView(R.id.recipeNameHeader)
    TextView mHeader;
    @BindView(R.id.nrServings)
    TextView mServings;
    @BindView(R.id.totalTime)
    TextView mTimeSec;
    @BindView(R.id.cusine)
    TextView mCusine;
    @BindView(R.id.addIngredients)
    Button mAddIngred;
    @BindView(R.id.rating)
    TextView mRating;
    @BindView(R.id.source_url)
    TextView mSource;


    private ArrayList<String> shoppingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        
        Intent intent = getIntent();
        recipId = intent.getStringExtra("recipId");

        getRecipe();

        mAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SousChefRepository.get(AboutActivity.this).addRecipe("test@testmail.com", recipId);
                Toast.makeText(getApplicationContext(), "Added to Favorites!", Toast.LENGTH_SHORT).show();
            }
        });

        mAddIngred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingList = getShoppingList(getApplicationContext());

                for (String ingred : mRecipe.getIngredientLines()) {
                    shoppingList.add(ingred);
                    Collections.sort(shoppingList);
                    storeShoppingList(shoppingList, getApplicationContext());
                }
                Toast.makeText(getApplicationContext(),"Ingredients added to shopping list", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void storeShoppingList(ArrayList<String> shoppingList, Context applicationContext) {
        Set<String> toWrite = new HashSet<String>(shoppingList);
        SharedPreferences WordSearchPutPrefs = applicationContext.getSharedPreferences("dbArrayValues",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = WordSearchPutPrefs.edit();
        prefEditor.putStringSet("myArray", toWrite);
        prefEditor.commit();
    }

    private ArrayList<String> getShoppingList(Context applicationContext) {
        SharedPreferences WordSearchGetPrefs = applicationContext.getSharedPreferences("dbArrayValues",
                Activity.MODE_PRIVATE);
        Set<String> tempSet = new HashSet<String>();
        tempSet = WordSearchGetPrefs.getStringSet("myArray", tempSet);
        return new ArrayList<String>(tempSet);
    }

    private void getRecipe() {
        String apiKey = "ca9cb76e0f393e4e209217c8d388780f";
        String apiId = "c94137d2";
        String getUrl = "http://api.yummly.com/v1/api/recipe/" + recipId + "?_app_id=" + apiId + "&_app_key="
                + apiKey;

        if (isNeworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(getUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mRecipe = parseRecipe(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON caught", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    private void setDisplay() {
        Picasso.get().load(mRecipe.getImage()).into(mRecipeImage);
        String[] ingredients = mRecipe.getIngredientLines();
        String[] cusines = mRecipe.getCuisine();
        mServings.setText("Nr of servings: " + mRecipe.getNumberOfServings());
        mHeader.setText("\n" + mRecipe.getName() + "\n\n\n");
        String text = "";
        for (String line : ingredients) {
            text += line + "\n\n";
        }
        mIngredLines.setText(text);
        text = "";
        if (cusines != null) {
            for (String line : cusines) {
                text += line + "\n\n";
            }
        }

        mCusine.setText(text);
        mTimeSec.setText("Prep and Cooking time: " + mRecipe.getTotalTime());
        mRating.setText("Yummli rating: " + mRecipe.getRating() + " out of 5\n\n\n");
        mSource.setText("Click link for Instructions: " + mRecipe.getSourceUrl());


    }

    private Recipe parseRecipe(String jsonData) throws JSONException {
        Recipe recip = new Recipe();
        JSONObject recipeData = new JSONObject(jsonData);
        String[] temp;

        if (recipeData.has("ingredientLines")) {
            JSONArray ingredientLines = recipeData.getJSONArray("ingredientLines");
            temp = new String[ingredientLines.length()];
            for (int i = 0; i < ingredientLines.length(); i++) {
                temp[i] = ingredientLines.getString(i);
            }
            recip.setIngredientLines(temp);
        }

        if (recipeData.has("images")) {
            JSONArray images = recipeData.getJSONArray("images");
            JSONObject largeUrl = images.getJSONObject(0);

            String image = largeUrl.getString("hostedLargeUrl");

            recip.setImage(image);
        }

        if (recipeData.has("source")) {
            JSONObject source = recipeData.getJSONObject("source");
            recip.setSourceUrl(source.getString("sourceRecipeUrl"));
        }

        if (recipeData.has("name")) {
            recip.setName(recipeData.getString("name"));
        }

        if (recipeData.has("yield")) {
            recip.setYield(recipeData.getString("yield"));
        }

        if (recipeData.has("totalTime")) {
            recip.setTotalTime(recipeData.getString("totalTime"));
        }

        if (recipeData.has("attributes")) {
            JSONObject attributes = recipeData.getJSONObject("attributes");
            if (attributes.has("cuisine")) {
                JSONArray cuisine = attributes.getJSONArray("cuisine");
                String[] cuis = new String[cuisine.length()];

                for (int i = 0; i < cuisine.length(); i++) {
                    cuis[i] = cuisine.getString(i);
                }

                recip.setCuisine(cuis);
            }
        }

        if (recipeData.has("rating")) {
            String rate = recipeData.getString("rating");

            if (rate.equalsIgnoreCase("null")) {
                recip.setRating(0.0);
            } else {
                recip.setRating(Double.parseDouble(rate));
            }
        }

        if (recipeData.has("numberOfServings")) {
            recip.setNumberOfServings(recipeData.getString("numberOfServings"));
        }

        return recip;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.MyShoppingList) {
            Intent intent = new Intent(AboutActivity.this, MyProfile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isNeworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}
