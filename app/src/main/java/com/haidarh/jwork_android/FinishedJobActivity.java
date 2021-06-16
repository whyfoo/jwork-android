package com.haidarh.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class FinishedJobActivity extends AppCompatActivity {

    int invoiceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_job);

        ConstraintLayout layout = findViewById(R.id.layout_finished_job);

        layout.setVisibility(View.INVISIBLE);

        fetchJob();

        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnFinish = findViewById(R.id.btn_finish);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            finish();
                        }
                        catch (JSONException e) {
                            Toast.makeText(FinishedJobActivity.this, "Cancel Job Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Toast.makeText(FinishedJobActivity.this, "Job Has Been Canceled",
                        Toast.LENGTH_SHORT).show();

                InvoiceStatusRequest cancelRequest = new InvoiceStatusRequest(invoiceId, "Cancelled", responseListener);
                RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
                queue.add(cancelRequest);

                finish();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Toast.makeText(FinishedJobActivity.this, "Job Has Been Finished",
                        Toast.LENGTH_SHORT).show();
                InvoiceStatusRequest finishedRequest = new InvoiceStatusRequest(invoiceId, "Finished", responseListener);
                RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
                queue.add(finishedRequest);

                finish();
            }
        });
    }

    private void fetchJob() {
        Bundle bundle = getIntent().getExtras();
        int jobseekerID = bundle.getInt("jobseekerID");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    TextView titleMain = findViewById(R.id.title_main);
                    TextView tvJobseekerName = findViewById(R.id.tv_jobseeker_name);
                    TextView tvInvoiceDate = findViewById(R.id.tv_invoice_date);
                    TextView tvPaymentType = findViewById(R.id.tv_payment_type);
                    TextView tvJobName = findViewById(R.id.tv_job_name);
                    TextView tvTotalFee = findViewById(R.id.tv_totalfee);
                    TextView tvFee = findViewById(R.id.tv_fee);
                    TextView tvInvoiceStatus = findViewById(R.id.tv_invoice_status);
                    TextView titleRefCode = findViewById(R.id.title_refcode);
                    TextView tvRefCode = findViewById(R.id.tv_refcode);

                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse.length() != 0){
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject invoice = jsonResponse.getJSONObject(i);
                            JSONObject jobseeker = invoice.getJSONObject("jobseeker");
                            JSONArray jobs = invoice.getJSONArray("jobs");

                            for (int j = 0; j < jobs.length(); j++) {
                                JSONObject jobs2 = jobs.getJSONObject(j);
                                tvJobName.setText(jobs2.getString("name"));
                                tvFee.setText(jobs2.getString("fee"));
                            }

                            titleMain.setText(getResources().getString(R.string.invoice_id, invoice.getInt("id")));
                            invoiceId = invoice.getInt("id");

                            tvJobseekerName.setText(jobseeker.getString("name"));
                            tvInvoiceDate.setText(invoice.getString("date"));
                            tvPaymentType.setText(invoice.getString("paymentType"));
                            tvTotalFee.setText(invoice.getString("totalFee"));
                            tvInvoiceStatus.setText(invoice.getString("invoiceStatus"));

                            if (invoice.getString("paymentType").equals("BankPayment")) {
                                titleRefCode.setVisibility(View.INVISIBLE);
                                tvRefCode.setVisibility(View.INVISIBLE);
                            } else {
                                tvRefCode.setText(invoice.getJSONObject("bonus").getString("referralCode"));
                            }
                        }

                        ConstraintLayout layout = findViewById(R.id.layout_finished_job);
                        layout.setVisibility(View.VISIBLE);
                    } else {
                        finish();
                    }

                } catch (JSONException e) {
                    Toast.makeText(FinishedJobActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                }
            }
        };

        JobFetchRequest jobFetchRequest = new JobFetchRequest(jobseekerID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
        queue.add(jobFetchRequest);
    }
}