package com.example.stationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stationtracker.Model.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StationAdapter stationAdapter;
    private List<Station> stationList;
    private ProgressBar progressBar;
    private Button btnAddStation;
    private RequestQueue requestQueue;
    private static final String URL = "http://172.20.2.26:8000/api/stations/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // اتصال ویجت‌ها
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        btnAddStation = findViewById(R.id.btnAddStation);

        // تنظیم RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // آماده‌سازی داده‌ها
        stationList = new ArrayList<>();
        stationAdapter = new StationAdapter(stationList);
        recyclerView.setAdapter(stationAdapter);

        // ایجاد RequestQueue برای Volley
        requestQueue = Volley.newRequestQueue(this);

        // بارگذاری ایستگاه‌ها از سرور
        loadStationsFromServer();

        // رویداد کلیک دکمه "Add Station"
        btnAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // باز کردن StationActivity برای اضافه کردن ایستگاه جدید
                Intent intentStation = new Intent(MainActivity.this, StationActivity.class);
                startActivity(intentStation);
            }
        });
    }

    // متد برای واکشی داده‌های ایستگاه‌ها از سرور
    private void loadStationsFromServer() {
        // نمایش ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        Utf8StringRequest stringRequest = new Utf8StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);  // مخفی کردن ProgressBar پس از بارگذاری
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    stationList.clear();  // پاکسازی لیست قبلی
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject stationObject = jsonArray.getJSONObject(i);

                        // استخراج اطلاعات هر ایستگاه
                        Station station = new Station(
                                stationObject.getInt("id"),
                                stationObject.getString("Title"),
                                stationObject.getString("Line"),
                                stationObject.getString("Address")
                        );

                        // اضافه کردن ایستگاه به لیست
                        stationList.add(station);
                    }

                    // به‌روزرسانی آداپتر
                    stationAdapter.notifyDataSetChanged();
                    Log.d("Stations", "Loaded " + stationList.size() + " stations.");
                } catch (JSONException e) {
                    Log.e("Volley", "JSON Parsing error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);  // مخفی کردن ProgressBar در صورت خطا
                Log.e("Volley", "Error fetching stations: " + error.getMessage());
            }
        });

        // ارسال درخواست به سرور
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // به روز رسانی لیست ایستگاه‌ها پس از بازگشت به MainActivity
        loadStationsFromServer();
    }
}
