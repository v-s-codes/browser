package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton homeButton, searchButton;
    EditText addressInput;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeButton=findViewById(R.id.home_btn);
        addressInput=findViewById(R.id.address_input);
        webView=findViewById(R.id.web_view);
        searchButton=findViewById(R.id.search_btn);

        webView.loadUrl("https://www.google.com");
        webView.getSettings().setJavaScriptEnabled(true);

        addressInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((event!=null && event.getKeyCode()==KeyEvent.KEYCODE_ENTER) || actionId== EditorInfo.IME_ACTION_DONE){

                    searchButton.performClick();
                    addressInput.setText("");
                }
                return false;
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                webView.loadUrl("https://www.google.com/");
                addressInput.setText("");
            }

        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addressInput.getText().toString().isEmpty()){
                    String url=addressInput.getText().toString();

                    if(url.contains("http://") || url.contains("https://")){
                        if(Patterns.WEB_URL.matcher(url).matches()){
                            webView.loadUrl(url);
                        }
                        else{
                            webView.loadUrl("https://www.google.com/search?q="+url.replace("http://","").replace("https://",""));
                        }
                    }
                    else{
                        String x="http://"+url;
                        String y="https://"+url;
                        if(Patterns.WEB_URL.matcher(y).matches()){
                            webView.loadUrl(y);
                        }
                        else if(Patterns.WEB_URL.matcher(x).matches()){
                            webView.loadUrl(x);
                        }
                        else{
                            webView.loadUrl("https://www.google.com/search?q="+url.replace("http://","").replace("https://",""));
                        }
                    }
                }
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            finish();
        }
    }
}