package com.serious.budgeat;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.serious.budgeat.Activity.OnBoardingActivity;

public class Utils {
    private Utils() {
        // private constructor.
    }

    /**
     * Push an "openScreen" event with the given screen name. Tags that match that event will fire.
     */
    public static void pushOpenScreenEvent(Context context, String screenName) {
        Log.d("push open screen", screenName);

        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("openScreen", DataLayer.mapOf("screenName", screenName));
    }

    /**
     * Push a "closeScreen" event with the given screen name. Tags that match that event will fire.
     */
    public static void pushCloseScreenEvent(Context context, String screenName) {
        Log.d("push close screen", screenName);


        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("closeScreen", DataLayer.mapOf("screenName", screenName));
    }

    public static void pushStartOnboarding(Context context) {
        Log.d("push start onboarding", "");


        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("onBoardingStart", DataLayer.mapOf("screenName", "onBoarding"));
    }

    public static void pushStopOnboarding(Context context, String action, Integer departureSlide) {
        Log.d("push stop onboarding", "");
        Log.d("push stop action", action);
        Log.d("push stop slide", String.valueOf(departureSlide));


        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();

        dataLayer.push(DataLayer.mapOf("departureSlide", departureSlide));
        dataLayer.push(DataLayer.mapOf("action", action));
        dataLayer.pushEvent("onBoardingStop", DataLayer.mapOf("screenName", "onBoarding"));
    }

    public static void pushSlideOnboarding(Context context, Integer slideFrom, Integer slideTo) {
        Log.d("push onboarding from", String.valueOf(slideFrom));
        Log.d("push onboarding to", String.valueOf(slideTo));

        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.push(DataLayer.mapOf("from", slideFrom));
        dataLayer.push(DataLayer.mapOf("to", slideTo));
        dataLayer.pushEvent("onBoardingSlide", DataLayer.mapOf("screenName", "onBoarding"));
    }
}