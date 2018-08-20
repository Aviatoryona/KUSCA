package com.aviator.kusca.frags;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.R;
import com.aviator.kusca.models.Candidate;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.rec.AdminElectionAdapter;
import com.aviator.kusca.rec.EmptyRecAdapter;
import com.aviator.kusca.volley.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


/**
 * A simple {@link Fragment} subclass.
 */
public class Elections extends Fragment implements View.OnClickListener{

    GoogleProgressBar googleProgressBar;
    RecyclerView recyclerView;
    Button button;
    LinearLayout linearLayout;
    TextView textView;
    public Elections() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View evt=inflater.inflate(R.layout.fragment_elections, container, false);
        // ((TextView)evt.findViewById(R.id.holder)).setTypeface(EasyFonts.caviarDreamsBold(getContext()));
        Init(evt);
        return  evt;

    }



    void  Init(View v){


        button=v.findViewById(R.id.dummy_button);
        button.setTypeface(EasyFonts.caviarDreamsBold(getContext()));
        button.setOnClickListener(this);
        googleProgressBar=v.findViewById(R.id.google_progress);
        recyclerView=v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        linearLayout= v.findViewById(R.id.fullscreen_content_controls);
        textView=v.findViewById(R.id.textView);
        textView.setTypeface(EasyFonts.caviarDreamsBold(getContext()));

    }


    @Override
    public void onStart() {
        super.onStart();
        ChkSession("","");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadCandidatureReqs();
            }
        },1500);
    }

    private void ChkSession(String action, String hrs){

        googleProgressBar.setVisibility(View.VISIBLE);
        final Map<String,String> params=setParams(action,hrs);
        if(params==null || params.isEmpty()){
            TastyToast.makeText(getContext(),"Could not complete request",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            return;
        }

        final RequestQueue requestQueue= VolleySingleton.getInstance(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                requestQueue.stop();
                googleProgressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject=new JSONObject(json);
                    if(jsonObject.getString("error").equalsIgnoreCase("false")){

                        if(jsonObject.getString("message").equalsIgnoreCase("closed")){
                            button.setText("Open Votting Session");
                            return;
                        }
                        if(jsonObject.getString("message").equalsIgnoreCase("open")){
                            button.setText("Close Votting Session");
                            return;
                        }

                        if(jsonObject.getString("message").equalsIgnoreCase("Voting Session Opened")){
                            TastyToast.makeText(getContext(),jsonObject.getString("message_evt")+"\n"+jsonObject.getString("message"),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                            button.setText("Close Votting Session");
                            return;
                        }

                        if(jsonObject.getString("message").equalsIgnoreCase("Voting Session Closed")){
                            TastyToast.makeText(getContext(),jsonObject.getString("message"),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                            button.setText("Open Votting Session");
                            return;
                        }

                        TastyToast.makeText(getContext(),jsonObject.getString("message"),TastyToast.LENGTH_LONG,TastyToast.ERROR);

                    }else{
                        TastyToast.makeText(getContext(),jsonObject.getString("message"),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    }

                } catch (JSONException e) {
                    TastyToast.makeText(getContext(),"Error encountere,try again",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                googleProgressBar.setVisibility(View.GONE);
                requestQueue.stop();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();


    }



    private void LoadCandidatureReqs(){

        googleProgressBar.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                requestQueue.stop();
                googleProgressBar.setVisibility(View.GONE);

                if((json != null && !json.isEmpty())) {
                    try {
                        JSONArray jsonArray=new JSONArray(json);
                        Stack<Candidate> st=new Stack<>();
                        for (int j = 0; j < jsonArray.length(); j++) {
                            Candidate ca=new Candidate();
                            JSONObject jsonObject1=jsonArray.getJSONObject(j);
                            ca.setID(jsonObject1.getString("cr_ID"));
                            ca.setNAME(jsonObject1.getString("cr_NAME"));
                            ca.setEMAIL(jsonObject1.getString("cr_EMAIL"));
                            ca.setPOSITION(jsonObject1.getString("cr_POSITION"));
                            ca.setPHOTO(jsonObject1.getString("cr_PHOTO"));
                            st.add(ca);
                        }

                        if(!st.isEmpty()){
                            textView.setVisibility(View.GONE);
                            recyclerView.setAdapter(new AdminElectionAdapter(st,getContext()));
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e1) {
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("No requests");

                        //recyclerView.setAdapter(new EmptyRecAdapter(getContext(),"No requests"));
                           // TastyToast.makeText(getContext(), e1.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }
                }else{

                    recyclerView.setAdapter(new EmptyRecAdapter(getContext(),"No requests"));
                    //no reqss
                  //TastyToast.makeText(getContext(),"No requests", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                googleProgressBar.setVisibility(View.GONE);
                requestQueue.stop();
                TastyToast.makeText(getContext(),"Connection error", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("action","candidature_reqs");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.dummy_button){

            if(button.getText().toString().contains("Open")){
                //  open session
                final String[] hrs=new String[73];
                for (int i = 0; i < 73; i++) {
                    hrs[i]=String.valueOf(i);
                }

                new AlertDialog.Builder(getContext()).setTitle("Time in Hrs")
                        .setCancelable(true)
                        .setSingleChoiceItems(hrs, android.R.layout.simple_list_item_single_choice, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ChkSession("open",hrs[which]);
                            }
                        }).show();

            }
            else if(button.getText().toString().contains("Close")){
                //close session
                ChkSession("close","");
            }else{
                //check session again
                ChkSession("","");
            }

        }
    }

    private Map<String,String> setParams(String action,String hrs){
        Map<String,String> paStringStringMap=new HashMap<>();

        switch (action){

            case "open":{

                paStringStringMap.put("action","openvoting_session");
                paStringStringMap.put("duration",hrs);
                break;
            }

            case "close":{

                paStringStringMap.put("action","closevoting_session");
                break;
            }

            default:{
                paStringStringMap.put("action","checksession");
            }

        }


        return paStringStringMap;
    }
}
