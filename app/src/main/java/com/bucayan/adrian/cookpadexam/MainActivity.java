package com.bucayan.adrian.cookpadexam;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bucayan.adrian.cookpadexam.Api.AuthenticationService;
import com.bucayan.adrian.cookpadexam.Api.ServiceGenerator;
import com.bucayan.adrian.cookpadexam.Model.Token;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button instagramBtn;
    private WebView webView;
    private LinearLayout webViewLayout;

    private CookpadExamPref mCookpadExamPreferences;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instagramBtn = (Button) findViewById(R.id.btn_instagram);
        webViewLayout = (LinearLayout) findViewById(R.id.layout_webView);
        webView = (WebView) findViewById(R.id.webView);

        mCookpadExamPreferences = new CookpadExamPref(MainActivity.this);

        instagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instagramBtn.setVisibility(View.GONE);
                webViewLayout.setVisibility(View.VISIBLE);
                webView.loadUrl("https://api.instagram.com/oauth/authorize/?client_id=" + InstagramData.CLIENT_ID +
                        "&redirect_uri=" + InstagramData.CALLBACK_URL + "&response_type=code&display=touch&scope=likes+comments+relationships");
                }
        });

        webView = (WebView) findViewById(R.id.webView);
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

                if (url.startsWith(getString(R.string.instagram_redirect_uri))) {
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
    protected void onResume() {
        super.onResume();
        if (mCookpadExamPreferences.getInstagramTokenId() == null)
            instagramBtn.setText("Login with Instagram");
        else
            instagramBtn.setText("Disconnect with Instagram");
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
                    mCookpadExamPreferences.setInstagramTokenId(token.getAccess_token());
                    Log.d(TAG, "Instagram Token = " + token.getAccess_token());
                } else {
                   // Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Failure " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }


}
