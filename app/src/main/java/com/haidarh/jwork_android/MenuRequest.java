package com.haidarh.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class MenuRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/job";

    public MenuRequest(Response.Listener<String> listener)
    {
        super(Method.GET, URL, listener, null);
    }
}
