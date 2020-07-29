package com.example.hcaptcha_android;

import android.webkit.JavascriptInterface;

public class BridgeWebViewClass {

    public BridgeWebViewClass() {}

    @JavascriptInterface
    public void hCaptchaCallbackInAndroid(String h_response){

        CallAPI.doExecute(h_response);

    }
}
