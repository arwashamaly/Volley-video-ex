package com.example.arwashamaly;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arwashamaly.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
String  url = "https://stgapiphp7.ireadarabic.com/en/Auth/login";
   public static SharedPreferences sharedToken;
   public static SharedPreferences.Editor editToken;
   public String failTokenName ="tokenShared";
   public static String sharedTokenKey = "token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedToken= getSharedPreferences(failTokenName,MODE_PRIVATE);
        editToken = sharedToken.edit();

        if (!sharedToken.getString(sharedTokenKey,"").isEmpty()){
            Intent intent = new Intent(getBaseContext(), SecondActivity.class);
            startActivity(intent);
            finish();
        }

            binding.btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginResponse(binding.etUserName.getText().toString(),
                            binding.etPass.getText().toString());
                }
            });



    }

    private void loginResponse(String userName,String pass) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String token =jsonObject1.getString("token");
                    editToken.putString(sharedTokenKey,token);
                    editToken.commit();
                    Intent intent = new Intent(getBaseContext(),SecondActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("os","IOS");
                map.put("version","1.2");
                map.put("username",userName);
                map.put("password",pass);
                map.put("user_type","student");
                map.put("os_version","1.2");
                map.put("device_token","dasdsa");
                map.put("device_id","fsafasads");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}