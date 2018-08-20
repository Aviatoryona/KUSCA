package com.aviator.kusca.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aviator.kusca.models.Candidate;


/**
 * Created by Aviator on 11/29/2017.
 */

public class MyDb extends SQLiteOpenHelper {

    private static final String dbName="kusca.db";
    private static final  String tbName="kusca_table";

    //columns
    public static final String  COL_1="ID";//0
    private static final String COL_2="POS_ID";//1
    private static final String COL_3="POS_NAME";//2
    private static final String COL_4="CANDIDATE_ID";  //3
    private static final String COL_5="CANDIDATE_NAME";//4
    private static final String COL_6="CANDIDATE_EMAIL";//5
    private static final String COL_7="CANDIDATE_PHOTO";//6



    public MyDb(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+tbName+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,POS_ID TEXT UNIQUE NOT NULL,POS_NAME TEXT ,CANDIDATE_ID TEXT ,CANDIDATE_NAME TEXT ,CANDIDATE_EMAIL TEXT , CANDIDATE_PHOTO TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tbName);
    }

    public void ResetTb(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+tbName);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+tbName+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,POS_ID TEXT UNIQUE NOT NULL,POS_NAME TEXT ,CANDIDATE_ID TEXT ,CANDIDATE_NAME TEXT ,CANDIDATE_EMAIL TEXT , CANDIDATE_PHOTO TEXT)");


    }

    public boolean InsertRows(String pid,String pname){

        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,pid);
        contentValues.put(COL_3,pname);
        contentValues.put(COL_4,pname);
        contentValues.put(COL_5,pname);
        contentValues.put(COL_6,pname);
        contentValues.put(COL_7,pname);

       long result= database.insert(tbName,null,contentValues);

        return result!=-1;

    }

      public boolean UpdateData(String pid, Candidate candidate){

          SQLiteDatabase database=this.getWritableDatabase();
          if(candidate!=null) {
              ContentValues contentValues = new ContentValues();
              //contentValues.put(COL_2, pid);
             // contentValues.put(COL_3, pname);
              contentValues.put(COL_4, candidate.getID());
              contentValues.put(COL_5, candidate.getNAME());
              contentValues.put(COL_6, candidate.getEMAIL());
              contentValues.put(COL_7, candidate.getPHOTO());

              int result=database.update(tbName,contentValues,"POS_ID =?",new String[]{pid});

              return  result>0;

          }else {
              return false;
          }

      }

      public Cursor GetData(){
          SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
          Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+tbName,null);
          return cursor;
      }


}
