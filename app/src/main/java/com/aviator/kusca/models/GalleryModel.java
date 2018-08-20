package com.aviator.kusca.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aviator on 12/8/2017.
 */
@SuppressWarnings("ALL")
public class GalleryModel implements Parcelable{
    String id,picName,dtPosted;


    public GalleryModel() {
    }

    protected GalleryModel(Parcel in) {
        id = in.readString();
        picName = in.readString();
        dtPosted = in.readString();
    }

    public static final Creator<GalleryModel> CREATOR = new Creator<GalleryModel>() {
        @Override
        public GalleryModel createFromParcel(Parcel in) {
            return new GalleryModel(in);
        }

        @Override
        public GalleryModel[] newArray(int size) {
            return new GalleryModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getDtPosted() {
        return dtPosted;
    }

    public void setDtPosted(String dtPosted) {
        this.dtPosted = dtPosted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(picName);
        dest.writeString(dtPosted);
    }
}
