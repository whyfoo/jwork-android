package com.haidarh.jwork_android.ui.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.haidarh.jwork_android.R;
import com.haidarh.jwork_android.utils.SharedPrefManager;
import com.haidarh.jwork_android.classes.Job;
import com.haidarh.jwork_android.classes.Location;
import com.haidarh.jwork_android.classes.Recruiter;
import com.haidarh.jwork_android.request.MenuRequest;
import com.haidarh.jwork_android.ui.apply.ApplyJobActivity;
import com.haidarh.jwork_android.ui.finished.FinishedJobActivity;
import com.haidarh.jwork_android.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    protected ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    protected ArrayList<Job> jobIdList = new ArrayList<>();
    protected HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_logout);

        sharedPrefManager = new SharedPrefManager(this);

        int jobseekerID;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobseekerID = extras.getInt("jobseekerID");
        } else {
            jobseekerID = sharedPrefManager.getSPid();
        }

        refreshList();

        Button btnAppliedJob = findViewById(R.id.btn_applied_job);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

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
        int jobseekerID;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobseekerID = extras.getInt("jobseekerId");
        } else {
            jobseekerID = sharedPrefManager.getSPid();
        }

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

                        expandableListView = findViewById(R.id.lvExpandable);
                        expandableListAdapter = new MainExpRecycler(MainActivity.this, listRecruiter, childMapping);
                        expandableListView.setAdapter(expandableListAdapter);
                        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                            int lastExpandedPosition = -1;
                            @Override
                            public void onGroupExpand(int groupPosition) {
                                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
                                    expandableListView.collapseGroup(lastExpandedPosition);
                                }
                                lastExpandedPosition = groupPosition;
                            }
                        });
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
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

//                        ExpandableListView lvExp = findViewById(R.id.lvExpandable);
//                        lvExp.setAdapter(new MainListAdapter(MainActivity.this, listRecruiter, childMapping));
//
//                        lvExp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                            @Override
//                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                                Job selectedJob = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);
//                                Intent intent = new Intent(MainActivity.this, ApplyJobActivity.class);
//                                intent.putExtra("jobseekerID", jobseekerID);
//                                intent.putExtra("jobID", selectedJob.getId());
//                                intent.putExtra("jobName", selectedJob.getName());
//                                intent.putExtra("jobCategory", selectedJob.getCategory());
//                                intent.putExtra("jobFee", selectedJob.getFee());
//                                startActivity(intent);
//                                return true;
//                            }
//                        });
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage("Are you sure to sign out?");
        dialogBuilder.setTitle("Warning");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPrefManager.saveSPBoolean("sp_login_status", false);
                        sharedPrefManager.saveSPInt("sp_id", 0);
                        sharedPrefManager.saveSPString("sp_name", "");
                        startActivity(new Intent(MainActivity.this, LoginActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                });
        dialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alert = dialogBuilder.create();
        alert.show();
        return true;
    }
}