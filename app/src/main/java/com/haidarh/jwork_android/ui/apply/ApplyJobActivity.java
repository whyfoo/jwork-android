package com.haidarh.jwork_android.ui.apply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.haidarh.jwork_android.R;
import com.haidarh.jwork_android.request.ApplyJobRequest;
import com.haidarh.jwork_android.request.BonusRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplyJobActivity extends AppCompatActivity {

    private int jobseekerID;
    private int jobID;
    private String jobName;
    private String jobCategory;
    private double jobFee;
    private int bonus;
    private String selectedPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        getSupportActionBar().hide();

        TextView tvJobName = findViewById(R.id.tv_jobseeker_name);
        TextView tvJobCategory = findViewById(R.id.tv_invoice_date);
        TextView tvJobFee = findViewById(R.id.tv_job_name);
        TextView textCode = findViewById(R.id.title_refcode);
        RadioGroup rgPayMethod = findViewById(R.id.rg_paymethod);
        final RadioButton rbEWallet = findViewById(R.id.rb_ewallet);
        final RadioButton rbBank = findViewById(R.id.rb_bank);
        final EditText edtReferralCode = findViewById(R.id.edt_refcode);
        final TextView tvTotalFee = findViewById(R.id.tv_totalfee);
        final Button btnCount = findViewById(R.id.btn_count);
        final Button btnApply = findViewById(R.id.btn_apply);
        final TextView titlePayment = findViewById(R.id.title_payment_type);

        btnApply.setEnabled(false);
        textCode.setVisibility(View.INVISIBLE);
        edtReferralCode.setVisibility(View.INVISIBLE);

        tvJobName.setText(jobName);
        tvJobCategory.setText(jobCategory);
        tvJobFee.setText(String.valueOf(jobFee));
        tvTotalFee.setText("0");

        Bundle extras = getIntent().getExtras();
        int jobseekerID = extras.getInt("jobseekerID");
        int jobID = extras.getInt("jobID");
        String jobName = extras.getString("jobName");
        String jobCategory = extras.getString("jobCategory");
        int jobFee = extras.getInt("jobFee");

        tvJobName.setText(jobName);
        tvJobCategory.setText(jobCategory);
        tvJobFee.setText(String.valueOf(jobFee));

        rgPayMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_ewallet) {
                    textCode.setVisibility(View.VISIBLE);
                    edtReferralCode.setVisibility(View.VISIBLE);
                    btnApply.setEnabled(true);
                } else if (checkedId == R.id.rb_bank){
                    textCode.setVisibility(View.INVISIBLE);
                    edtReferralCode.setVisibility(View.INVISIBLE);
                    btnApply.setEnabled(false);
                }
            }
        });

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbBank.isChecked()) {
                    tvTotalFee.setText(String.valueOf(jobFee));
                    btnApply.setEnabled(true);
                } else if(rbEWallet.isChecked() && edtReferralCode.getText() != null) {
                    Response.Listener<String> responseListener = new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getBoolean("active") && jsonObject.getInt("minTotalFee")<=jobFee)
                                {
                                    bonus = jsonObject.getInt("extraFee");
                                    tvTotalFee.setText(String.valueOf(jobFee + bonus));
                                    Toast.makeText(ApplyJobActivity.this, "Referral Code Found", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(ApplyJobActivity.this, "Your Referral Code is inactive or your fee is lower than the minimum total fee", Toast.LENGTH_LONG).show();
                                }
                            } catch(JSONException e) {
                                tvTotalFee.setText(String.valueOf(jobFee));
                                Toast.makeText(ApplyJobActivity.this, "Referral Code not Found", Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    BonusRequest bonusRequest = new BonusRequest(edtReferralCode.getText().toString(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                    queue.add(bonusRequest);
                    btnApply.setEnabled(true);
                } else {
                    Toast.makeText(ApplyJobActivity.this, "Select Payment Method first", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioId = rgPayMethod.getCheckedRadioButtonId();
                ApplyJobRequest request = null;

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.length() != 0) {
                                Toast.makeText(ApplyJobActivity.this, "Apply Successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            else {
                                Toast.makeText(ApplyJobActivity.this, "Apply Failed",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        catch (JSONException e) {
                            Toast.makeText(ApplyJobActivity.this, "Apply Failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                };
                if(selectedRadioId == R.id.rb_bank) {
                    request = new ApplyJobRequest(String.valueOf(jobID), String.valueOf(jobseekerID), responseListener);
                    RequestQueue q = Volley.newRequestQueue(ApplyJobActivity.this);
                    q.add(request);
                }

                else if(selectedRadioId == R.id.rb_ewallet) {
                    request = new ApplyJobRequest(String.valueOf(jobID), String.valueOf(jobseekerID), edtReferralCode.getText().toString(), responseListener);
                    RequestQueue q = Volley.newRequestQueue(ApplyJobActivity.this);
                    q.add(request);
                }

                finish();
            }
        });
    }
}