package triangle.triangleapp.persistence.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.PrivateKey;
import java.security.PublicKey;

import triangle.triangleapp.TriangleApplication;
import triangle.triangleapp.persistence.ConnectionCallback;
import triangle.triangleapp.persistence.StreamAdapter;

/**
 * Created by Kevin Ly on 6/16/2017.
 */

public class HttpStream implements StreamAdapter {
    private static final String TAG = "HttpStream";
    private static final String URL = "http://145.49.44.137:9000/api/";
    private RequestQueue mRequestQueue;
    private String id;

    /**
     * Initializing HttpStream
     */
    public HttpStream() {
        mRequestQueue = Volley.newRequestQueue(TriangleApplication.getAppContext());
    }

    @Override
    public void sendPublicKey(@NonNull PublicKey publicKey) {

    }

    @Override
    public void connect(final ConnectionCallback callback) {
        final String completeUrl = URL + "stream/connect";

        StringRequest idRequest = new StringRequest(Request.Method.GET, completeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                id = response.substring(1, response.length() - 1);
                Log.i(TAG, "ID = " + id);
                callback.onConnected();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error getting ID", error);
            }
        });

        mRequestQueue.add(idRequest);
    }

    @Override
    public void sendFile(@NonNull byte[] fileInBytes, @NonNull PrivateKey privateKey) {
        try {
            final String completeUrl = URL + "stream/send/" + id;

            MultipartRequest multipartRequest = new MultipartRequest(completeUrl, null, "application/octet-stream", fileInBytes, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    Log.i(TAG, "Done sending data");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error sending data", error);
                }
            });

            mRequestQueue.add(multipartRequest);


        } catch (Exception ex) {
            Log.e(TAG, "Error occured while send request via Volley", ex);
        }
    }
}
