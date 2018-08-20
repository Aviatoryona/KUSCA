package com.aviator.kusca.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aviator on 11/27/2017.
 */

public class Candidate implements Parcelable{
    String ID,NAME,EMAIL,POSITION,PHOTO,positionId;

    public Candidate() {
    }

    protected Candidate(Parcel in) {
        ID = in.readString();
        NAME = in.readString();
        EMAIL = in.readString();
        POSITION = in.readString();
        PHOTO = in.readString();
        positionId = in.readString();
    }

    public static final Creator<Candidate> CREATOR = new Creator<Candidate>() {
        @Override
        public Candidate createFromParcel(Parcel in) {
            return new Candidate(in);
        }

        @Override
        public Candidate[] newArray(int size) {
            return new Candidate[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String POSITION) {
        this.POSITION = POSITION;
    }

    public String getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(String PHOTO) {
        this.PHOTO = PHOTO;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(NAME);
        dest.writeString(EMAIL);
        dest.writeString(POSITION);
        dest.writeString(PHOTO);
        dest.writeString(positionId);
    }
}
