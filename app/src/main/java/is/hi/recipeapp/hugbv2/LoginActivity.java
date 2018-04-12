package is.hi.recipeapp.hugbv2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;
import is.hi.recipeapp.hugbv2.model.Account;
import is.hi.recipeapp.hugbv2.service.AccountService;

/**
 *
 * @date april 2018
 *
 * Klasi sem er upphafs activity smáforritsins
 * Athugar hvort upplýsingar séu réttar við innskráningu,
 * og virkjar önnur activity.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static AccountService sAccountService = new AccountService();
    ArrayList<Account> mAccounts;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    // Geymir email þess sem ætlar að skrá sig inn
    private String iEmail;
    // Geymir lykilorð þess sem ætlar að skrá sig inn
    private String iPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sAccountService.setLoginActivity(LoginActivity.this);
        ButterKnife.bind(this);

        //login takki, virkjar search activity
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iEmail = _emailText.getText().toString();
                iPassword = _passwordText.getText().toString();

                mAccounts = sAccountService.getAccounts();

               login();
            }
        });

        //signup takki, virkjar signup activity
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //virkjar signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        //auðkennis ferlið
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // Checks if email and password exist in database/mockdatabase
                        boolean token = false;
                        Account temp = null;
                        for (Account account : mAccounts) {
                            if (account.getEmail().equals(iEmail) && account.getPassword().equals(iPassword)) {
                                token = true;
                                temp = account;
                            }
                        }

                        if (token) {
                            onLoginSuccess(temp);
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                //TODO: bæta við "alvöru" innskráningar "Logic" hérna
                //Activity-ið klárast og loggar þeim sjálfkrafa
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //tökum burt virknina að fara til baka í MainActivity
        moveTaskToBack(true);
    }

    //ef innskráning tekst fær notandi skilaboð um að það hafi tekist.
    public void onLoginSuccess(Account account) {
        _loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_LONG).show();

        sAccountService.login(account);

        Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
        finish();
        startActivity(intent);
    }

    //ef innskráning mistekst fær notandi skilaboð um að það hafi ekki tekist.
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    /**
     * Skilyrðin sem þarf að uppfylla svo hægt sé innskrá aðgang
     * Notandi látinn vita ef einhvað gengur ekki upp.
     *
     * @return skilar réttur ef aðgangur innskráist.
     */
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //email þarf að vera skráð
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        //password þarf að vera skráð og á bilinu 4-10 bókstafir
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public static AccountService getAccountService() {
        return sAccountService;
    }
}
