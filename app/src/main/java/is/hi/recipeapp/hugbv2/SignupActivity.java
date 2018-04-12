package is.hi.recipeapp.hugbv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
 * @date april 2018
 *
 * Klasi fyrir stofnun aðganga svo hægt sé að nota smáforrit.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_address) EditText _addressText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    private static Account newAccount;

    /**
     * Tilbúin gervi gögn til að logga sig inn, user og password.
     * TODO: henda út eftir tengingu við gagnagrunn
     */
    private static final ArrayList<String> DUMMY_CREDENTIALS = new ArrayList();

    private static ArrayList<Account> mAccounts;

    private String[] temp = new String[] {
            "foo@example.com:hello", "bar@example.com:world", "hopur6@hi.is:hopur6",
            "testmail@gmail.com:TestPassWord123"
    };

    private AccountService sAccountService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        sAccountService = LoginActivity.getAccountService();
        sAccountService.setSignupActivity(SignupActivity.this);

        mAccounts = sAccountService.getAccounts();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lokar registration glugga og skilar okkur í login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        // signup ferlið
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        boolean token = true;
                        for (Account account : mAccounts) {
                            if (account.getEmail().equals(_emailText.getText().toString())) {
                                token = false;
                            }
                        }

                        if (token) {
                            onSignupSuccess();
                        } else {
                            onSignupFailed();
                        }

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    //lætur notanda vita ef signup gengur upp
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();

        newAccount = new Account(name, address, email, mobile, password);

        //TODO: henda út eftir tengingu við gagnagrunn
        //dummy credentials
        //DUMMY_CREDENTIALS.add(name + ":" + password);
        sAccountService.addAccount(newAccount);

        //Toast.makeText(getBaseContext(), "Signup successful!", Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(SignupActivity.this, SearchActivity.class);
        //finish();
        //startActivity(intent);
    }

    //lætur notanda vita ef signup gengur ekki upp.
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed, email already in use", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    /**
     * Skilyrðin sem þarf að uppfylla svo hægt sé að stofna aðgang.
     * Notandi látinn vita ef einhvað gengur ekki upp
     *
     * @return skilar réttu ef aðgangur hefur verið stofnaður
     */
    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        //nafn verður að innihalda lágmark 3 bókstafi
        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        //heimilsfang
        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }

        //email verður að vera á réttu formi
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        //símanúmer verður að vera á réttu formi (7 tölustafir)
        if (mobile.isEmpty() || mobile.length()!=7) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }


        //password verður að hafa á bilinu 4-10 bókstafi
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        //staðfesting á password sé rétt
        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    public void signupSuccess() {
        Intent intent = new Intent(this, SearchActivity.class);
        finish();
        startActivity(intent);
    }
}