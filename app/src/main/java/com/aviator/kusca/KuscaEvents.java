package com.aviator.kusca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.models.EventsModel;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.prefs.MyPreferences;
import com.aviator.kusca.rec.EventsAdapter;
import com.aviator.kusca.volley.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@SuppressWarnings("ALL")
public class KuscaEvents extends AppCompatActivity {
    private BottomSheetBehavior bottomSheetBehavior;
    private GoogleProgressBar googleProgressBar;
    ExpandableListView expandableListView;

    private GoogleProgressBar googleProgressBar2;
    //empty adp
    CardView cardView;
    TextView textView;
    ImageView imageView;
    TextView textViewBottomSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kusca_events);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        collapsingToolbarLayout.setTitleEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Init();
    }


    void Init(){
        expandableListView=findViewById(R.id.expanded_list);
        googleProgressBar= (GoogleProgressBar) findViewById(R.id.google_progress);
        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        bottomSheetBehavior=BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        textViewBottomSheet= (TextView) findViewById(R.id.fullscreen);
        textViewBottomSheet.setTypeface(EasyFonts.caviarDreamsBold(KuscaEvents.this));

        googleProgressBar2= findViewById(R.id.google_progress2);
        cardView=findViewById(R.id.cardEmpty);
        textView=findViewById(R.id.textView);
        imageView=findViewById(R.id.reloadEmpty);
        textView.setTypeface(EasyFonts.caviarDreamsBold(KuscaEvents.this));
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
        googleProgressBar2.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaEvents.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                try {
                    JSONArray jsonArray=new JSONArray(s);
                    Stack<EventsModel> eventsModels=new Stack<>();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        EventsModel model=new EventsModel();
                        model.setTitle(jsonObject.getString("event_Tittle"));
                        model.setStory(jsonObject.getString("event_Story"));
                        model.setPic(jsonObject.getString("event_Pic"));
                        model.setDh(jsonObject.getString("event_DateHappening"));
                        model.setDp(jsonObject.getString("event_datePosted"));
                        model.setId(jsonObject.getString("event_ID"));
                        eventsModels.add(model);

                    }

                    expandableListView.setAdapter(new EventsAdapter(KuscaEvents.this,eventsModels));
                    googleProgressBar2.setVisibility(View.GONE);

                } catch (JSONException e) {

                    googleProgressBar2.setVisibility(View.GONE);
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
                googleProgressBar2.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                textView.setText("Connection Error");
                // recyclerView.setAdapter(new EmptyRecAdapter(KuscaTeam.this,"Connection Error"));
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("action","getevents");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.kusca_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){

            case R.id.action_agm:{
                startActivity(new Intent(KuscaEvents.this,ListActivity.class));
                return true;
            }
            case R.id.action_trips:{

                return true;
            }
            case R.id.action_elections:{
                //if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
                //   bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                // }else {
                ChooserDialog();
                // }

                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }


    private void CheckVoterStatus(){
        final String email=new MyPreferences(KuscaEvents.this).getKucsa_EMAIL();
        if(email==null || email.isEmpty()){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            googleProgressBar.setVisibility(View.GONE);
            textViewBottomSheet.setText("Kindly login");
            return;
        }

        googleProgressBar.setVisibility(View.VISIBLE);
        textViewBottomSheet.setText("");
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaEvents.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();

                if(s!=null && !s.isEmpty()){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        String message=jsonObject.getString("message");

                        if(message.equalsIgnoreCase("Not Voted")){
                            googleProgressBar.setVisibility(View.GONE);
                            textViewBottomSheet.setText("");
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                            startActivity(new Intent(KuscaEvents.this,KuscaElection.class));
                        }else{
                            googleProgressBar.setVisibility(View.GONE);
                            textViewBottomSheet.setText(message);
                        }

                        // TastyToast.makeText(KuscaEvents.this,message,TastyToast.LENGTH_LONG,TastyToast.ERROR);

                    } catch (JSONException e) {
                        googleProgressBar.setVisibility(View.GONE);
                        textViewBottomSheet.setText("Error encountered,try again");
                        //TastyToast.makeText(KuscaEvents.this,e.getMessage(),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                requestQueue.stop();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                googleProgressBar.setVisibility(View.GONE);
                textViewBottomSheet.setText("Connection error");
                // TastyToast.makeText(context,"Connection error",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("action","voting_status");
                params.put("email",email);
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }

    private void ChooserDialog(){

        TextView view=new TextView(KuscaEvents.this);
        view.setTypeface(EasyFonts.caviarDreamsBold(KuscaEvents.this));
        view.setText("Hello,\nWhat do you want to do?");
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        AlertDialog.Builder aBuilder=new AlertDialog.Builder(KuscaEvents.this)
                .setCancelable(false).setView(view);

        AlertDialog dialog=aBuilder.create();
        dialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE,"Vote", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                CheckVoterStatus();
            }
        });
        dialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "Request candidature", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               CheckIfMember();
            }
        });
        dialog.show();
    }

    private void CheckIfMember(){
        final ProgressDialog progressDialog=new ProgressDialog(KuscaEvents.this);
        progressDialog.setMessage("checking membership...");
        progressDialog.show();
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaEvents.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject=new JSONObject(s);
                    if(jsonObject.getString("error").equalsIgnoreCase("false")){
                        GetAllPosition();
                    }else{
                        TastyToast.makeText(KuscaEvents.this,"Kindly confirm membership",TastyToast.LENGTH_LONG,TastyToast.DEFAULT);

                    }
                } catch (JSONException e) {
                    TastyToast.makeText(KuscaEvents.this,"Failed,try again",TastyToast.LENGTH_LONG,TastyToast.ERROR);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                requestQueue.stop();
                progressDialog.dismiss();
                TastyToast.makeText(KuscaEvents.this,"Connection error",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("action","checkifmember");
                params.put("email",new MyPreferences(KuscaEvents.this).getKucsa_EMAIL());
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    private void GetAllPosition(){
        final ProgressDialog progressDialog=new ProgressDialog(KuscaEvents.this);
        progressDialog.setMessage("Retrieving available positions");
        progressDialog.show();
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaEvents.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();


                if(s!=null && !s.isEmpty()){

                    try {
                        JSONArray jsonArray=new JSONArray(s);
                        ArrayList<String> arrayList=new ArrayList<>();

                        for (int i = 0; i <jsonArray.length() ; i++) {
                            String pos=((JSONObject)jsonArray.get(i)).getString("posname");
                            arrayList.add(pos);
                        }

                        final String[] seats=new String[arrayList.size()];
                        for (int i = 0; i < arrayList.size(); i++) {
                            seats[i]=arrayList.get(i);
                        }


                        progressDialog.dismiss();
                        if(seats!=null){
                            AlertDialog.Builder aBuilder=new AlertDialog.Builder(KuscaEvents.this)
                                    .setTitle("Select Position")
                                    .setCancelable(true)
                                    .setSingleChoiceItems(seats, android.R.layout.simple_list_item_single_choice, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            String posNa=seats[which];
                                            RequestCandidature(posNa);

                                        }
                                    }).setNeutralButton("dismiss", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            aBuilder.show();
                        }




                    } catch (JSONException e1) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String message=jsonObject.getString("message");

                            TastyToast.makeText(KuscaEvents.this,message,TastyToast.LENGTH_LONG,TastyToast.ERROR);

                        } catch (JSONException e) {
                            TastyToast.makeText(KuscaEvents.this,e.getMessage(),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        }
                    }
                    return;
                }
                progressDialog.dismiss();
                TastyToast.makeText(KuscaEvents.this,"No position available",TastyToast.LENGTH_LONG,TastyToast.DEFAULT);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                requestQueue.stop();
                progressDialog.dismiss();
                TastyToast.makeText(KuscaEvents.this,"Connection error",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("action","getallseats");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    private void RequestCandidature(final String position){
        final String email=new MyPreferences(KuscaEvents.this).getKucsa_EMAIL();
        if(email==null || email.isEmpty()){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            googleProgressBar.setVisibility(View.GONE);
            textViewBottomSheet.setText("Kindly login");
            return;
        }

        googleProgressBar.setVisibility(View.VISIBLE);
        textViewBottomSheet.setText("");
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaEvents.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();

                if(s!=null && !s.isEmpty()){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        //String error=jsonObject.getString("error");
                        String message=jsonObject.getString("message");
                        googleProgressBar.setVisibility(View.GONE);
                        textViewBottomSheet.setText(message);
                        // TastyToast.makeText(KuscaEvents.this,message,TastyToast.LENGTH_LONG,TastyToast.ERROR);

                    } catch (JSONException e) {
                        googleProgressBar.setVisibility(View.GONE);
                        textViewBottomSheet.setText("Error encountered,try again");
                        //TastyToast.makeText(KuscaEvents.this,e.getMessage(),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                requestQueue.stop();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                googleProgressBar.setVisibility(View.GONE);
                textViewBottomSheet.setText("Connection error");
                // TastyToast.makeText(context,"Connection error",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("action","request_candidature");
                params.put("email",email);
                params.put("position",position);
               // params.put("name",new MyPreferences(KuscaEvents.this).getKucsa_NAME());
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }

}
