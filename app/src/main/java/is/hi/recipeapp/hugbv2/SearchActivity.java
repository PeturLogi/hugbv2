package is.hi.recipeapp.hugbv2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import is.hi.recipeapp.hugbv2.model.Attribution;
import is.hi.recipeapp.hugbv2.model.CustomListAdapter;
import is.hi.recipeapp.hugbv2.model.Matches;
import is.hi.recipeapp.hugbv2.model.RecipeData;
import is.hi.recipeapp.hugbv2.model.recipeSearchMock;
import is.hi.recipeapp.hugbv2.ui.AlertDialogFragment;
import is.hi.recipeapp.hugbv2.ui.MyShoppingList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @date April 2018
 * HBV601G Hugbúnaðarverkefni 2
 * Háskóli Íslands
 *
 * gerir okkur kleyft að leita af gögnum (uppskriftum) í gagnagrunni
 *
 */


public class SearchActivity extends AppCompatActivity {

    public static final String TAG = SearchActivity.class.getSimpleName();
    private RecipeData mData;
    private String mQuery = "defaultString";
    private CustomListAdapter adapter;
    private String resultStart = "0";
    //ArrayAdapter<String> adapter;
    EditText editText;
    // Login breytur
    private GestureDetectorCompat gestureObject;

    //tenging við layoutið activity_search.xml

