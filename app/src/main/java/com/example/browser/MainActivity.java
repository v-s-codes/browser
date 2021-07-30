package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton homeButton, searchButton;
    EditText addressInput;
    WebView webView;
    ImageView imageView;
    Drawable drawble;
    boolean completelyLoaded = true, redirect= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        homeButton=findViewById(R.id.home_btn);
        addressInput=findViewById(R.id.address_input);
        webView=findViewById(R.id.web_view);
        searchButton=findViewById(R.id.search_btn);
        imageView=findViewById(R.id.loading);
        drawble=getResources().getDrawable(R.drawable.loading);
        imageView.setImageDrawable(drawble);
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
                addressInput.setCursorVisible(false);
            }

        });
        addressInput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addressInput.setCursorVisible(true);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressInput.setCursorVisible(false);
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
                completelyLoaded=false;
                imageView.setVisibility(View.VISIBLE);
                Log.d("completelyLoaded", completelyLoaded+"");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if(!completelyLoaded){
                    redirect=true;
                }
                completelyLoaded=false;
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(!redirect){
                    completelyLoaded=true;
                    imageView.setVisibility(View.GONE);
                }
                if(completelyLoaded && !redirect){
                    Log.d("completelyLoaded", completelyLoaded+"");
                }
                else{
                    redirect=false;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        addressInput.setCursorVisible(false);
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            finish();
        }
    }
}