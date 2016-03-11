package com.kedark.androidtest.core;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kedark.androidtest.util.CustomException;


import org.json.JSONArray;

/**
 * Created by kedarkarmarkar on 3/10/16.
 */
public class API {
    public final static String API_BASE_URL = " http://express-it.optusnet.com.au/sample.json";
    public final static String TAG="";
    private final static String API_CONTENT_TYPE_JSON = "application/json";


    public static void getData(Context context, final OnDataFetcchedCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest req = new JsonArrayRequest(API_BASE_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                callback.onFailure(new CustomException(error.getMessage()));
            }
        });

// Adding request to request queue
        queue.add(req);;

    }


    public interface OnDataFetcchedCallback {
        void onSuccess(JSONArray successResponse);

        void onFailure(CustomException e);
    }
}
