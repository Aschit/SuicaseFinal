package com.example.suicasedesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    EditText Name,Email,Password,cPassword;  //EditText fields to input user registration information such as name, email, password, and password confirmation.
    Button Signup,Signin; // Buttons for user registration and navigation to the login screen.
    FirebaseAuth firebaseAuth; // Firebase Authentication to handle user registration.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //his method is called when the activity is created.
        setContentView(R.layout.activity_signup);

        //It calls the findId method to initialize UI elements and set up click listeners.

        findId();
    }
    public void findId(){
        Name=findViewById(R.id.etName);
        Email=findViewById(R.id.etEmail);
        Password=findViewById(R.id.EtPassword);
        cPassword=findViewById(R.id.etCpassword);
        Signup=findViewById(R.id.signupBtn);
        Signin=findViewById(R.id.signinBtn);
        firebaseAuth=FirebaseAuth.getInstance();
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=Name.getText().toString().trim();
                String email=Email.getText().toString().trim();
                String password=Password.getText().toString().trim();
                String cpassword=cPassword.getText().toString().trim();

                if (name.isEmpty()){
                    Toast.makeText(signup.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                if (email.isEmpty()){
                    Toast.makeText(signup.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                if (password.isEmpty()){
                    Toast.makeText(signup.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                if (password.equals(cpassword)){
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(getApplicationContext(), login.class);
                                        startActivity(intent);
                                        Toast.makeText(signup.this, "Successful", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(signup.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                //This method initializes UI elements and sets up click listeners for buttons.
                //It sets click listeners for the "Signin" button to navigate to the login screen and the "Signup" button to register a new user.
                //Inside the "Signup" button's click listener, it performs the following tasks:
                //Retrieves user-entered data from the EditText fields for name, email, password, and password confirmation.
                //Checks if any of the fields are empty and displays a toast message if they are.
                //Compares the entered password and password confirmation to ensure they match.
                //If the passwords match, it uses Firebase Authentication to create a new user account with the provided email and password.
                //It adds an OnCompleteListener to handle the registration task's result.
                //If registration is successful, it navigates to the login screen (login activity) and displays a success toast message.
                //If registration fails, it displays a failure toast message.
            }
        });
    }
}

