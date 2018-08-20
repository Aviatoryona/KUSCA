package com.aviator.kusca.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Aviator on 11/23/2017.
 */

public class VolleySingleton {
    static RequestQueue requestQueue;

    public  static RequestQueue getInstance(Context context){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
}
