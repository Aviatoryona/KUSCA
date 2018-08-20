package com.aviator.kusca.rec;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.volley.VolleySingleton;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aviator on 12/5/2017.Tra
 */

public abstract class AdminConfirm {

    private String email,position;

    Context context;

    AdminConfirm(Context context) {
        this.context = context;
    }

    public void Send(){
        final RequestQueue requestQueue= VolleySingleton.getInstance(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();

                if(s!=null){
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        TastyToast.makeText(context,jsonObject.getString("message"),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    } catch (JSONException e) {
                        TastyToast.makeText(context,e.getMessage(),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("action","confirm_candidature_req");
                params.put("email",getEmail());
                params.put("position",getPosition());
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();



    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
