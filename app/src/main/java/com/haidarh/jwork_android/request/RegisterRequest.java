package com.haidarh.jwork_android.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * melakukan request untuk register jobseeker
 *
 * @author Haidar Hanif
 */
public class RegisterRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/jobseeker/register";
    private Map<String, String> params;

    /**
     * Instantiates a new Register request.
     *
     * @param name     the name
     * @param email    the email
     * @param password the password
     * @param listener the listener
     */
    public RegisterRequest(String name, String email, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}
