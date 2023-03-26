package com.example.arwashamaly;

import static com.example.arwashamaly.MainActivity.editToken;
import static com.example.arwashamaly.MainActivity.sharedToken;
import static com.example.arwashamaly.MainActivity.sharedTokenKey;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.arwashamaly.databinding.ActivityScondBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {
ActivityScondBinding binding;
String url = "https://stgapiphp7.ireadarabic.com/en/Video/videosList?category_id=1";
String urlDelete = "https://stgapi.ireadarabic.com/en/Auth/logout";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityScondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        getVideosList();

    }

    private void logout() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                urlDelete, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                editToken.putString(sharedTokenKey,"");
                editToken.commit();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getVideosList() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("data");

                    ArrayList<Video> videoArrayList=new ArrayList<>();

                    for (int i = 0; i <jsonArray.length() ; i++) {
                        String video = jsonArray.get(i).toString();
                        JSONObject jsonObject =new JSONObject(video);

                        String video_id=jsonObject.getString("video_id");
                        String discription=jsonObject.getString("discription");
                        String video_cover_image=jsonObject.getString("video_image_url");

                        videoArrayList.add(new Video(video_id,discription,video_cover_image));

                        VideoAdapter adapter =new VideoAdapter(videoArrayList,getBaseContext());
                        binding.rc.setAdapter(adapter);
                        binding.rc.setLayoutManager(new LinearLayoutManager(getBaseContext(),
                                RecyclerView.VERTICAL,false));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedToken.getString(sharedTokenKey,"");
                Map<String, String> map =new HashMap<>();
                map.put("token",token);
                return map;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}