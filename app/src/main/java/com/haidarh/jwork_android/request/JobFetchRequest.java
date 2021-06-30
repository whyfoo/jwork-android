package com.haidarh.jwork_android.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * mengambil data invoice by jobseeker id
 *
 * @author Haidar Hanif
 */
public class JobFetchRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/jobseeker/";
    private Map<String, String> params;

    /**
     * Instantiates a new Job fetch request.
     *
     * @param jobseekerId the jobseeker id
     * @param listener    the listener
     */
    public JobFetchRequest(int jobseekerId, Response.Listener<String> listener) {
        super(Method.GET, URL + jobseekerId, listener, null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}