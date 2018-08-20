package com.aviator.kusca;

import android.os.Bundle;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.aviator.kusca.rec.EmptyRecAdapter;
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

public class KuscaTeam extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private GoogleProgressBar googleProgressBar;
    //empty adp
    CardView cardView;
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kusca_team);
        Init();
    }

    void Init(){
        ((TextView)findViewById(R.id.txtHeda)).setTypeface(EasyFonts.droidSerifBold(KuscaTeam.this));

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(KuscaTeam.this));
        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorLightOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Volley_LoadData();
            }
        });

        googleProgressBar= findViewById(R.id.google_progress);
        cardView=findViewById(R.id.cardEmpty);
        textView=findViewById(R.id.textView);
        imageView=findViewById(R.id.reloadEmpty);
        textView.setTypeface(EasyFonts.caviarDreamsBold(KuscaTeam.this));

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
        Volley_LoadData();
    }

    void Volley_LoadData(){
        googleProgressBar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaTeam.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                swipeRefreshLayout.setRefreshing(false);
                try {
                    JSONArray jsonArray=new JSONArray(s);
                    ArrayList<Kuscateam_model> kuscateam_models=new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                         JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Kuscateam_model kuscateam_model=new Kuscateam_model();
                        kuscateam_model.setName(jsonObject.getString("kusca_NAME"));
                        kuscateam_model.setEmail(jsonObject.getString("kusca_EMAIL"));
                        kuscateam_model.setInfo("");
                        kuscateam_model.setPhoto(jsonObject.getString("kusca_PHOTOURI"));
                        kuscateam_model.setPosition(jsonObject.getString("kusca_POSITION"));
                        kuscateam_models.add(kuscateam_model);
                    }

                    googleProgressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(new KuscateamAdapterHolder(kuscateam_models,KuscaTeam.this));

                } catch (JSONException e) {

                    googleProgressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        cardView.setVisibility(View.VISIBLE);
                        textView.setText(jsonObject.getString("message"));
                       //recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,jsonObject.getString("message")));
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
                swipeRefreshLayout.setRefreshing(false);
                cardView.setVisibility(View.VISIBLE);
                textView.setText("Connection Error");
               // recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,"Connection Error"));
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("action","getleaders");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }

}
