package com.example.stationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stationtracker.Model.Station;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;  // تعریف متغیر
    NavigationView navigationView;
    private RecyclerView recyclerView;
    private StationAdapter stationAdapter;
    private List<Station> stationList;
    private RequestQueue requestQueue;
    private static final String URL = "http://172.20.2.26:8000/api/stations/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);

        // تنظیم کردن Listener برای آیتم‌های منو
        navigationView.setNavigationItemSelectedListener(this);

        // اتصال RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // آماده‌سازی داده‌ها
        stationList = new ArrayList<>();
        stationAdapter = new StationAdapter(stationList);

        // اتصال adapter به RecyclerView
        recyclerView.setAdapter(stationAdapter);

        // ایجاد RequestQueue برای Volley
        requestQueue = Volley.newRequestQueue(this);

        // بارگذاری ایستگاه‌ها از سرور
        loadStationsFromServer();
    }

    private void loadStationsFromServer() {
        Utf8StringRequest  stringRequest = new Utf8StringRequest (Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
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
                Log.e("Volley", "Error fetching stations: " + error.getMessage());
            }
        });

        // ارسال درخواست به سرور
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.action_home) {
            Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.action_station) {
            // باز کردن StationActivity
            Intent intentStation = new Intent(MainActivity.this, StationActivity.class);
            startActivity(intentStation);
        }

        // بستن منو بعد از انتخاب آیتم
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
