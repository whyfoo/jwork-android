package com.haidarh.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshList();

        ExpandableListView lvExp = findViewById(R.id.lvExp);

        lvExp.setAdapter(new MainListAdapter(MainActivity.this, listRecruiter, childMapping));
    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Toast.makeText(MainActivity.this, "respon kena", Toast.LENGTH_SHORT).show();
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i =0; i < jsonResponse.length(); i++) {
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

                            listRecruiter.add(recster);

                            Job jobster = new Job(
                                                job.getInt("id"),
                                                job.getString("name"),
                                                recster,
                                                job.getInt("fee"),
                                                job.getString("category")
                                            );

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
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Load List Failed", Toast.LENGTH_SHORT).show();
                }
            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}