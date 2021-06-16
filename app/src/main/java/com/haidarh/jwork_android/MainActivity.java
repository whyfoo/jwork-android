package com.haidarh.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    protected ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    protected ArrayList<Job> jobIdList = new ArrayList<>();
    protected HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        int jobseekerID = extras.getInt("jobseekerID");

        refreshList();

        Button btnAppliedJob = findViewById(R.id.btn_applied_job);

        btnAppliedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FinishedJobActivity.class);
                intent.putExtra("jobseekerID", jobseekerID);
                startActivity(intent);
            }
        });
    }

    protected void refreshList() {
        Bundle extras = getIntent().getExtras();
        int jobseekerID = extras.getInt("jobseekerID");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            Location loc = new Location(
                                    location.getString("province"),
                                    location.getString("city"),
                                    location.getString("description"));

                            Recruiter recster =
                                    new Recruiter(
                                            recruiter.getInt("id"),
                                            recruiter.getString("name"),
                                            recruiter.getString("email"),
                                            recruiter.getString("phoneNumber"),
                                            loc);

                            Job jobster = new Job(
                                                job.getInt("id"),
                                                job.getString("name"),
                                                recster,
                                                job.getInt("fee"),
                                                job.getString("category")
                                            );

                            if (listRecruiter.size() > 0) {
                                boolean flag = true;
                                for (Recruiter rec : listRecruiter) {
                                    if (rec.getId() == recster.getId()) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    listRecruiter.add(recster);
                                }
                            } else {
                                listRecruiter.add(recster);
                            }

                            jobIdList.add(jobster);

                        }

                        for (Recruiter rec : listRecruiter) {
                            ArrayList<Job> temp = new ArrayList<>();
                            for (Job job2 : jobIdList) {
                                if(job2.getRecruiter().getName().equals(rec.getName()) ||
                                        job2.getRecruiter().getEmail().equals(rec.getEmail()) ||
                                        job2.getRecruiter().getPhoneNumber().equals(rec.getPhoneNumber()))
                                {
                                    temp.add(job2);
                                }
                            }
                            childMapping.put(rec, temp);
                        }

                        ExpandableListView lvExp = findViewById(R.id.lvExpandable);
                        lvExp.setAdapter(new MainListAdapter(MainActivity.this, listRecruiter, childMapping));

                        lvExp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                Job selectedJob = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);
                                Intent intent = new Intent(MainActivity.this, ApplyJobActivity.class);
                                intent.putExtra("jobseekerID", jobseekerID);
                                intent.putExtra("jobID", selectedJob.getId());
                                intent.putExtra("jobName", selectedJob.getName());
                                intent.putExtra("jobCategory", selectedJob.getCategory());
                                intent.putExtra("jobFee", selectedJob.getFee());
                                startActivity(intent);
                                return true;
                            }
                        });
                    }

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Load List Failed", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}