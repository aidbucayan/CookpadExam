package com.bucayan.adrian.cookpadexam.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Adrian Bucayan on 12/15/16.
 */

public class CookpadExamPref {

    private static final String PREFERENCE_INSTAGRAM_CODE = "instagram_code";
    private static final String PREFERENCE_INSTAGRAM_TOKEN_ID = "instagram_token_id";

    private Context mContext;
    private SharedPreferences mPreferences;

    public CookpadExamPref(Context context) {
        this.mContext = context;
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getInstagramCode() {
        return mPreferences.getString(PREFERENCE_INSTAGRAM_CODE, null);
    }

    public void setInstagramCode(String code) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_INSTAGRAM_CODE, code);
        editor.apply();
    }

    public String getInstagramTokenId() {
        return mPreferences.getString(PREFERENCE_INSTAGRAM_TOKEN_ID, null);
    }

    public void setInstagramTokenId(String tokenId) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_INSTAGRAM_TOKEN_ID, tokenId);
        editor.apply();
    }

    public void resetAccessToken() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_INSTAGRAM_CODE, null);
        editor.putString(PREFERENCE_INSTAGRAM_TOKEN_ID, null);
        editor.apply();
    }

}