    @BindView(R.id.showRecipe)
    Button mShowRecipe;
    @BindView(R.id.checkBoxCheese)
    CheckBox mCheckBoxCheese;
    @BindView(R.id.checkBoxHam)
    CheckBox mCheckBoxHam;
    @BindView(R.id.checkBoxPasta)
    CheckBox mCheckBoxPasta;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.search_bar)
    SearchView mSearchView;

    // Array listar
    ArrayList<recipeSearchMock> allRecipies = new ArrayList<>();
    ArrayList<Matches> recipeList = new ArrayList<>();


    /**
     * smiðurinn birtir viðmótið activity_search.xml og virkjar upphafs aðferðir
     *
     * @param savedInstanceState
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mProgressBar.setVisibility(View.INVISIBLE);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mQuery = s;
                return true;
            }
        };

        mSearchView.setOnQueryTextListener(queryTextListener);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        mShowRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getRecipes();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedItem = recipeList.get(position).getId();
                Intent intent = new Intent(SearchActivity.this, AboutActivity.class);
                intent.putExtra("recipId", selectedItem);
                startActivity(intent);
            }
        });
    }


    /**
     *
     * @throws UnsupportedEncodingException
     */

    private void getRecipes() throws UnsupportedEncodingException {
        String apiKey = "ca9cb76e0f393e4e209217c8d388780f";
        String apiId = "c94137d2";
        String searchUrl = "http://api.yummly.com/v1/api/recipes?_app_id=" + apiId + "&_app_key="
                + apiKey;

        if (!mQuery.equalsIgnoreCase("defaultString")) {
            String urlQuery = URLEncoder.encode(mQuery, "UTF-8");
            searchUrl += ("&q=" + urlQuery);
        }

        searchUrl += ("&requirePictures=true&maxResult=10&start=" + resultStart);

        ArrayList<String> chosenIngred = new ArrayList<>();

        if (mCheckBoxPasta.isChecked()) {
            chosenIngred.add("pasta");
        }
        if (mCheckBoxCheese.isChecked()) {
            chosenIngred.add("cheese");
        }
        if (mCheckBoxHam.isChecked()) {
            chosenIngred.add("ham");
        }

        for (String item : chosenIngred) {
            searchUrl += "&allowedIngredient[]=" + item;
        }

        if(isNeworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(searchUrl).build();

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
                            mData = parseRecipeDeitails(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
        /*
        https://www.yummly.com/recipes?noUserSettings=true&showRegistrationModal=true&startEmailLogin=true&q=chicken%20grilled%20chicken%20with%20chicken%20breasts%20with%20vegetables&start=0&allowedIngredient=chicken%20breasts&allowedIngredient=vegetables&allowedTechnique=technique^technique-grilling&allowedAttribute=difficulty^difficulty-quick-and-easy

        &allowedIngredient[]=garlic&allowedIngredient[]=cognac
         */
    }

    private void updateDisplay() {
        Attribution attribution = mData.getAttribution();
        Matches[] recipes = mData.getMatches();

        adapter = new CustomListAdapter(this, recipeList, R.drawable.cashking);

        recipeList.clear();
        for (Matches item : mData.getMatches()) {
            recipeList.add(item);
        }

        //Toast.makeText(getApplicationContext(), mQuery, Toast.LENGTH_SHORT).show();
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(adapter);

    }

    private void toggleRefresh() {
       if(mProgressBar.getVisibility() == View.INVISIBLE) {
           mProgressBar.setVisibility(View.VISIBLE);
       } else {
           mProgressBar.setVisibility(View.INVISIBLE);
       }
    }

    private RecipeData parseRecipeDeitails(String jsonData) throws JSONException {
        RecipeData recipes = new RecipeData();

        recipes.setAttribution(getAttribution(jsonData));
        recipes.setMatches(getMatches(jsonData));

        return recipes;
    }

    private Attribution getAttribution(String string) throws JSONException {
        JSONObject recipeData = new JSONObject(string);

        JSONObject attribution = recipeData.getJSONObject("attribution");

        Attribution myAttribution = new Attribution();
        myAttribution.setHtml(attribution.getString("html"));
        myAttribution.setLogo(attribution.getString("logo"));
        myAttribution.setText(attribution.getString("text"));
        myAttribution.setUrl(attribution.getString("url"));

        return myAttribution;
    }

    private Matches[] getMatches(String string) throws JSONException {
        JSONObject recipeData = new JSONObject(string);

        JSONArray matches = recipeData.getJSONArray("matches");

        Matches[] recipes = new Matches[matches.length()];

        for (int i = 0; i < matches.length(); i++) {
            JSONObject recip = matches.getJSONObject(i);
            Matches recipe = new Matches();

            JSONObject attributes = recip.getJSONObject("attributes");

            String[] temp;
            //Initialize courses
            if(attributes.has("course")) {
                JSONArray course = attributes.getJSONArray("course");
                temp = new String[course.length()];
                for (int j = 0; j < course.length(); j++) {
                    temp[j] = course.getString(j);
                }
                recipe.setCourse(temp);
            }


            //Initialize cuisines
            if (attributes.has("cuisine")) {
                JSONArray cuisine = attributes.getJSONArray("cuisine");
                temp = new String[cuisine.length()];
                for (int j = 0; j < cuisine.length(); j++) {
                    temp[j] = cuisine.getString(j);
                }
                recipe.setCuisine(temp);
            }


            //Initialize rating
            if (recip.has("rating")) {
                recipe.setRating(recip.getDouble("rating"));
            } else {
                recipe.setRating(0.0);
            }

            //Initialize Id
            recipe.setId(recip.getString("id"));


            //Initialize images
            if (recip.has("smallImageUrls")) {
                JSONArray imageUrls = recip.getJSONArray("smallImageUrls");
                temp = new String[imageUrls.length()];
                for(int j = 0; j < imageUrls.length(); j++) {
                    temp[j] = imageUrls.getString(j);
                }
                recipe.setSmallImageUrls(temp);

            }

            //Initialize source name
            if (recip.has("sourceDisplayName")) {
                recipe.setSourceDisplayName(recip.getString("sourceDisplayName"));
            }


            //Initialize total time
            if (recip.has("totalTimeInSeconds")) {
                String time = recip.getString("totalTimeInSeconds");

                if (time.equals("null")) {
                    recipe.setTotalTimeSeconds(0);
                } else {
                    recipe.setTotalTimeSeconds(Integer.parseInt(time));
                }
            } else {
                recipe.setTotalTimeSeconds(0);
            }


            //Initialize ingredients
            if (recip.has("ingredients")) {
                JSONArray ingredients = recip.getJSONArray("ingredients");
                temp = new String[ingredients.length()];
                for (int j = 0; j < ingredients.length(); j++) {
                    temp[j] = ingredients.getString(j);
                }
                recipe.setIngredients(temp);
            }

            //Initialize recipe name
            if (recip.has("recipeName")) {
                recipe.setRecipeName(recip.getString("recipeName"));
            }

            recipes[i] = recipe;
        }

        return recipes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.MyShoppingList) {
            Intent intent = new Intent(SearchActivity.this, MyProfile.class);
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
                Intent intent = new Intent(SearchActivity.this, MyShoppingList.class);
                startActivity(intent);
            }
            return true;
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


