package com.bucayan.adrian.cookpadexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.bucayan.adrian.cookpadexam.Fragment.LoginFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // to Login Fragment
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .detach(loginFragment)
                .replace(R.id.frmContentFrame, loginFragment, LoginFragment.class.getSimpleName())
                .attach(loginFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
