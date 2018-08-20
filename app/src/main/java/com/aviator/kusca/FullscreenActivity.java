package com.aviator.kusca;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.models.Member;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.prefs.MyPreferences;
import com.aviator.kusca.volley.VolleySingleton;
import com.bumptech.glide.Glide;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;

@SuppressWarnings("ALL")
public class FullscreenActivity extends AppCompatActivity {

    NestedScrollView linearLayout;
    FButton textView;
    EditText editText;
    LinearLayout layoutLinear;
    GoogleProgressBar googleProgressBar,loginProgress;
    Typeface typeface,typeface2,typeface3;
    TextView tx,tx2,kusca;

    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        MyPreferences myPreferences=new MyPreferences(FullscreenActivity.this);
        if(myPreferences.hasEmail()){
            finish();
            startActivity(new Intent(FullscreenActivity.this,MainActivity.class));
        }
        else{
            Init();
        }
    }


    void Init(){
        typeface= Typeface.createFromAsset(getAssets(), "fonts/Android Insomnia Regular.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(), "fonts/glyphicons-halflings-regular.ttf");
        typeface3 = Typeface.createFromAsset(getAssets(), "fonts/youngones_RS-Regular.ttf");

        googleProgressBar= (GoogleProgressBar) findViewById(R.id.google_progress);
        linearLayout= (NestedScrollView) findViewById(R.id.linearLayout);
        layoutLinear= (LinearLayout) findViewById(R.id.lnOut);

        textView= (FButton)findViewById(R.id.btnContinue);
        textView.setTypeface(typeface3);

        editText= (EditText) findViewById(R.id.appCompatEditText);
        //editText.setTypeface(typeface);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText())){
                    editText.setError("Required");
                    return;
                }

                if(!editText.getText().toString().contains("@")){
                    editText.setError("invalid email");
                    return;
                }

                googleProgressBar.setVisibility(View.VISIBLE);
                //write preferences
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CheckUser(editText.getText().toString());
                    }
                },3000);



            }
        });

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle();
            }
        });
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        final RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);

        rotatingTextWrapper.setSize(45);
        rotatingTextWrapper.setTypeface(typeface2);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFFFFF"), 2000, "THE 1", "AFFECTS", "THE WE");//TAKE RESPONSIBILITY,EMBRACE TECHNOLOGY
        rotatable.setSize(22);
        rotatable.setCenter(true);

        rotatable.setAnimationDuration(2000);
        rotatable.setTypeface(typeface);
        rotatable.setInterpolator(new AccelerateDecelerateInterpolator());// BounceInterpolator()
        rotatingTextWrapper.setContent("", rotatable);

        tx= (TextView) findViewById(R.id.xtx);  //Hello
        tx.setTypeface(typeface);
        tx2= (TextView) findViewById(R.id.xtx2); //join us
        tx2.setTypeface(typeface2);

        kusca= (TextView) findViewById(R.id.kusca); //kusca
        kusca.setTypeface(EasyFonts.robotoBold(FullscreenActivity.this));
        Animation animation= AnimationUtils.loadAnimation(FullscreenActivity.this,R.anim.blink);
        kusca.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rotatingTextWrapper.pause(0);
                layoutLinear.setVisibility(View.GONE);
                mContentView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        },10000);
    }


    void NextWindow(String userName, final String email,String photo){
        LayoutInflater layoutInflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.continue_us,null,false);
        setContentView(view);

        loginProgress=view.findViewById(R.id.google_progress);
        TextView txta=view.findViewById(R.id.xtx);
        txta.setText("Hello, "+userName);
        txta.setTypeface(typeface);

        CircleImageView profpic=view.findViewById(R.id.profic_pic);
        Glide.with(FullscreenActivity.this).load(Uri.parse(Constants.MEMBER_PIC_URL+photo)).into(profpic);
        final EditText editTextMm=view.findViewById(R.id.appCompatEditText);
        FButton fButton=view.findViewById(R.id.f_continue_button);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(editTextMm.getText().toString())){
                    editTextMm.setError("password required");
                    return;
                }

                Login(email,editTextMm.getText().toString());

            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }


    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }



    //////////////////////////////////////////////////////////////////////
    void CheckUser(final String user){

        final RequestQueue requestQueue= VolleySingleton.getInstance(FullscreenActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                googleProgressBar.setVisibility(View.GONE);
                requestQueue.stop();
                if(s!=null){

                    try {
                        JSONObject jsonObject=new JSONObject(s);

                        String error=jsonObject.getString("error");
                        String message=jsonObject.getString("message");

                        if(error.equalsIgnoreCase("false")){

                            if(message.equalsIgnoreCase("SUBSCRIBED")){
                                new MyPreferences(FullscreenActivity.this).setKucsa_EMAIL(user);
                                finish();
                                startActivity(new Intent(FullscreenActivity.this,MainActivity.class));

                            }else{
                                String photo=jsonObject.getString("photo");
                                NextWindow(message,user,photo);
                            }

                        }else{
                            TastyToast.makeText(FullscreenActivity.this,message,TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        }

                    } catch (JSONException e) {
                        TastyToast.makeText(FullscreenActivity.this,"Error encountered",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                googleProgressBar.setVisibility(View.GONE);
                requestQueue.stop();
                TastyToast.makeText(FullscreenActivity.this,"Connection Error",TastyToast.LENGTH_LONG,TastyToast.WARNING);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("action","chkuser");
                params.put("username",user);

                return params;
            }
        };


        requestQueue.add(stringRequest);
        requestQueue.start();

    }


    //////////////////////////////////////////////////////////////////////
    void Login(final String email, final String password){
        loginProgress.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(FullscreenActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                loginProgress.setVisibility(View.GONE);
                requestQueue.stop();
                if(s!=null){

                    try {
                        JSONObject jsonObject=new JSONObject(s);

                        String error=jsonObject.getString("error");
                        if(error.equalsIgnoreCase("false")){
                            MyPreferences myPreferences=new MyPreferences(FullscreenActivity.this);
                            myPreferences.setKucsa_EMAIL(email);

                            String user=jsonObject.getString("user");
                            if(user!=null) {
                                JSONObject userInfo = new JSONObject(user);

                                Member member = new Member();
                                member.setID(userInfo.getString("ID"));
                                member.setKucsa_ID(userInfo.getString("kucsa_ID"));
                                member.setKucsa_NAME(userInfo.getString("kucsa_NAME"));
                                member.setKucsa_REGNO(userInfo.getString("kucsa_REGNO"));
                                member.setKucsa_EMAIL(userInfo.getString("kucsa_EMAIL"));
                                member.setKucsa_GENDER(userInfo.getString("kucsa_GENDER"));
                                member.setKucsa_YEAROFSTUDY(userInfo.getString("kucsa_YEAROFSTUDY"));
                                member.setKucsa_COURSE(userInfo.getString("kucsa_COURSE"));
                                member.setKucsa_PHONE1(userInfo.getString("kucsa_PHONE1"));
                                member.setKucsa_PHONE2(userInfo.getString("kucsa_PHONE2"));
                                member.setKucsa_PASSWORD(userInfo.getString("kucsa_PASSWORD"));
                                member.setKucsa_PHOTOURI(userInfo.getString("kucsa_PHOTOURI"));
                                member.setKucsa_DATE(userInfo.getString("kucsa_DATE"));
                                member.setKusca_POSITION(userInfo.getString("kusca_POSITION"));
                                member.setKusca_DATEPOSITIONED(userInfo.getString("kusca_DATEPOSITIONED"));
                                member.setKusca_VOTINGSTATUS(userInfo.getString("kusca_VOTINGSTATUS"));

                                if(myPreferences.WriteMember(member)){
                                    TastyToast.makeText(FullscreenActivity.this,"logged in",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                                }
                            }
                            finish();
                            startActivity(new Intent(FullscreenActivity.this,MainActivity.class));

                        }else if(error.equalsIgnoreCase("true")){
                            TastyToast.makeText(FullscreenActivity.this,"Invalid credentials",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        }
                    } catch (JSONException e) {
                        TastyToast.makeText(FullscreenActivity.this,"Error encountered",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    }

                }else {
                    TastyToast.makeText(FullscreenActivity.this,"Server busy,tru again later",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loginProgress.setVisibility(View.GONE);
                requestQueue.stop();
                TastyToast.makeText(FullscreenActivity.this,"Connection Error",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("action","login");
                params.put("username",email);
                params.put("password",password);

                return params;
            }
        };


        requestQueue.add(stringRequest);
        requestQueue.start();
    }




}
