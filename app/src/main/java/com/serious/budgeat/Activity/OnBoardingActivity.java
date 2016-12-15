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



        String title = "Personnalise ton sandwich";
        String description = "Choisit ton pain, ta viande, ton fromage & tes légumes";
        int image = R.drawable.illu_sandwich;

        addSlide(AppIntroFragment.newInstance(title, description, image, Color.parseColor("#ffd6a0")));


        title = "Reçois des réductions";
        description = "Soit débité à minuit pour profiter d'1% de réduction par étudiant de ton " +
                "école qui commande";
        image = R.drawable.illu_reduc;
        addSlide(AppIntroFragment.newInstance(title, description, image, Color.parseColor("#ffd6a0")));


        title = "Livré à l'école";
        description = "BudgEat dépose ton sandwich à ton école entre 12 et 13h";
        image = R.drawable.illu_livraison;

        addSlide(AppIntroFragment.newInstance(title, description, image, Color.parseColor("#ffd6a0")));


    }

    void goToLanding(){
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        Utils.pushStopOnboarding(this, "skip", currentSlide);
        goToLanding();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Utils.pushStopOnboarding(this, "done", currentSlide);
        goToLanding();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        Utils.pushSlideOnboarding(this, currentSlide, currentSlide + 1);
        currentSlide++;
    }

}
