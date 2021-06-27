package com.haidarh.jwork_android.ui.finished;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.haidarh.jwork_android.R;
import com.haidarh.jwork_android.classes.Invoice;
import com.haidarh.jwork_android.request.JobFetchRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FinishedJobActivity extends AppCompatActivity {

    int invoiceId;
    Context context;
    Handler handler;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_job);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.invoice_list);

        ProgressBar progressBar = findViewById(R.id.progress_bar);

        fetchJob();

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                context = getApplicationContext();
                recyclerView = findViewById(R.id.recyclerView);
                recylerViewLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(recylerViewLayoutManager);
                recyclerViewAdapter = new FinishedJobAdapter(context, invoiceList);
                recyclerView.setAdapter(recyclerViewAdapter);
                progressBar.setVisibility(View.GONE);
            }
        },1000);
    }

    private void fetchJob() {
        Bundle bundle = getIntent().getExtras();
        int jobseekerID = bundle.getInt("jobseekerID");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse.length() != 0){
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject invoice = jsonResponse.getJSONObject(i);
                            JSONObject jobseeker = invoice.getJSONObject("jobseeker");
                            JSONArray jobs = invoice.getJSONArray("jobs");

                            String jobName = "";
                            String jobFee = "";

                            for (int j = 0; j < jobs.length(); j++) {
                                JSONObject jobs2 = jobs.getJSONObject(j);
                                jobName = jobs2.getString("name");
                                jobFee = jobs2.getString("fee");
                            }

                            invoiceId = invoice.getInt("id");

                            String refCode = "";

                            if (!invoice.getString("paymentType").equals("BankPayment")) {
                                refCode = invoice.getJSONObject("bonus").getString("referralCode");
                            }

                            invoiceList.add(new Invoice(invoice.getInt("id"), jobName,
                                    invoice.getString("date"), jobFee, invoice.getString("totalFee"),
                                    jobseeker.getString("name"), invoice.getString("invoiceStatus"),
                                    invoice.getString("paymentType"), refCode));
                        }

                    } else {
                        Toast.makeText(FinishedJobActivity.this, "No applied job yet", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}