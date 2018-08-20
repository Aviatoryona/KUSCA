package com.aviator.kusca.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aviator on 11/27/2017.
 */

public class Kuscateam_model implements Parcelable{

    String position,name,info,photo,email;

    public Kuscateam_model() {
    }

    protected Kuscateam_model(Parcel in) {
        position = in.readString();
        name = in.readString();
        info = in.readString();
        photo = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position);
        dest.writeString(name);
        dest.writeString(info);
        dest.writeString(photo);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Kuscateam_model> CREATOR = new Creator<Kuscateam_model>() {
        @Override
        public Kuscateam_model createFromParcel(Parcel in) {
            return new Kuscateam_model(in);
        }

        @Override
        public Kuscateam_model[] newArray(int size) {
            return new Kuscateam_model[size];
        }
    };

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
