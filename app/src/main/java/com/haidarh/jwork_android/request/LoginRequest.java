package com.haidarh.jwork_android.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * request untuk login jobseeker
 *
 * @author Haidar Hanif
 */
public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/jobseeker/login";
    private Map<String, String> params;

    /**
     * Instantiates a new Login request.
     *
     * @param email    the email
     * @param password the password
     * @param listener the listener
     */
    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}

