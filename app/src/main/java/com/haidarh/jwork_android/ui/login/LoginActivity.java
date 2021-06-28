package com.haidarh.jwork_android.ui.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.haidarh.jwork_android.ui.main.MainActivity;
import com.haidarh.jwork_android.R;
import com.haidarh.jwork_android.ui.register.RegisterActivity;
import com.haidarh.jwork_android.utils.SharedPrefManager;
import com.haidarh.jwork_android.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(this);

        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvRegister = findViewById(R.id.tv_register);
        TextInputLayout layoutPassword = findViewById(R.id.layout_password);

        if (sharedPrefManager.getSPLoginStatus()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty()){
                    etEmail.setError("Email cannot be empty.");
                } else if (password.isEmpty()){
                    etPassword.setError("Password cannot be empty");
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject != null)
                                {
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

                                    sharedPrefManager.saveSPBoolean("sp_login_status", true);
                                    sharedPrefManager.saveSPInt("sp_id", jsonObject.getInt("id"));
                                    sharedPrefManager.saveSPString("sp_name", jsonObject.getString("name"));

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("jobseekerID", jsonObject.getInt("id"));
                                    startActivity(intent);
                                    finish();
                                }
                            } catch(JSONException e) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                                dialogBuilder.setMessage("Wrong email or password inserted.");
                                dialogBuilder.setTitle("Login Failed");
                                dialogBuilder.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                AlertDialog alert = dialogBuilder.create();
                                alert.show();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}