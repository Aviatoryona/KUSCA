package com.aviator.kusca.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.aviator.kusca.models.Member;

/**
 * Created by Aviator on 11/29/2017.
 */
@SuppressWarnings("ALL")
public  class MyPreferences {

  String ID="ID",
          kucsa_NAME="kucsa_NAME",
          kucsa_REGNO="kucsa_REGNO",
          kucsa_ID="kucsa_ID",
          kucsa_EMAIL="kucsa_EMAIL",
          kucsa_GENDER="kucsa_GENDER",
          kucsa_YEAROFSTUDY="kucsa_YEAROFSTUDY",
          kucsa_COURSE="kucsa_COURSE",
          kucsa_PHONE1="kucsa_PHONE1",
          kucsa_PHONE2="kucsa_PHONE2",
          kucsa_PASSWORD="kucsa_PASSWORD",
          kucsa_PHOTOURI="kucsa_PHOTOURI",
          kucsa_DATE="kucsa_DATE",
          kusca_POSITION="kusca_POSITION",
          kusca_DATEPOSITIONED="kusca_DATEPOSITIONED",
          kusca_VOTINGSTATUS="kusca_VOTINGSTATUS";

    SharedPreferences sharedPreferences;
    final String PREF_NAME="KUSCA_PREFS";
    final int MODE=Context.MODE_PRIVATE;
    Context context;


    public MyPreferences(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,MODE);
    }


    public boolean WriteMember(Member member){

        if(member!=null){

          SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(ID,member.getID());
            editor.putString(kucsa_ID,member.getKucsa_ID());
            editor.putString(kucsa_NAME,member.getKucsa_NAME());
            editor.putString(kucsa_REGNO,member.getKucsa_REGNO());
            editor.putString(kucsa_EMAIL,member.getKucsa_EMAIL());
            editor.putString(kucsa_GENDER,member.getKucsa_GENDER());
            editor.putString(kucsa_YEAROFSTUDY,member.getKucsa_YEAROFSTUDY());
            editor.putString(kucsa_COURSE,member.getKucsa_COURSE());
            editor.putString(kucsa_PHONE1,member.getKucsa_PHONE1());
            editor.putString(kucsa_PHONE2,member.getKucsa_PHONE2());
            editor.putString(kucsa_PASSWORD,member.getKucsa_PASSWORD());
            editor.putString(kucsa_PHOTOURI,member.getKucsa_PHOTOURI());
            editor.putString(kucsa_DATE,member.getKucsa_DATE());
            editor.putString(kusca_POSITION,member.getKusca_POSITION());
            editor.putString(kusca_DATEPOSITIONED,member.getKusca_DATEPOSITIONED());
            editor.putString(kusca_VOTINGSTATUS,member.getKusca_VOTINGSTATUS());

            editor.apply();
            return true;
        }

        return false;

    }


    public Member getMember(){
        Member member=new Member();
        member.setID(sharedPreferences.getString(ID,""));
        member.setKucsa_ID(sharedPreferences.getString(kucsa_ID,""));
        member.setKucsa_NAME(sharedPreferences.getString(kucsa_NAME,""));
        member.setKucsa_REGNO(sharedPreferences.getString(kucsa_REGNO,""));
        member.setKucsa_EMAIL(sharedPreferences.getString(kucsa_EMAIL,""));
        member.setKucsa_GENDER(sharedPreferences.getString(kucsa_GENDER,""));
        member.setKucsa_YEAROFSTUDY(sharedPreferences.getString(kucsa_YEAROFSTUDY,""));
        member.setKucsa_COURSE(sharedPreferences.getString(kucsa_COURSE,""));
        member.setKucsa_PHONE1(sharedPreferences.getString(kucsa_PHONE1,""));
        member.setKucsa_PHONE2(sharedPreferences.getString(kucsa_PHONE2,""));
        member.setKucsa_PASSWORD(sharedPreferences.getString(kucsa_PASSWORD,""));
        member.setKucsa_PHOTOURI(sharedPreferences.getString(kucsa_PHOTOURI,""));
        member.setKucsa_DATE(sharedPreferences.getString(kucsa_DATE,""));
        member.setKusca_POSITION(sharedPreferences.getString(kusca_POSITION,""));
        member.setKusca_DATEPOSITIONED(sharedPreferences.getString(kusca_DATEPOSITIONED,""));
        member.setKusca_VOTINGSTATUS(sharedPreferences.getString(kusca_VOTINGSTATUS,""));
        return member;
    }


    public String getKucsa_EMAIL() {
        return sharedPreferences.getString(kucsa_EMAIL,"");
    }

    public boolean setKucsa_EMAIL(String EMAIL) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(kucsa_EMAIL,EMAIL);
        editor.apply();
        return true;
    }

    public boolean hasEmail(){
        return sharedPreferences.contains(kucsa_EMAIL);
    }

    public String getID() {
        return sharedPreferences.getString(ID,"");
    }

    public String getKucsa_NAME() {
        return sharedPreferences.getString(kucsa_NAME,"");
    }

    public String getKucsa_REGNO() {
        return sharedPreferences.getString(kucsa_REGNO,"");
    }

    public String getKucsa_ID() {
        return sharedPreferences.getString(kucsa_ID,"");
    }

    public String getKucsa_GENDER() {
        return sharedPreferences.getString(kucsa_GENDER,"");
    }

    public String getKucsa_YEAROFSTUDY() {
        return sharedPreferences.getString(kucsa_YEAROFSTUDY,"");
    }

    public String getKucsa_COURSE() {
        return sharedPreferences.getString(kucsa_COURSE,"");
    }

    public String getKucsa_PHONE1() {
        return sharedPreferences.getString(kucsa_PHONE1,"");
    }

    public String getKucsa_PHONE2() {
        return sharedPreferences.getString(kucsa_PHONE2,"");
    }

    public String getKucsa_PASSWORD() {
        return sharedPreferences.getString(kucsa_PASSWORD,"");
    }

    public String getKucsa_PHOTOURI() {
        return sharedPreferences.getString(kucsa_PHOTOURI,"");
    }

    public String getKucsa_DATE() {
        return sharedPreferences.getString(kucsa_DATE,"");
    }

    public String getKusca_POSITION() {
        return sharedPreferences.getString(kusca_POSITION,"");
    }

    public String getKusca_DATEPOSITIONED() {
        return sharedPreferences.getString(kusca_DATEPOSITIONED,"");
    }

    public String getKusca_VOTINGSTATUS() {
        return sharedPreferences.getString(kusca_VOTINGSTATUS,"");
    }

    public boolean Reset(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
