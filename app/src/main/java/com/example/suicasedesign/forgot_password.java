package com.example.suicasedesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
    Button GetEmail; // button for triggering password reset
    EditText resetEmail;  // A reference to an EditText field where the user can input their email for password reset.
    FirebaseAuth firebaseAuth; //irebase Authentication, which is used for sending password reset emails.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password); // This method is called when the activity is created.

        //  calls the init method and initializes the firebaseAuth object.
        init();
        firebaseAuth=FirebaseAuth.getInstance();
    }
    public void init(){

        //his custom method initializes the button components and sets an OnClickListener for the "Get Email" button.
        GetEmail=findViewById(R.id.getEmail);
        resetEmail=findViewById(R.id.resetEmail);
        GetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=resetEmail.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(forgot_password.this, "Enter Emial", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(forgot_password.this, "Check Email", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                // When the button is clicked, it performs the following steps:
                                //It retrieves the email entered by the user in the resetEmail EditText field.
                                //It checks if the email field is empty and displays a toast message if it is.
                                //If the email field is not empty, it calls firebaseAuth.
                                // sendPasswordResetEmail(email) to send a password reset email to the provided email address.
                                //It listens for the completion of this task using an OnCompleteListener:
                                //If the task is successful, it displays a toast message saying "Check Email,
                                // " indicating that a password reset email has been sent to the user.
                                //If the task is not successful, it handles any potential errors that may occur during the password reset process.
                            });
                }
            }
        });
    }
}

