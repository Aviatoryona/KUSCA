package com.aviator.kusca.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aviator on 11/27/2017.
 */

public class Member implements Parcelable{
    String ID,kucsa_NAME,kucsa_REGNO,kucsa_ID,kucsa_EMAIL,kucsa_GENDER,kucsa_YEAROFSTUDY,kucsa_COURSE,kucsa_PHONE1,kucsa_PHONE2,kucsa_PASSWORD,kucsa_PHOTOURI,kucsa_DATE,kusca_POSITION,kusca_DATEPOSITIONED,kusca_VOTINGSTATUS;

    public Member() {
    }

    protected Member(Parcel in) {
        ID = in.readString();
        kucsa_NAME = in.readString();
        kucsa_REGNO = in.readString();
        kucsa_ID = in.readString();
        kucsa_EMAIL = in.readString();
        kucsa_GENDER = in.readString();
        kucsa_YEAROFSTUDY = in.readString();
        kucsa_COURSE = in.readString();
        kucsa_PHONE1 = in.readString();
        kucsa_PHONE2 = in.readString();
        kucsa_PASSWORD = in.readString();
        kucsa_PHOTOURI = in.readString();
        kucsa_DATE = in.readString();
        kusca_POSITION = in.readString();
        kusca_DATEPOSITIONED = in.readString();
        kusca_VOTINGSTATUS = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getKucsa_NAME() {
        return kucsa_NAME;
    }

    public void setKucsa_NAME(String kucsa_NAME) {
        this.kucsa_NAME = kucsa_NAME;
    }

    public String getKucsa_REGNO() {
        return kucsa_REGNO;
    }

    public void setKucsa_REGNO(String kucsa_REGNO) {
        this.kucsa_REGNO = kucsa_REGNO;
    }

    public String getKucsa_ID() {
        return kucsa_ID;
    }

    public void setKucsa_ID(String kucsa_ID) {
        this.kucsa_ID = kucsa_ID;
    }

    public String getKucsa_EMAIL() {
        return kucsa_EMAIL;
    }

    public void setKucsa_EMAIL(String kucsa_EMAIL) {
        this.kucsa_EMAIL = kucsa_EMAIL;
    }

    public String getKucsa_GENDER() {
        return kucsa_GENDER;
    }

    public void setKucsa_GENDER(String kucsa_GENDER) {
        this.kucsa_GENDER = kucsa_GENDER;
    }

    public String getKucsa_YEAROFSTUDY() {
        return kucsa_YEAROFSTUDY;
    }

    public void setKucsa_YEAROFSTUDY(String kucsa_YEAROFSTUDY) {
        this.kucsa_YEAROFSTUDY = kucsa_YEAROFSTUDY;
    }

    public String getKucsa_COURSE() {
        return kucsa_COURSE;
    }

    public void setKucsa_COURSE(String kucsa_COURSE) {
        this.kucsa_COURSE = kucsa_COURSE;
    }

    public String getKucsa_PHONE1() {
        return kucsa_PHONE1;
    }

    public void setKucsa_PHONE1(String kucsa_PHONE1) {
        this.kucsa_PHONE1 = kucsa_PHONE1;
    }

    public String getKucsa_PHONE2() {
        return kucsa_PHONE2;
    }

    public void setKucsa_PHONE2(String kucsa_PHONE2) {
        this.kucsa_PHONE2 = kucsa_PHONE2;
    }

    public String getKucsa_PASSWORD() {
        return kucsa_PASSWORD;
    }

    public void setKucsa_PASSWORD(String kucsa_PASSWORD) {
        this.kucsa_PASSWORD = kucsa_PASSWORD;
    }

    public String getKucsa_PHOTOURI() {
        return kucsa_PHOTOURI;
    }

    public void setKucsa_PHOTOURI(String kucsa_PHOTOURI) {
        this.kucsa_PHOTOURI = kucsa_PHOTOURI;
    }

    public String getKucsa_DATE() {
        return kucsa_DATE;
    }

    public void setKucsa_DATE(String kucsa_DATE) {
        this.kucsa_DATE = kucsa_DATE;
    }

    public String getKusca_POSITION() {
        return kusca_POSITION;
    }

    public void setKusca_POSITION(String kusca_POSITION) {
        this.kusca_POSITION = kusca_POSITION;
    }

    public String getKusca_DATEPOSITIONED() {
        return kusca_DATEPOSITIONED;
    }

    public void setKusca_DATEPOSITIONED(String kusca_DATEPOSITIONED) {
        this.kusca_DATEPOSITIONED = kusca_DATEPOSITIONED;
    }

    public String getKusca_VOTINGSTATUS() {
        return kusca_VOTINGSTATUS;
    }

    public void setKusca_VOTINGSTATUS(String kusca_VOTINGSTATUS) {
        this.kusca_VOTINGSTATUS = kusca_VOTINGSTATUS;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(kucsa_NAME);
        dest.writeString(kucsa_REGNO);
        dest.writeString(kucsa_ID);
        dest.writeString(kucsa_EMAIL);
        dest.writeString(kucsa_GENDER);
        dest.writeString(kucsa_YEAROFSTUDY);
        dest.writeString(kucsa_COURSE);
        dest.writeString(kucsa_PHONE1);
        dest.writeString(kucsa_PHONE2);
        dest.writeString(kucsa_PASSWORD);
        dest.writeString(kucsa_PHOTOURI);
        dest.writeString(kucsa_DATE);
        dest.writeString(kusca_POSITION);
        dest.writeString(kusca_DATEPOSITIONED);
        dest.writeString(kusca_VOTINGSTATUS);
    }
}
