package com.aviator.kusca.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.KuscaElection;
import com.aviator.kusca.db.DbSingleton;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.prefs.MyPreferences;
import com.aviator.kusca.volley.VolleySingleton;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aviator on 11/29/2017.
 */
@SuppressWarnings("ALL")
public class KuscaElectionHelper {
    Context context;
    KuscaElection kuscaElection;
    ProgressDialog progressDialog;

    public KuscaElectionHelper(Context context, KuscaElection kuscaElection) {
        this.context = context;
        this.kuscaElection = kuscaElection;
        this.progressDialog = new ProgressDialog(this.context);
    }

    public void SubmitVote(){
        progressDialog.setMessage("submitting vote...");
        progressDialog.show();
        final Map<String,String > params=setParams();
        if(params==null){
            progressDialog.dismiss();
            return;
        }
        final RequestQueue requestQueue= VolleySingleton.getInstance(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                requestQueue.stop();

                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String message=jsonObject.getString("message");

                   if(jsonObject.getString("error").equalsIgnoreCase("false")){
                       TastyToast.makeText(context,message,TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                       //finish activity
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               kuscaElection.doClose();
                           }
                       },2000);
                   }
                    TastyToast.makeText(context,message,TastyToast.LENGTH_LONG,TastyToast.DEFAULT);
                } catch (JSONException e) {
                    TastyToast.makeText(context,"Sorry,please try again",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
             progressDialog.dismiss();
                requestQueue.stop();
                TastyToast.makeText(context,"Connection error",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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



    private Map<String,String> setParams(){

        String email=new MyPreferences(context).getKucsa_EMAIL();
        if(email!=null || !email.isEmpty()){
            Map<String,String> params=new HashMap<>();
            params.put("action","kusca_vote");
            params.put("email",email);

            Cursor cursor= DbSingleton.getInstance(context).GetData();
            if(cursor!=null && cursor.getCount()>0){
                while (cursor.moveToNext()){
                    //eg params.put("pos_1","kusca@ksu.ac.ke");
                    params.put(cursor.getString(1),cursor.getString(5));  //args pos_id,candidate_email
                }
            }
            return params;
        }
        else{
            TastyToast.makeText(context,"Kindly login ",TastyToast.LENGTH_LONG,TastyToast.INFO);
            return null;
        }
    }


    public interface CloseActivity{
        void doClose();
    }

}
