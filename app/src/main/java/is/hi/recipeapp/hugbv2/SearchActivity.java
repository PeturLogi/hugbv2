package is.hi.recipeapp.hugbv2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
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
 * Klasi sem gerir okkur kleyft að leita af gögnum (uppskriftum) í gagnagrunni
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
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.search_bar)
    SearchView mSearchView;
    @BindView(R.id.next)
    Button mNext;
    @BindView(R.id.previous)
    Button mPrev;
    @BindView(R.id.select_ingred)
    Button selectIngredButton;
    @BindView(R.id.select_allergies)
    Button selectAllergiesButton;
    @BindView(R.id.select_diet)
    Button selectDietButton;
    @BindView(R.id.select_course)
    Button selectCourseButton;
    @BindView(R.id.select_max_time)
    Button selectMaxTimeButton;

    // Array listar
    protected ArrayList<Matches> recipeList = new ArrayList<>();

    // ArrayLists for ingredient checkboxes
    protected ArrayList<String> selectedIngredients = new ArrayList<>();
    // String array for ingredient checkboxes
    protected String[] selectableIngredients = {"shrimp", "chicken", "turkey", "beef", "lamb", "ham",
            "fish", "lobster", "pork", "pasta", "cheese", "eggs", "rice", "tomatoes", "onion",
            "black beans", "mayonnaise", "yogurt", "potatoes", "spinach", "mushrooms"};

    //ArrayLists for Allergy checkboxes
    protected ArrayList<String> selectedAllergies = new ArrayList<>();
    // String array for allergy checkboxes
    protected String[] selectableAllergies = {"Gluten-Free", "Peanut-Free","Dairy-Free",
            "Seafood-Free", "Sesame-Free", "Soy-Free", "Egg-Free", "Sulfite-Free",
            "Tree Nut-Free", "Wheat-Free"};

    //ArrayLists for Diet checkboxes
    protected ArrayList<String> selectedDiets = new ArrayList<>();
    // String array for Diet checkboxes
    protected String[] selectableDiets = {"Lacto vegetarian", "Ovo vegetarian", "Pescetarian",
            "Vegan", "Lacto-ovo vegetarian", "Paleo"};

    //ArrayLists for Course checkboxes
    protected ArrayList<String> selectedCourses = new ArrayList<>();
    // String array for Course checkboxes
    protected String[] selectableCourses = {"Main Dishes", "Desserts", "Side Dishes",
            "Appetizers", "Salads", "Breakfast and Brunch", "Breads", "Soups", "Beverages",
            "Condiments and Sauces", "Cocktails", "Snacks", "Lunch"};

    //ArrayLists for Max Time checkboxes
    protected int selectedMaxTime = -1;
    // String array for Max Time checkboxes
    //protected int[] selectableMaxTime = {15, 30, 45, 60, 75, 90, 105, 120};
    protected String[] selectableMaxTime = {"Unlimited", "15", "30", "45", "60", "75", "90", "105", "120"};

    protected int mSelected = -1;

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
        mSearchView.setIconified(false);

        //search barinn
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


        //hlustar á textann i searchbar
        mSearchView.setOnQueryTextListener(queryTextListener);

        //swipe stuff
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());


        //virkjar leitar takkann og birtir það sem er leitað af.
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

        //virkjar aboutactivity
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

        // Flettir leitarniðurstöðum um 10 niðurstöður
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.parseInt(resultStart);

                try {
                    temp += 10;
                    resultStart = "" + temp;
                    getRecipes();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        // Flettir leitarniðurstöðum til baka um 10 niðurstður
        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.parseInt(resultStart);
                if (temp >= 10) {
                    try {
                        temp -= 10;
                        resultStart = ""+temp;
                        getRecipes();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Virkjar Select Ingredient takkan og birtir dialog með checkboxum
        selectIngredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.select_ingred) {
                    showSelectIngredientDialog();
                }
            }
        });

        // Virkjar Select Allergies takkan og birtir dialog með checkboxum
        selectAllergiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.select_allergies) {
                    showSelectAllergiesDialog();
                }
            }
        });

        // Virkjar Select Diet takkan og birtir dialog með checkboxum
        selectDietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.select_diet) {
                    showSelectDietDialog();
                }
            }
        });

        selectCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.select_course) {
                    showSelectCourseDialog();
                }
            }
        });

        selectMaxTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.select_max_time) {
                    showSelectMaxTime();
                }
            }
        });
    }

    /**
     *Tenging við API og sækir það sem við leitum af.
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

        // Bætum völdum hraefnum við leitina
        for (String ingred : selectedIngredients) {
            searchUrl += "&allowedIngredient[]=" + ingred;
        }

        for (String allergy : selectedAllergies) {
            searchUrl += "&allowedAllergy[]=" + allergy;
        }

        for (String diet : selectedDiets) {
            searchUrl += "&allowedDiet[]=" + diet;
        }

        for (String course : selectedCourses) {
            searchUrl += "&allowedCourse[]=" + course;
        }

        if (selectedMaxTime != -1) {
            searchUrl += "&maxTotalTimeInSeconds=" + (60 * selectedMaxTime);
        }


        /**
         * Athugar vort hægt sé að tengjast gagnagrunni yummli
         * annars skilar villumelding
         */
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


                /**
                 * Geymir json gögn um uppskrift í mRecipe breytu ef tenging er í boði
                 * annars skilar villumeldingu
                 *
                 * @param call
                 * @param response
                 * @throws IOException
                 */
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


    /**
     * það sem lætur birtast á listviewið
     */
    private void updateDisplay() {
        Attribution attribution = mData.getAttribution();
        Matches[] recipes = mData.getMatches();

        adapter = new CustomListAdapter(this, recipeList);

        recipeList.clear();
        for (Matches item : recipes) {
            recipeList.add(item);
        }

        //Toast.makeText(getApplicationContext(), mQuery, Toast.LENGTH_SHORT).show();
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(adapter);

    }

    //segir til um hvort progressbar sé sýnilegur eða ekki
    private void toggleRefresh() {
       if(mProgressBar.getVisibility() == View.INVISIBLE) {
           mProgressBar.setVisibility(View.VISIBLE);
       } else {
           mProgressBar.setVisibility(View.INVISIBLE);
       }
    }

    /**
     * BReytir json gögnum frá API í recipe hlut
     *
     * @param jsonData
     * @return recip
     * @throws JSONException
     */
    private RecipeData parseRecipeDeitails(String jsonData) throws JSONException {
        RecipeData recipes = new RecipeData();

        recipes.setAttribution(getAttribution(jsonData));
        recipes.setMatches(getMatches(jsonData));

        return recipes;
    }

    /**
     * Tekur inn json data og breytir því attribution hlut
     *
     *
     * @param string
     * @return myAttribution
     * @throws JSONException
     */

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

    /**
     * Sækir JSON gögn sem samsvarar leitarskiyrðum og býr til array af hlutum sem
     * json gögnin skila
     *
     * @param string
     * @return recipes
     * @throws JSONException
     */

    private Matches[] getMatches(String string) throws JSONException {
        JSONObject recipeData = new JSONObject(string);

        JSONArray matches = recipeData.getJSONArray("matches");

        Matches[] recipes = new Matches[matches.length()];

        for (int i = 0; i < matches.length(); i++) {
            JSONObject recip = matches.getJSONObject(i);
            Matches recipe = new Matches();

            JSONObject attributes = recip.getJSONObject("attributes");

            String[] temp;

            //Frumstillir courses
            if(attributes.has("course")) {
                JSONArray course = attributes.getJSONArray("course");
                temp = new String[course.length()];
                for (int j = 0; j < course.length(); j++) {
                    temp[j] = course.getString(j);
                }
                recipe.setCourse(temp);
            }


            //frumstillir cuisines
            if (attributes.has("cuisine")) {
                JSONArray cuisine = attributes.getJSONArray("cuisine");
                temp = new String[cuisine.length()];
                for (int j = 0; j < cuisine.length(); j++) {
                    temp[j] = cuisine.getString(j);
                }
                recipe.setCuisine(temp);
            }


            //Frumstillir rating
            if (recip.has("rating")) {
                recipe.setRating(recip.getDouble("rating"));
            } else {
                recipe.setRating(0.0);
            }

            //frumstillir id
            recipe.setId(recip.getString("id"));


            //frumstillir smallImageUrls
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

    //gesture object klasi
    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        //SimpleOnGestureListener er "listener" fyrir það sem við viljum og hvernig við gerum það


        //MotionEvent fyrir hægri og vinstri "swipe" á skjánum.
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if(event2.getX() > event1.getX()) {
                //frá vinstri til hægri "swipe"
                Intent intent = new Intent(
                        SearchActivity.this, LoginActivity.class);
                finish(); //finish is used to stop history for SearchActivity class
                startActivity(intent);
            } else if (event2.getX() < event1.getX()) {
                //ffrá hægri til vinstri "swipe"
                Intent intent = new Intent(SearchActivity.this, MyShoppingList.class);
                startActivity(intent);
            }
            return true;
        }
    }

    /**
     * Athugar hvort tenging sé í boði í gagnagrunn
     * @return isAvailable
     */

    private boolean isNeworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    //birtir villuskilabð
    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    /**
     * Birtir Dialog með checkboxum fyrir hraefni
     */
    protected void showSelectIngredientDialog() {
        boolean[] checkedIngredients = new boolean[selectableIngredients.length];

        int count = selectableIngredients.length;

        for (int i = 0; i < count; i++) {
            checkedIngredients[i] = selectedIngredients.contains(selectableIngredients[i]);
        }

        DialogInterface.OnMultiChoiceClickListener ingredientDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedIngredients.add(selectableIngredients[which]);
                } else {
                    selectedIngredients.remove(selectableIngredients[which]);
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Ingredients");
        builder.setMultiChoiceItems(selectableIngredients, checkedIngredients, ingredientDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * Birtir Dialog með checkboxum fyrir ofnaemi
     */
    private void showSelectAllergiesDialog() {
        boolean[] checkedAllergies = new boolean[selectableAllergies.length];

        int count = selectableAllergies.length;

        for (int i = 0; i < count; i++) {
            boolean token = false;
            for (String s : selectedAllergies) {
                if (s.endsWith(selectableAllergies[i])) {
                    token = true;
                    break;
                }
            }

            checkedAllergies[i] = token;
        }

        DialogInterface.OnMultiChoiceClickListener allergyDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String append = "";
                switch (which) {
                    case 0:     append = "393^";
                                break;
                    case 1:     append = "394^";
                                break;
                    case 2:     append = "396^";
                                break;
                    case 3:     append = "398^";
                                break;
                    case 4:     append = "399^";
                                break;
                    case 5:     append = "400^";
                                break;
                    case 6:     append = "397^";
                                break;
                    case 7:     append = "401^";
                                break;
                    case 8:     append = "395^";
                                break;
                    case 9:     append = "392^";
                                break;
                    default:    break;
                }

                if (isChecked) {
                    selectedAllergies.add(append + selectableAllergies[which]);
                } else {
                    selectedAllergies.remove(append + selectableAllergies[which]);
                }

                System.out.println(append + selectableAllergies[which]);
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Allergies");
        builder.setMultiChoiceItems(selectableAllergies, checkedAllergies, allergyDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Birtir Dialog með checkboxum fyrir mataræði
     */
    private void showSelectDietDialog() {
        boolean[] checkedDiets = new boolean[selectableDiets.length];

        int count = selectableDiets.length;

        for (int i = 0; i < count; i++) {
            boolean token = false;
            for (String s : selectedDiets) {
                if (s.endsWith(selectableDiets[i])) {
                    token = true;
                    break;
                }
            }

            checkedDiets[i] = token;
        }

        DialogInterface.OnMultiChoiceClickListener dietDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String append = "";
                switch (which) {
                    case 0:     append = "388^";
                                break;
                    case 1:     append = "389^";
                                break;
                    case 2:     append = "390^";
                                break;
                    case 3:     append = "386^";
                                break;
                    case 4:     append = "387^";
                                break;
                    case 5:     append = "403^";
                                break;
                    default:    break;
                }


                if (isChecked) {
                    selectedDiets.add(append + selectableDiets[which]);
                } else {
                    selectedDiets.remove(append + selectableDiets[which]);
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Diet");
        builder.setMultiChoiceItems(selectableDiets, checkedDiets, dietDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSelectCourseDialog() {
        boolean[] checkedCourses = new boolean[selectableCourses.length];

        int count = selectableCourses.length;

        for (int i = 0; i < count; i++) {
            boolean token = false;
            for (String s : selectedCourses) {
                if (s.endsWith(selectableCourses[i])) {
                    token = true;
                    break;
                }
            }

            checkedCourses[i] = token;
        }

        DialogInterface.OnMultiChoiceClickListener courseDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String append = "course^course-";


                if (isChecked) {
                    selectedCourses.add(append + selectableCourses[which]);
                } else {
                    selectedCourses.remove(append + selectableCourses[which]);
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Courses");
        builder.setMultiChoiceItems(selectableCourses, checkedCourses, courseDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSelectMaxTime() {

        final AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Select Max Time in Minutes");
        build.setCancelable(true);
        final DialogInterface.OnMultiChoiceClickListener onClick =
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override public void onClick(final DialogInterface dialog,
                                                  final int which, final boolean isChecked) {

                        if (isChecked) {
                            if ((mSelected != -1) && (mSelected != which)) {
                                final int oldVal = mSelected;
                                final AlertDialog alert = (AlertDialog)dialog;
                                final ListView list = alert.getListView();
                                list.setItemChecked(oldVal, false);
                            }
                            mSelected = which;
                        } else {
                            mSelected = -1;
                        }
                        if (mSelected > 0){
                            selectedMaxTime = Integer.parseInt(selectableMaxTime[mSelected]);
                        } else {
                            selectedMaxTime = -1;
                        }
                        onChangeMaxTime();
                    }
                };

        build.setMultiChoiceItems(selectableMaxTime, null, onClick);
        AlertDialog dialog = build.create();
        dialog.show();
    }

    private void onChangeMaxTime() {
        StringBuilder stringBuilder = new StringBuilder();

        if (selectedMaxTime < 0) {
            stringBuilder.append("Unlimited");
        } else {
            stringBuilder.append(selectedMaxTime);
        }

        selectMaxTimeButton.setText(stringBuilder.toString());
    }
}


