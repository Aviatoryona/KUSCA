package com.aviator.kusca.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.KuscaGallery;
import com.aviator.kusca.models.GalleryModel;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.rec.KuscaGalleryAdapter;
import com.aviator.kusca.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Aviator on 12/12/2017.
 */

public class KuscaGalleryHelper {
    RecyclerView recyclerView;
    Context context;


    public KuscaGalleryHelper(RecyclerView recyclerView, Context context) {
        this.recyclerView = recyclerView;
        this.context = context;
    }




    public void Volley_LoadData(){
//        googleProgressBar.setVisibility(View.VISIBLE);
//        cardView.setVisibility(View.GONE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                try {
                    JSONArray jsonArray=new JSONArray(s);
//                    googleProgressBar.setVisibility(View.GONE);
                    Stack<GalleryModel> galleryModels=new Stack<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        GalleryModel galleryModel=new GalleryModel();
                        galleryModel.setId(jsonObject.getString("ID"));
                        galleryModel.setPicName(jsonObject.getString("pic_name"));
                        galleryModel.setDtPosted(jsonObject.getString("date_posted"));
                        galleryModels.add(galleryModel);

                    }

                    recyclerView.setAdapter(new KuscaGalleryAdapter(galleryModels,context));

                } catch (JSONException e) {

//                    googleProgressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject=new JSONObject(s);
//                        cardView.setVisibility(View.VISIBLE);
//                        textView.setText(jsonObject.getString("message"));
                        // recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,jsonObject.getString("message")));
                    } catch (JSONException e1) {
//                        cardView.setVisibility(View.VISIBLE);
//                        textView.setText("Error encountered");
                        //recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,"Error encountered"));
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
//                googleProgressBar.setVisibility(View.GONE);
//                cardView.setVisibility(View.VISIBLE);
//                textView.setText("Connection Error");
                // recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,"Connection Error"));
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("action","gallery");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }
}
