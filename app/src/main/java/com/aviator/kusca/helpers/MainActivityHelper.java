package com.aviator.kusca.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.Admin;
import com.aviator.kusca.FullscreenActivity;
import com.aviator.kusca.KuscaEvents;
import com.aviator.kusca.MainActivity;
import com.aviator.kusca.models.Member;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.prefs.MyPreferences;
import com.aviator.kusca.volley.VolleySingleton;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aviator on 12/2/2017.
 */

public class MainActivityHelper {
    Context context;
    ProgressDialog progressDialog;

    public MainActivityHelper(Context context) {
        this.context = context;
        progressDialog=new ProgressDialog(this.context);
    }



    public void ChkLognUserAdmin(final String action,String pass){
        progressDialog.setMessage("please wait");
        progressDialog.show();
        final Map<String,String > params=setParams(action,pass);
        if(params==null){
            progressDialog.dismiss();
            TastyToast.makeText(context,"Kindly login ",TastyToast.LENGTH_LONG,TastyToast.INFO);
            return;
        }
        final RequestQueue requestQueue= VolleySingleton.getInstance(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                requestQueue.stop();

                    try {
                        JSONObject userInfo=new JSONObject(s);
                        if(userInfo.getString("error").equals("false")){

                            if(userInfo.getString("message").equalsIgnoreCase("107")){
                                context. startActivity(new Intent(context,Admin.class));
                                return;
                            }

                            final EditText editText=new EditText(context);
                            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());//show
                            //hide
                            //editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editText.setHint("Password");
                            editText.setTypeface(EasyFonts.caviarDreamsBold(context));
                            new AlertDialog.Builder(context).setCancelable(true)
                                    .setView(editText)
                                    .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if(!TextUtils.isEmpty(editText.getText().toString())){
                                                ChkLognUserAdmin("login",editText.getText().toString());
                                            }
                                        }
                                    }).setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();



                        }else{
                            TastyToast.makeText(context,userInfo.getString("message"),TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        }

                    } catch (JSONException e1) {
                        TastyToast.makeText(context,e1.getMessage(),TastyToast.LENGTH_LONG,TastyToast.ERROR);
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


    private Map<String, String> setParams(String action,String password){
        final String email=new MyPreferences(context).getKucsa_EMAIL();
        if(email==null || TextUtils.isEmpty(email)){
            TastyToast.makeText(context,"Kindly login ",TastyToast.LENGTH_LONG,TastyToast.INFO);
            return null;
        }
        Map<String, String> map=new HashMap<>();
        map.put("email",email);
        switch (action){
            case "login":{
               map.put("action","admin_auth");
                map.put("password",password);
                break;
            }

            case "check":{
                map.put("action","checkifadmin");
                break;
            }
        }
        return map;

    }



}
