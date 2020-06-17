package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailLogin,edtPasswordLogin;
    private Button btnLoginLoginActivity,btnSignUpLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLoginLoginActivity = findViewById(R.id.btnLoginLoginActivity);
        btnSignUpLoginActivity = findViewById(R.id.btnSignUpLoginActivity);

        btnSignUpLoginActivity.setOnClickListener(this);
        btnLoginLoginActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {

            transitionToSocialMediaActivity();

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnLoginLoginActivity:
                ParseUser.logInInBackground(edtEmailLogin.getText().toString(),
                        edtPasswordLogin.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {

                                if (user != null && e == null) {

                                    FancyToast.makeText(LoginActivity.this,
                                            user.getUsername() + " is Logged in successfully",
                                            Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                            true).show();
                                    transitionToSocialMediaActivity();
                                } else {

                                    FancyToast.makeText(LoginActivity.this,
                                            "There was an error: " + e.getMessage(),
                                            Toast.LENGTH_LONG, FancyToast.ERROR,
                                            true).show();

                                }
                            }
                        });

                break;

            case R.id.btnSignUpLoginActivity:
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                finish();
                break;


        }

    }

    private void transitionToSocialMediaActivity() {

        Intent intent = new Intent(LoginActivity.this, WhatsAppUsersActivity.class);
        startActivity(intent);
        finish();
    }
}