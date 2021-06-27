package com.haidarh.jwork_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ApplyJobRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/";
    private Map<String, String> params;
    
    public ApplyJobRequest(String jobIdList, String jobseekerId, Response.Listener<String> listener)
    {
        super(Method.POST, URL+"createBankPayment", listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobIdList);
        params.put("jobseekerId", jobseekerId);
    }

    public ApplyJobRequest(String jobIdList, String jobseekerId, String referralCode, Response.Listener<String> listener)
    {
        super(Method.POST, URL+"createEWalletPayment", listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobIdList);
        params.put("jobseekerId", jobseekerId);
        params.put("referralCode", referralCode);
    }

    @Override
    protected Map<String, String> getParams()
    {
        return params;
    }
}
