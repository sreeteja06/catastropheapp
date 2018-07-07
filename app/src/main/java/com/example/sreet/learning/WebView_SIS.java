package com.example.sreet.learning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebView_SIS extends AppCompatActivity {
    private WebView webView;

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }else{
        super.onBackPressed();}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view__sis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      // WebSettings webSettings = webView.getSettings();
       //webSettings.setJavaScriptEnabled(true);
        setTitle("SIS");
        webView = findViewById(R.id.sisWeb);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                //progDailog.dismiss();
            }
        });

        webView.loadUrl("http://42.104.112.137/sz/Login.aspx");

       /* webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("www.google.com");
        //42.104.112.137/sz/Login.aspx*/
    }
}
