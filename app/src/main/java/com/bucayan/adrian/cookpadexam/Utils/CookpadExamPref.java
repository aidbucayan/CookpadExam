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
    private static final String PREFERENCE_USERNAME = "username";
    private static final String PREFERENCE_ID = "id";
    private static final String PREFERENCE_FULL_NAME = "full_name";

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
        editor.putString(PREFERENCE_USERNAME, null);
        editor.putString(PREFERENCE_ID, null);
        editor.putString(PREFERENCE_FULL_NAME, null);
        editor.apply();
    }


    public String getUserName() {
        return mPreferences.getString(PREFERENCE_USERNAME, null);
    }

    public void setUserName(String code) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_USERNAME, code);
        editor.apply();
    }

    public String getId() {
        return mPreferences.getString(PREFERENCE_ID, null);
    }

    public void setId(String code) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_ID, code);
        editor.apply();
    }

    public String getFullName() {
        return mPreferences.getString(PREFERENCE_FULL_NAME, null);
    }

    public void setFullName(String code) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_FULL_NAME, code);
        editor.apply();
    }


}
