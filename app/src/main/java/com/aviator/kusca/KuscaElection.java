package com.aviator.kusca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.db.DbSingleton;
import com.aviator.kusca.db.MyDb;
import com.aviator.kusca.helpers.KuscaElectionHelper;
import com.aviator.kusca.models.Candidate;
import com.aviator.kusca.models.ElectionModel;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.rec.ElectionRecAdapter;
import com.aviator.kusca.superrec.PaddingItemDecoration;
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
public class KuscaElection extends AppCompatActivity implements KuscaElectionHelper.CloseActivity{

    //  ExpandableListView expandableListView;

    LinearLayout linearLayout;
    GoogleProgressBar googleProgressBar;
    FloatingActionButton fab;
    MyDb myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.election_secondary);

        myDb= DbSingleton.getInstance(KuscaElection.this);

        ((TextView)findViewById(R.id.txtKuscaEvents)).setTypeface(EasyFonts.caviarDreamsBold(KuscaElection.this));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go();
                //  FetchData();
                GetSelectedAll();
            }
        });
        Init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fab.setVisibility(View.GONE);
        FetchData();
    }

    void Init(){

        googleProgressBar= (GoogleProgressBar) findViewById(R.id.google_progress);
        linearLayout= (LinearLayout) findViewById(R.id.container);

        //  expandableListView= (ExpandableListView) findViewById(R.id.expanded_list);
    }

    void AddVIEWS(ArrayList<ElectionModel> electionModels){

        if(electionModels!=null){

            if(!electionModels.isEmpty()){

                for (int i = 0; i < electionModels.size(); i++) {
                    ElectionModel el=electionModels.get(i);
                    View childView= LayoutInflater.from(this).inflate(R.layout.election_secondary_model,null,false);
                    TextView tx=childView.findViewById(R.id.header);
                    tx.setText(el.getPosition());
                    tx.setTypeface(EasyFonts.droidSerifBold(this));


                    //set up db

                    try {
                        myDb.InsertRows(el.getPositionId(),el.getPosition());
                    } catch (Exception ignored) {
                    }


                    RecyclerView rec=childView.findViewById(R.id.recyclerView);
                    rec.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
                    rec.addItemDecoration(new PaddingItemDecoration());
                    rec.setAdapter(new ElectionRecAdapter(el.getCandidateStack(),this));

                    linearLayout.addView(childView);

                }

            }
            googleProgressBar.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }

    }





    /////fetch   data//////

    private void FetchData(){

        googleProgressBar.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(KuscaElection.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                requestQueue.stop();

                if(json!=null && !json.isEmpty()){
                    ParseJson(json);
                }else{
                    ErrorDisplay("Parsing data error");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                googleProgressBar.setVisibility(View.GONE);
                requestQueue.stop();

                ErrorDisplay("Connection error,click to retry");
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("action","all_candidates");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();


    }


    void ErrorDisplay(String message){
        View view=LayoutInflater.from(KuscaElection.this).inflate(R.layout.emptyview,null,false);
        TextView textView=view.findViewById(R.id.textView);
        textView.setText(message);
        textView.setTypeface(EasyFonts.ostrichBold(KuscaElection.this));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeAllViews();
                FetchData();
            }
        });

        linearLayout.addView(view);

    }

    void ParseJson(String json){

        new AsyncTask<String,String,ArrayList<ElectionModel>>(){
            String jsonData;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<ElectionModel> doInBackground(String... params) {
                try {
                    JSONArray jsonArray=new JSONArray(params[0]);
                    ArrayList<ElectionModel> modelArrayList=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ElectionModel electionModel=new ElectionModel();

                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String position=jsonObject.getString("position");
                        String positionId="pos_"+jsonObject.getString("id");

                        JSONArray candidates=jsonObject.getJSONArray("candidates");
                        Stack<Candidate> st=new Stack<>();
                        for (int j = 0; j < candidates.length(); j++) {
                            Candidate ca=new Candidate();
                            JSONObject jsonObject1=candidates.getJSONObject(j);
                            ca.setID(jsonObject1.getString("kucsa_ID"));
                            ca.setNAME(jsonObject1.getString("kucsa_NAME"));
                            ca.setEMAIL(jsonObject1.getString("kucsa_EMAIL"));
                            ca.setPOSITION(jsonObject1.getString("kucsa_POSITION"));
                            ca.setPHOTO(jsonObject1.getString("kucsa_PHOTOURI"));
                            ca.setPositionId(positionId);
                            st.add(ca);
                        }

                        electionModel.setPosition(position);//position name eg President etc
                        electionModel.setPositionId(positionId);   //position id eg 0,1,2,3..
                        electionModel.setCandidateStack(st);
                        modelArrayList.add(electionModel);
                    }
                    return modelArrayList;
                } catch (JSONException e) {
                    jsonData=params[0];
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<ElectionModel> aVoid) {
                super.onPostExecute(aVoid);
                if(aVoid!=null) {
                    AddVIEWS(aVoid);
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        String message = jsonObject.getString("message");
                        TastyToast.makeText(KuscaElection.this, message, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    } catch (JSONException e1) {
                        TastyToast.makeText(KuscaElection.this, "Error encountered", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                }
            }
        }.execute(json);

    }



    void Go(){
        new AsyncTask<Void,String,ArrayList<ElectionModel>>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<ElectionModel> doInBackground(Void... params) {
                ArrayList<ElectionModel> arrayList=getAdapter();
                if(arrayList!=null){

                    if(!arrayList.isEmpty()){
                        return arrayList;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<ElectionModel> aVoid) {
                super.onPostExecute(aVoid);
                if(aVoid!=null){
                    AddVIEWS(aVoid);
                    //   expandableListView.setAdapter(new ElectionAdapter(aVoid,KuscaElection.this));
                }else{
                    TastyToast.makeText(KuscaElection.this,"Error encountered",TastyToast.LENGTH_LONG,TastyToast.CONFUSING);
                }
            }
        }.execute();

    }
    private ArrayList<ElectionModel> getAdapter(){

        try {
            ArrayList<ElectionModel> modelArrayList=new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                ElectionModel electionModel=new ElectionModel();
                electionModel.setPosition(i+"");
                Stack<Candidate> st=new Stack<>();
                for (int j = 0; j < 6; j++) {

                    Candidate ca=new Candidate();
                    ca.setID(j+"");
                    ca.setEMAIL(j+"@"+j+".com");
                    ca.setNAME("Juu Naa");
                    ca.setPOSITION(i+"");
                    ca.setPHOTO("ryan.jpg");

                    st.add(ca);
                }

                electionModel.setCandidateStack(st);
                modelArrayList.add(electionModel);

            }


            return modelArrayList;
        } catch (Exception e) {
            return null;
        }

    }

   void GetSelectedAll(){
        new AsyncTask<Void,Void,Stack<Candidate>>(){
            ProgressDialog progressDialog;
            boolean error;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(KuscaElection.this);
                progressDialog.setMessage("please wait");
                progressDialog.show();

                error=false;
            }

            @Override
            protected Stack<Candidate> doInBackground(Void... params) {
                Cursor cursor=myDb.GetData();
                Stack<Candidate> candidates=new Stack<>();
                if(cursor!=null && cursor.getCount()>0){
                    while (cursor.moveToNext()){
                        Candidate ca=new Candidate();
                        ca.setPositionId(cursor.getString(1));//pos_1,pos_2 etc
                        ca.setPOSITION(cursor.getString(2)); //president etc
                        ca.setID(cursor.getString(3));
                        ca.setNAME(cursor.getString(4));
                        if(cursor.getString(5).equalsIgnoreCase(cursor.getString(2))){
                            error=true;
                            break;
                        }
                        if(!cursor.getString(5).trim().contains("@")) {
                            error=true;
                            break;
                        }   //NOT EMAIL,MEANS NO SELECTION MADE.dISMISS
                        ca.setEMAIL(cursor.getString(5));
                        ca.setPHOTO(cursor.getString(6));
                        candidates.add(ca);
                    }
                }
                if(error){
                    return null;
                }
                return candidates;
            }

            @Override
            protected void onPostExecute(Stack<Candidate> candidates) {
                super.onPostExecute(candidates);
                progressDialog.dismiss();
                if(candidates!=null){

                    if(candidates.isEmpty()){
                        TastyToast.makeText(KuscaElection.this,"You have not made selections",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                        return;
                    }

                    View view=LayoutInflater.from(KuscaElection.this).inflate(R.layout.confirm_selection,null,false);
                    RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(KuscaElection.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new PaddingItemDecoration());
                    recyclerView.setAdapter(new ElectionRecAdapter(candidates,KuscaElection.this));

                    AlertDialog.Builder abBuilder=new AlertDialog.Builder(KuscaElection.this);
                    abBuilder.setTitle("Confirm selection");
                    abBuilder.setCancelable(true);
                    abBuilder.setView(view);
                    abBuilder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new KuscaElectionHelper(KuscaElection.this,KuscaElection.this).SubmitVote();
                            //TastyToast.makeText(KuscaElection.this,"Submitted",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            TastyToast.makeText(KuscaElection.this,"cancelled",TastyToast.LENGTH_LONG,TastyToast.DEFAULT);
                        }
                    });
                    abBuilder.show();

                }else{
                    TastyToast.makeText(KuscaElection.this,"Selection incomplete",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }
            }
        }.execute();

    }

    public void CloseWin(){
        finish();
    }

    @Override
    public void doClose() {
        finish();
    }
}
