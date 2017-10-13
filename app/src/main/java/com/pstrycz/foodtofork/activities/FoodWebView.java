package com.pstrycz.foodtofork.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pstrycz.foodtofork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2017-07-23.
 */

public class FoodWebView extends AppCompatActivity {

    public static final String WEB_URL_KEY = "WEB_URL_KEY";

    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getStringExtra(WEB_URL_KEY));

    }
}


