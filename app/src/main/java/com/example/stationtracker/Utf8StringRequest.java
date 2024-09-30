package com.example.stationtracker;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

public class Utf8StringRequest extends StringRequest {

    public Utf8StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            // تبدیل داده‌ها به UTF-8
            parsed = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // اگر UTF-8 پشتیبانی نشود، داده‌ها به شکل پیش‌فرض ISO-8859-1 پردازش می‌شوند
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
