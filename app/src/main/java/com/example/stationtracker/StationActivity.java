package com.example.stationtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
public class StationActivity extends AppCompatActivity {
    EditText edt_title, edt_english_title, edt_line, edt_address, edt_Lat, edt_Lang, edt_Description;
    Button btn_send;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        progressBar = findViewById(R.id.progressbar);
        btn_send = findViewById(R.id.btn_send);
        edt_title = findViewById(R.id.edt_title);
        edt_english_title = findViewById(R.id.edt_english_title);
        edt_line = findViewById(R.id.edt_line);
        edt_address = findViewById(R.id.edt_address);
        edt_Lat = findViewById(R.id.edt_Lat);
        edt_Lang = findViewById(R.id.edt_Lang);
        edt_Description = findViewById(R.id.edt_Description);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("StationActivity", "Send button clicked");

                progressBar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://172.20.2.26:8000/api/stations/";

                Utf8StringRequest request = new Utf8StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("StationActivity", "Response received: " + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("StationActivity", "Error response: " + error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Log.d("StationActivity", "Preparing request parameters");
                        Map<String, String> params = new HashMap<>();
                        params.put("Line", "1");
                        params.put("English_title", "shademan");
                        params.put("Lat", "0");
                        params.put("Description", "شادمان");
                        params.put("Title", "شادمان");
                        params.put("Lang", "0");
                        params.put("Address", "شادمان");
                        return params;
                     /*  Map<String, String> params = new HashMap<>();
                        params.put("Title", edt_title.getText().toString());
                        params.put("English_title", edt_english_title.getText().toString());
                        params.put("Line", edt_line.getText().toString());
                        params.put("Address", edt_address.getText().toString());
                        params.put("Lat", edt_Lat.getText().toString());
                        params.put("Lang", edt_Lang.getText().toString());
                        params.put("Description", edt_Description.getText().toString());
                        Log.d("StationActivity", "Request parameters: " + params.toString());
                        return params;*/
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Log.d("StationActivity", "Setting request headers");
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        return headers;
                    }
                };

                Log.d("StationActivity", "Sending request to server");
                requestQueue.add(request);
            }
        });
    }
}
