package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailSignUp, edtUsernameSignUp, edtPasswordSignUp;
    private Button btnSignUpSignUpActivity, btnLoginSignUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtUsernameSignUp = findViewById(R.id.edtUsernameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        btnSignUpSignUpActivity = findViewById(R.id.btnSignUpSignUpActivity);
        btnLoginSignUpActivity = findViewById(R.id.btnLoginSignUpActivity);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        btnSignUpSignUpActivity.setOnClickListener(this);
        btnLoginSignUpActivity.setOnClickListener(this);

        edtPasswordSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN) {

                    onClick(btnSignUpSignUpActivity);

                }
                return false;
            }
        });

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

    }


    private void transitionToSocialMediaActivity() {

        Intent intent = new Intent(SignUp.this, WhatsAppUsersActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignUpSignUpActivity:

                if (edtEmailSignUp.getText().toString().equals("") ||
                        edtUsernameSignUp.getText().toString().equals("") ||
                        edtPasswordSignUp.getText().toString().equals("")) {

                    FancyToast.makeText(SignUp.this,
                            "Email, Username, Password is required!",
                            Toast.LENGTH_SHORT, FancyToast.INFO,
                            true).show();

                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmailSignUp.getText().toString());
                    appUser.setUsername(edtUsernameSignUp.getText().toString());
                    appUser.setPassword(edtPasswordSignUp.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtUsernameSignUp.getText().toString());
                    progressDialog.show();
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this,
                                        appUser.getUsername() + " is signed up",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                        true).show();

                                transitionToSocialMediaActivity();

                            } else {

                                FancyToast.makeText(SignUp.this,
                                        "There was an error: " + e.getMessage(),
                                        Toast.LENGTH_LONG, FancyToast.ERROR,
                                        true).show();

                            }


                            progressDialog.dismiss();
                        }
                    });

                }
                break;

            case R.id.btnLoginSignUpActivity:

                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void rootLayoutTapped(View view) {

        try {

            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {

            e.printStackTrace();
        }


    }
}