package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    private EditText newUsername;
    private EditText newPassword;
    private Button btnSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        newUsername = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "OnClick signup button");
                String username = newUsername.getText().toString();
                String password = newPassword.getText().toString();
                signUp(username, password);
            }
        });


    }

    private void signUp(String username, String password) {
            Log.i(TAG, "Attempting to sign user up"+username);
            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e != null) {
                        Log.e(TAG,"issue with sign up", e);
                        return;
                    }

                    Log.i(TAG, "Successful Sign Up!");
                    goMainActivity();
                    Toast.makeText(SignupActivity.this, "Successful Sign Up!", Toast.LENGTH_SHORT).show();

                }
            });

        }

    private void goMainActivity() {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }


