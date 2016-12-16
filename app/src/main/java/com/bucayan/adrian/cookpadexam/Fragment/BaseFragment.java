package com.bucayan.adrian.cookpadexam.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bucayan.adrian.cookpadexam.Utils.CookpadExamPref;

/**
 * @author Adrian Bucayan on 3/11/16.
 */
public class BaseFragment extends Fragment {

    public CookpadExamPref mCookpadExamPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCookpadExamPreferences = new CookpadExamPref(getActivity());
    }

    public CookpadExamPref getmCookpadExamPreferences() {
        return mCookpadExamPreferences;
    }

    public void setmCookpadExamPreferences(CookpadExamPref mCookpadExamPreferences) {
        this.mCookpadExamPreferences = mCookpadExamPreferences;
    }

}
