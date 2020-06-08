package com.shubzz.wire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText email, password, uq_key;


    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_uq = "key";
    private static SessionHandler session;
    private String login_url = "http://192.168.43.98/wire/clogin.php";  // child app

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initGUI();
        session = new SessionHandler(getApplicationContext());
        if (session.isLoggedIn()) {
            loadMainactivity();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temail = email.getText().toString().trim();
                String tpass = password.getText().toString().trim();
                String tkey = uq_key.getText().toString().trim();
                if (validateInput(temail, tpass, tkey)) {
                    signUser(temail, tpass, tkey);
                }
                //startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

        uq_key.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (uq_key.getRight() - uq_key.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Toast.makeText(getApplicationContext(), "Really", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
                return false;
            }

        });


    }


    private void signUser(final String temail, String tpass, final String tkey) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, temail);
            request.put(KEY_PASSWORD, tpass);
            request.put(KEY_uq, tkey);
            Log.d("Request", request.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt(KEY_STATUS) == 0) {
                        session.loginUser(temail, response.getString(KEY_FULL_NAME), tkey);
                        loadMainactivity();
                    } else {
                        Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSONERROR", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private boolean validateInput(String temail, String tpass, String tkey) {
        if (temail.isEmpty()) {
            email.setError("Email field is empty.");
            return false;
        }
        if (tpass.isEmpty()) {
            password.setError("Password is empty.");
            return false;
        }
        if (tkey.isEmpty()) {
            password.setError("Key is empty.");
            return false;
        }
        return true;
    }


    private void initGUI() {
        login = findViewById(R.id.log);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        uq_key = findViewById(R.id.uq_key);
    }


    private void loadMainactivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
