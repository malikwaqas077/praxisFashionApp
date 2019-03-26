package com.example.hp.prixisfashion;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.animation.Animator;
import android.os.Handler;

import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.hp.prixisfashion.Admin.admin_home;
import com.example.hp.prixisfashion.Cusmoter.CustomerNavDrawerActivity;
import com.google.firebase.auth.FirebaseAuth;

import me.wangyuwei.particleview.ParticleView;

public class SplashScreen extends AppCompatActivity {
    ParticleView particleView;
    ImageView image;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        particleView=findViewById(R.id.particleView);
        image=(ImageView)findViewById(R.id.image);
        auth = FirebaseAuth.getInstance();

//        final Boolean session=getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("session",false);

        YoYo.with(Techniques.BounceIn).duration(8000).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                final float scale = getBaseContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (300 * scale + 0.5f);
                image.requestLayout();
                image.getLayoutParams().width=pixels;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).playOn(image);
        particleView.startAnim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (null != auth.getCurrentUser()) {
                    startActivity(new Intent(SplashScreen.this, admin_home.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));

                }

            }

        },8000);


    }
}

