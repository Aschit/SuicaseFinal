package com.example.suicasedesign;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splash_screen extends AppCompatActivity {
    ImageView imageView;
//ImageView used to display an image or logo on the splash screen.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //This method is called when the activity is created.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView=findViewById(R.id.sImg);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        imageView.startAnimation(animation);
        //It loads an animation from the resource file R.anim.animation using AnimationUtils.loadAnimation()
        // and assigns it to the imageView using the startAnimation() method.

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent=new Intent(splash_screen.this,signup.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }

    //A new thread is created within the onCreate method using the Thread class.
    //Inside the thread's run method, there is a try-catch block.
    //The sleep(3000) statement pauses the execution of the thread for 3 seconds (3,000 milliseconds).
    //After the sleep period, an Intent is created to navigate to the signup activity (user registration).
    //The startActivity(intent) method is called to start the signup activity.
    //The splash screen is typically used to display branding or loading animations while some initialization tasks are performed in the background.
    //After the specified delay, the splash screen transitions to the next activity (in this case, the user registration screen).
}

