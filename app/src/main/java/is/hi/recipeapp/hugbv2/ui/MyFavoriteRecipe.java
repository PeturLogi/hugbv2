package is.hi.recipeapp.hugbv2.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import is.hi.recipeapp.hugbv2.AboutActivity;
import is.hi.recipeapp.hugbv2.R;
import is.hi.recipeapp.hugbv2.SearchActivity;
import is.hi.recipeapp.hugbv2.model.Attribution;
import is.hi.recipeapp.hugbv2.model.CustomListAdapter;
import is.hi.recipeapp.hugbv2.model.Matches;
import is.hi.recipeapp.hugbv2.model.Recipe;
import is.hi.recipeapp.hugbv2.model.RecipeData;
import is.hi.recipeapp.hugbv2.model.recipeSearchMock;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyFavoriteRecipe extends AppCompatActivity {

    public static final String TAG = MyFavoriteRecipe.class.getSimpleName();
    private String[] recipId;
    private RecipeData mRecipe;
    private Matches[] mMatches;
    private CustomListAdapter adapter;

    @BindView(R.id.listViewFav)
    ListView mListViewFav;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    // Arraylist
    ArrayList<Matches> favRecipList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_recipe);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mProgressBar.setVisibility(View.INVISIBLE);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        recipId = new String[] {preferences.getString("Id", "")};
        mMatches = new Matches[recipId.length];

        addRecipToFav();


        //Toast.makeText(getApplicationContext(), recipId, Toast.LENGTH_LONG).show();

    }

    public void addRecipToFav() {
        String apiKey = "ca9cb76e0f393e4e209217c8d388780f";
        String apiId = "c94137d2";
        String getUrl = "http://api.yummly.com/v1/api/recipe/" + recipId[0] + "?_app_id=" + apiId + "&_app_key="
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
                        Log.e(TAG, "JSON caught ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    private void setDisplay() {
        Attribution attribution = mRecipe.getAttribution();

        adapter = new CustomListAdapter(this, favRecipList, R.drawable.cashking);

        for (Matches item : mMatches) {
            if (item.getRecipeName() != null) {
                favRecipList.add(item);
            }

        }

        mListViewFav = (ListView) findViewById(R.id.listViewFav);
        mListViewFav.setAdapter(adapter);
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private RecipeData parseRecipe(String jsonData) throws JSONException {
        RecipeData recipe = new RecipeData();

        recipe.setAttribution(getAttribution(jsonData));
        getMatches(jsonData);

        return recipe;
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

    private void getMatches(String string) throws JSONException {
        JSONObject recipeData = new JSONObject(string);


        for (int i = 0; i < mMatches.length; i++) {
            Matches recipe = new Matches();

            String[] temp;

            // Initialize images
            if (recipeData.has("images")) {
                JSONArray imageUrsl = recipeData.getJSONArray("images");

                JSONObject smallImage = imageUrsl.getJSONObject(1);

                temp = new String[1];
                if (smallImage.has("hostedSmallUrl")) {
                    temp[0] = smallImage.getString("hostedSmallUrl");
                } else {
                    temp[0] = "null";
                }
                recipe.setSmallImageUrls(temp);
            }

            // Initialize recipe name
            if (recipeData.has("recipName")) {
                recipe.setRecipeName(recipeData.getString("recipeName"));
            }

            mMatches[i] = recipe;
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
