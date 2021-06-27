package com.haidarh.jwork_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class BonusRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/bonus/refcode/";

    public BonusRequest(String referralCode, Response.Listener<String> listener)
    {
        super(Method.GET, URL+referralCode, listener, null);
    }
}
