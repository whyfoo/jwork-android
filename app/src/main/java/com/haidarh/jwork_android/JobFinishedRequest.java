package com.haidarh.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JobFinishedRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoiceStatus/";
    private Map<String, String> params;

    public JobFinishedRequest(int invoiceId, String status, Response.Listener<String> listener) {
        super(Method.PUT, URL + invoiceId, listener, null);
        params = new HashMap<>();
        params.put("status", status);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}