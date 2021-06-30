package com.haidarh.jwork_android.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * mengganti invoice status pada invoice menjadi "cancelled" atau "finished"
 *
 * @author Haidar Hanif
 */
public class InvoiceStatusRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;

    /**
     * Instantiates a new Invoice status request.
     *
     * @param invoiceId the invoice id
     * @param status    the status
     * @param listener  the listener
     */
    public InvoiceStatusRequest(int invoiceId, String status, Response.Listener<String> listener) {
        super(Method.PUT, URL + invoiceId, listener, null);
        params = new HashMap<>();
        params.put("status", status);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}


