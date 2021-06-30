package com.haidarh.jwork_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * mengambil data job yang tersedia pada database job
 *
 * @author Haidar Hanif
 */
public class MenuRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/job";

    /**
     * Instantiates a new Menu request.
     *
     * @param listener the listener
     */
    public MenuRequest(Response.Listener<String> listener)
    {
        super(Method.GET, URL, listener, null);
    }
}
