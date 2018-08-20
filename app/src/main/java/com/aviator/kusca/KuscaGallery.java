package com.aviator.kusca;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.aviator.kusca.models.GalleryModel;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.rec.EmptyRecAdapter;
import com.aviator.kusca.rec.KuscaGalleryAdapter;
import com.aviator.kusca.volley.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class KuscaGallery extends AppCompatActivity implements OnMoreListener{

    private GoogleProgressBar googleProgressBar;
    //empty adp
    CardView cardView;
    TextView textView;
    ImageView imageView;
    RecyclerView superRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kusca_gallery);

        googleProgressBar= findViewById(R.id.google_progress);
        cardView=findViewById(R.id.cardEmpty);
        textView=findViewById(R.id.textView);
        imageView=findViewById(R.id.reloadEmpty);
        textView.setTypeface(EasyFonts.caviarDreamsBold(KuscaGallery.this));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volley_LoadData();
            }
        });

        superRecyclerView= findViewById(R.id.superRec);
        superRecyclerView.setLayoutManager(new LinearLayoutManager(KuscaGallery.this));



    }


    @Override
    protected void onStart() {
        super.onStart();
        Volley_LoadData();
    }


    void Volley_LoadData(){
        googleProgressBar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaGallery.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                try {
                    JSONArray jsonArray=new JSONArray(s);
                    googleProgressBar.setVisibility(View.GONE);
                    Stack<GalleryModel>  galleryModels=new Stack<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        GalleryModel galleryModel=new GalleryModel();
                        galleryModel.setId(jsonObject.getString("ID"));
                        galleryModel.setPicName(jsonObject.getString("pic_name"));
                        galleryModel.setDtPosted(jsonObject.getString("date_posted"));
                        galleryModels.add(galleryModel);

                    }

                    superRecyclerView.setAdapter(new KuscaGalleryAdapter(galleryModels,KuscaGallery.this));

                } catch (JSONException e) {

                    googleProgressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        cardView.setVisibility(View.VISIBLE);
                        textView.setText(jsonObject.getString("message"));
                     //   superRecyclerView.setAdapter(new EmptyRecAdapter(KuscaGallery.this,jsonObject.getString("message")));
                    } catch (JSONException e1) {
                        cardView.setVisibility(View.VISIBLE);
                        textView.setText("Error encountered");
                //       superRecyclerView.setAdapter(new EmptyRecAdapter(KuscaGallery.this,"Error encountered"));
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
                params.put("action","gallery");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

    }
}
