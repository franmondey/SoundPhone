package com.franmondey.soundphone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class SoundcloudActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundcloud);
        mWebView = (WebView) findViewById(R.id.webView);

        try {
            InputStream is = getResources().openRawResource(R.raw.soundcloud);

            String data = getResultFromStream(is);

            WebSettings ws = mWebView.getSettings();
            ws.setJavaScriptEnabled(true);
            ws.setJavaScriptCanOpenWindowsAutomatically(true);

            MyWebChromeClient chromeClient = new MyWebChromeClient();
            MyWebViewClient webViewClient = new MyWebViewClient();

            mWebView.setWebChromeClient(chromeClient);
            mWebView.setWebViewClient(webViewClient);

            mWebView.loadData(data, "text/html","UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.e("overrideUrlLoading", "URL: " + url);

            if(url.startsWith("http://connect.soundcloud.com/examples/callback.html") && url.contains("access_token")){
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    }

    private synchronized String getResultFromStream(InputStream stream)
            throws Exception {

        StringBuffer buffer = new StringBuffer();
        int ch = 0;
        while ((ch = stream.read()) != -1)
            buffer.append((char) ch);
        String result = buffer.toString().trim();
        return result;
    }
}