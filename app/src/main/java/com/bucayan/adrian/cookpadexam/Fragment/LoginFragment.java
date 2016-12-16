package com.bucayan.adrian.cookpadexam.Fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bucayan.adrian.cookpadexam.Api.AuthenticationService;
import com.bucayan.adrian.cookpadexam.Api.ServiceGenerator;
import com.bucayan.adrian.cookpadexam.MainActivity;
import com.bucayan.adrian.cookpadexam.Model.Token;
import com.bucayan.adrian.cookpadexam.Model.User;
import com.bucayan.adrian.cookpadexam.R;
import com.bucayan.adrian.cookpadexam.Utils.InstagramData;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private Button instagramBtn;
    private WebView webView;
    private LinearLayout webViewLayout;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instagramBtn = (Button) view.findViewById(R.id.btn_instagram);
        webViewLayout = (LinearLayout)  view.findViewById(R.id.layout_webView);
        webView = (WebView)  view.findViewById(R.id.webView);

        instagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCookpadExamPreferences.getInstagramTokenId() == null) {
                    instagramBtn.setVisibility(View.GONE);
                    webViewLayout.setVisibility(View.VISIBLE);
                    webView.loadUrl("https://api.instagram.com/oauth/authorize/?client_id=" + InstagramData.CLIENT_ID +
                            "&redirect_uri=" + InstagramData.CALLBACK_URL + "&response_type=code&display=touch&scope=likes+comments+relationships");
                } else {
                    mCookpadExamPreferences.resetAccessToken();
                    instagramBtn.setText(getString(R.string.login_with_instagram));
                }

            }
        });

        webView = (WebView)  view.findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "Page Start Url is " + url);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view,url);
                Log.d(TAG, "Page Finished Url is " + url);

                if (url.startsWith(InstagramData.CALLBACK_URL)) {
                    Log.d(TAG, "Callback is here " + url);
                    String urls[] = url.split("=");
                    Log.d(TAG, "code is here = " + urls[1]);

                    // hide webview
                    webViewLayout.setVisibility(View.GONE);

                    getAccessToken(urls[1]);
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCookpadExamPreferences.getInstagramTokenId() == null)
            instagramBtn.setText(getString(R.string.login_with_instagram));
        else
            instagramBtn.setText(getString(R.string.logout_with_instagram));
    }

    private void getAccessToken(String code) {

        // save Instagram code on shared preferences
        mCookpadExamPreferences.setInstagramCode(code);

        final AuthenticationService authenticationService = ServiceGenerator.getInstance().getAuthenticationService();
        Call<Token> getAccessTokenTask = authenticationService.getInstagramAccessToken(
                InstagramData.CLIENT_ID, InstagramData.CLIENT_SECRET,
                InstagramData.GRANT_TYPE, InstagramData.CALLBACK_URL, code);

        getAccessTokenTask.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    Token token = response.body();

                    // save in preferences
                    mCookpadExamPreferences.setInstagramTokenId(token.getAccess_token());
                    mCookpadExamPreferences.setUserName(token.getUser().getUsername());
                    mCookpadExamPreferences.setId(token.getUser().getId());
                    mCookpadExamPreferences.setFullName(token.getUser().getFull_name());

                    Log.d(TAG, "Instagram Token = " + token.getAccess_token());

                    instagramBtn.setText(getString(R.string.logout_with_instagram));

                    toWelcomeFragment(token.getUser());
                } else {
                    //Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Failure " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    private void toWelcomeFragment(User user) {
        WelcomeFragment welcomeFragment = new WelcomeFragment();

        Bundle args = new Bundle();
        args.putSerializable("user", user);
        welcomeFragment.setArguments(args);

        final MainActivity activity = ((MainActivity) getContext());
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .detach(welcomeFragment)
                .replace(R.id.frmContentFrame, welcomeFragment,
                        WelcomeFragment.class.getSimpleName())
                .attach(welcomeFragment)
                .addToBackStack("welcomeFragment")
                .commit();
    }

}
