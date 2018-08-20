package com.aviator.kusca;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.models.Kuscateam_model;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.rec.KuscateamAdapterHolder;
import com.aviator.kusca.volley.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KuscaAnnouncements extends AppCompatActivity {

    private GoogleProgressBar googleProgressBar;
    //empty adp
    CardView cardView;
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kusca_announcements);
        ((TextView)findViewById(R.id.holder)).setTypeface(EasyFonts.caviarDreamsBold(KuscaAnnouncements.this));

        googleProgressBar= findViewById(R.id.google_progress);
        cardView=findViewById(R.id.cardEmpty);
        textView=findViewById(R.id.textView);
        imageView=findViewById(R.id.reloadEmpty);
        textView.setTypeface(EasyFonts.caviarDreamsBold(KuscaAnnouncements.this));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volley_LoadData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                googleProgressBar.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                textView.setText("No Annoucements");
            }
        },3000);
    }

    void Volley_LoadData(){
        googleProgressBar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaAnnouncements.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                try {
                    JSONArray jsonArray=new JSONArray(s);
                    googleProgressBar.setVisibility(View.GONE);
                  //  recyclerView.setAdapter(new KuscateamAdapterHolder(kuscateam_models,KuscaTeam.this));

                } catch (JSONException e) {

                    googleProgressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        cardView.setVisibility(View.VISIBLE);
                        textView.setText(jsonObject.getString("message"));
                        // recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,jsonObject.getString("message")));
                    } catch (JSONException e1) {
                        cardView.setVisibility(View.VISIBLE);
                        textView.setText("Error encountered");
                        //recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,"Error encountered"));
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                googleProgressBar.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                textView.setText("Connection Error");
                // recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,"Connection Error"));
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("action","getannouncements");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }
}
