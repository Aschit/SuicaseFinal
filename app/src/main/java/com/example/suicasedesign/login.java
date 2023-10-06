package com.example.suicasedesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    Button SignupBtn,LoginBtn;  // buttons for signing up and logging in.
    EditText etEmail,etPassword;  // to input fields for email and password.
    TextView forgotPassword; //A TextView that provides an option to initiate the password recovery process.
    FirebaseAuth firebaseAuth; // Firebase Authentication for handling user authentication.


    //This method is called when the activity is created.
    //It sets the content view to the layout defined in activity_login.xml.
    //It initializes the firebaseAuth instance and calls the init method.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        init();

        //This method initializes UI names and sets click listeners for buttons.
        //It sets click listeners for the "Forgot Password," "Sign Up," and "Log In" buttons.

    }
    public void init(){
        SignupBtn=findViewById(R.id.signup);
        LoginBtn=findViewById(R.id.LoginBtn);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.EtPassword);
        forgotPassword=findViewById(R.id.txt_forgotPassword);

        forgotPassword.setOnClickListener(this::startPasswordRecoveryProcess);
        SignupBtn.setOnClickListener(this::startSignUpActivity);
        LoginBtn.setOnClickListener(this::login);
    }

//    private void byPass(View view) {
//        startActivity( new Intent(getApplicationContext(), MainActivity.class)); // go directly to main page
//
//    }

    private void login(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(login.this, "Enter Email", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(login.this, "Enter Password", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //his method is called when the "Log In" button is clicked.
        //It retrieves the email and password entered by the user.
        //It checks if either the email or password fields are empty and displays a toast message if they are.
        //It attempts to sign in the user using firebaseAuth.signInWithEmailAndPassword.
        //It listens for the completion of the authentication task and,
        // if successful, starts the MainActivity. If not, it displays a login failure message.

    }

    //This method is called when the "Forgot Password" TextView is clicked.
    //It starts the forgot_password activity to initiate the password recovery process

    private void startPasswordRecoveryProcess(View view) {
        Intent intent = new Intent(getApplicationContext(), forgot_password.class);
        startActivity(intent);
    }

    //This method is called when the "Sign Up" button is clicked.
    //It starts the signup activity to allow users to create a new account.

    private void startSignUpActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), signup.class);
        startActivity(intent);
    }
}

