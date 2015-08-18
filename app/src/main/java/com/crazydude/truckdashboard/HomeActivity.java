package com.crazydude.truckdashboard;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by kartavtsev.s on 18.08.2015.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {

    @ViewById(R.id.activity_home_splashscreen)
    View mSplashScreen;

    boolean mIsSplashShown = false;

    @AfterViews
    void initViews() {
        if (!mIsSplashShown) {
            animateSplashscreen();
            mIsSplashShown = true;
        }
    }

    private void animateSplashscreen() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mSplashScreen, "alpha", 1, 0);
        animator.setStartDelay(2500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSplashScreen.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(500);
        animator.start();
    }
}
