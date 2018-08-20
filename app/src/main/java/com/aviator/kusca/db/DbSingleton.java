package com.aviator.kusca.db;

import android.content.Context;

/**
 * Created by Aviator on 11/29/2017.
 */

public class DbSingleton {
    static MyDb myDb;

    public static MyDb getInstance(Context context){
        if(myDb==null){
            myDb=new MyDb(context);
        }

        return myDb;
    }

}
