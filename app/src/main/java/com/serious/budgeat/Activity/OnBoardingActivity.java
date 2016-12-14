package com.serious.budgeat.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

public class OnBoardingActivity extends AppIntro {
    private Integer currentSlide = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.pushStartOnboarding(this);


        String title = "titre";
        String description = "coucou";
        Integer image = R.drawable.yolo;

        addSlide(AppIntroFragment.newInstance(title, description, image, Color.BLUE));
        addSlide(AppIntroFragment.newInstance(title, description, image, Color.RED));
        addSlide(AppIntroFragment.newInstance(title, description, image, Color.YELLOW));
        addSlide(AppIntroFragment.newInstance(title, description, image, Color.GREEN));


    }

    void gotoLanding(){
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        Utils.pushStopOnboarding(this, "skip", currentSlide);
        gotoLanding();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Utils.pushStopOnboarding(this, "done", currentSlide);
        gotoLanding();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        Utils.pushSlideOnboarding(this, currentSlide, currentSlide + 1);
        currentSlide++;
    }

}
