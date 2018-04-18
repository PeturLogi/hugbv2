package is.hi.recipeapp.hugbv2.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import is.hi.recipeapp.hugbv2.AboutActivity;
import is.hi.recipeapp.hugbv2.R;
import is.hi.recipeapp.hugbv2.model.Attribution;
import is.hi.recipeapp.hugbv2.model.CustomListAdapter;
import is.hi.recipeapp.hugbv2.model.Matches;
import is.hi.recipeapp.hugbv2.model.RecipeData;
import is.hi.recipeapp.hugbv2.repository.SousChefRepository;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyFavoriteRecipe extends AppCompatActivity {

    public static final String TAG = MyFavoriteRecipe.class.getSimpleName();
    private List<String> recipId;
    private RecipeData mRecipe;
    private Matches[] mMatches;
    private CustomListAdapter adapter;

    private int mNrHolder = -1;

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

        recipId = SousChefRepository.get(MyFavoriteRecipe.this).getRecipes();
        mMatches = new Matches[recipId.size()];

        for (int i = 0; i < recipId.size(); i++) {
            displayFavorites(i);
        }

        setDisplay();

        mListViewFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = favRecipList.get(i).getId();

                alertWindow(selectedItem, i);
            }
        });
    }

    private void alertWindow(final String selectedItem, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Inspect or Remove " + favRecipList.get(position).getRecipeName() + "?");
        builder.setPositiveButton("Inspect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MyFavoriteRecipe.this, AboutActivity.class);
                intent.putExtra("recipId", selectedItem);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                favRecipList.remove(position);
                SousChefRepository.get(MyFavoriteRecipe.this).removeRecipe(selectedItem);
                mListViewFav.setAdapter(adapter);
                setDisplay();
            }
        });
        builder.show();
    }

    public void displayFavorites(int nr) {
        String apiKey = "ca9cb76e0f393e4e209217c8d388780f";
        String apiId = "c94137d2";
        String getUrl = "http://api.yummly.com/v1/api/recipe/" + recipId.get(nr) + "?_app_id=" +
                apiId + "&_app_key=" + apiKey;

        mNrHolder = nr;

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
                            Matches temp = parseRecipe(jsonData);
                            temp.setId(recipId.get(mNrHolder));
                            favRecipList.add(temp);

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
        //Attribution attribution = mRecipe.getAttribution();

        adapter = new CustomListAdapter(this, favRecipList);

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

    private Matches parseRecipe(String jsonData) throws JSONException {
        Matches match = getMatches(jsonData);

        return match;
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

    private Matches getMatches(String string) throws JSONException {
        JSONObject recipeData = new JSONObject(string);

        Matches match = new Matches();

        //Initialize images
        if (recipeData.has("images")) {
            JSONArray imageURLS = recipeData.getJSONArray("images");
            JSONObject smallImage = imageURLS.getJSONObject(imageURLS.length() - 1);

            String[] temp = new String[1];

            temp[0] = smallImage.getString("hostedSmallUrl");

            match.setSmallImageUrls(temp);
        }

        // Initialize recipe name
        if (recipeData.has("name")) {
            match.setRecipeName(recipeData.getString("name"));
        }

        // Initialize recipe ingredients
        if (recipeData.has("ingredientLines")) {
            JSONArray ingred = recipeData.getJSONArray("ingredientLines");
            String[] temp = new String[ingred.length()];

            for (int i = 0; i < ingred.length(); i++) {
                temp[i] = ingred.getString(i);
            }
            match.setIngredients(temp);
        }

        return match;
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
