package com.haidarh.jwork_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etName = findViewById(R.id.et_name);
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);

        Button btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (name.isEmpty()){
                    etName.setError("Name cannot be empty.");
                } else if (!email.matches("^[\\w&*_~]+(\\.?[\\w&*_~]+)*@[^-][\\w\\-\\.]+$")) {
                    etEmail.setError("Email format incorrect");
                } else if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}")){
                    etPassword.setError("Password format incorrect");
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject != null)
                                {
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                                    dialogBuilder.setMessage("Register Successful!");
                                    dialogBuilder.setTitle("Success");
                                    dialogBuilder.setCancelable(false);
                                    dialogBuilder.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            });
                                    AlertDialog alert = dialogBuilder.create();
                                    alert.show();
                                }
                            } catch(JSONException e) {
                                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }

            }
        });
    }
}