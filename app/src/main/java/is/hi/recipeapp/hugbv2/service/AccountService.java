package is.hi.recipeapp.hugbv2.service;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Credentials;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import is.hi.recipeapp.hugbv2.SignupActivity;
import is.hi.recipeapp.hugbv2.model.Account;
import is.hi.recipeapp.hugbv2.repository.AccountRepo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;

/**
 * Service klasi fyrir Account
 * Sér um þjónustu fyrir Account með því að tengjast við heroku web service.
 * Þar getur klasinn fundið, breytt, eytt, búið til og borið saman Account hluti.
 *
 * Created by Pétur Logi Pétursson on 4/11/2018.
 */

public class AccountService  implements Serializable {
    // String breyta til þess að auðkenna klasann í logger
    public static final String TAG = AccountService.class.getSimpleName();

    // AccountRepo hlutur sem geymir notendur
    AccountRepo mAccountRepo = new AccountRepo();
    // Sá notandi sem er skráður inn á hverjum tíma
    Account mCurrentAcc;
    // Strengur sem geymir url fyrir heroku þjón
    private String URL = "https://enigmatic-beach-42063.herokuapp.com/souschef/";
    // Tenging yfir í LoginActivity
    private Activity loginActivity = null;
    // Tenging yfir í SignupActivity
    private Activity signupActivity = null;

    // okhttp3 þjónn sem hefur samskipti við bakendann
    OkHttpClient client;
    // Gson hlutur sem vinnur med JSON gögn
    Gson gson = new Gson();

    public AccountService() {
        client = new OkHttpClient();
    }


    public ArrayList<Account> getAccounts() {
        String append = "accounts/";

        if (isNeworkAvailable(loginActivity)) {
            Request request = new Request.Builder().url(URL+append).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    loginActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    Log.e(TAG, "Exception caught: ", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    loginActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            parseAccounts(jsonData);
                        } else {
                            throw new IOException("No response");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON caught: ", e);
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Log.e(TAG, "Network is unavailable");
        }

        return mAccountRepo.getAccounts();
    }

    /**
     * Aðferð sem skráir inn notanda
     * @param account
     */
    public void login (Account account) {
        mCurrentAcc = account;
    }

    public void logout() {
        mCurrentAcc = null;
    }

    public void addAccount(final Account account) {
        String append = "/account/create";


        if (isNeworkAvailable(getSignupActivity())) {
            String temp = "{\"email_string\":\"" + account.getEmail() + "\", \"name_string\":\"" +
                    account.getName() + "\" , \"address_string\":\"" + account.getAdress() + "\" , \"mobileNr_string\":\"" +
                    account.getMobileNr() + "\" , \"password_string\":\"" + account.getPassword() + "\"}";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON,temp);
            Request request = new Request.Builder().url(URL + append).post(body).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    signupActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    Log.e(TAG, "Exception caught: ", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    signupActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    try {
                        final String jsonData = response.body().string();
                        Log.v(TAG, "Her koma gognin" + jsonData);
                        if (response.isSuccessful()) {
                            signupActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (jsonData.toString().equals("{\"message\":\"Account Added\"}")) {
                                        mCurrentAcc = account;
                                        mAccountRepo.addAccount(account);
                                        Toast.makeText(signupActivity.getBaseContext(),
                                                "Signup successful!", Toast.LENGTH_LONG).show();
                                        ((SignupActivity) signupActivity).signupSuccess();
                                    } else {
                                        Toast.makeText(signupActivity.getBaseContext(),
                                                "Það hefur komið upp villa við nýskráningu, reyndu aftur."
                                                ,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            //Toast.makeText(signupActivity,
                            //        "Nettenging finnst ekki, reyndu aftur síðar."
                            //        ,Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Nettenging finnst ekki, reyndu aftur síðar:");
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
    }


    /**
     * Breytir json gögnum frá bakenda í lista af Account hlutum
     * @param jsonData
     * @throws JSONException
     */
    public void parseAccounts(String jsonData) throws JSONException{
        JSONArray accountData = new JSONArray(jsonData);



        for (int i = 0; i < accountData.length(); i++) {
            Account temp = new Account();
            JSONObject acc = accountData.getJSONObject(i);

            temp.setId(acc.getString("_id"));
            temp.setEmail(acc.getString("email_string"));
            temp.setAdress(acc.getString("address_string"));
            temp.setMobileNr(acc.getString("mobileNr_string"));
            temp.setName(acc.getString("name_string"));
            temp.setPassword(acc.getString("password_string"));

            Log.e(TAG, temp.getEmail());

            mAccountRepo.addAccount(temp);
        }
    }

    private boolean isNeworkAvailable(Activity act) {
        ConnectivityManager manager = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    public Activity getLoginActivity() {
        return loginActivity;
    }

    public void setLoginActivity(Activity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public Activity getSignupActivity() {
        return this.signupActivity;
    }

    public void setSignupActivity(Activity signupActivity) {
        this.signupActivity = signupActivity;
    }



}
