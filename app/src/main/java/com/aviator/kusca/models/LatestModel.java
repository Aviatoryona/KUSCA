package com.aviator.kusca.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aviator on 12/7/2017.
 */
@SuppressWarnings("ALL")
public class LatestModel implements Parcelable {
    String id,pic,title,body,date;

    public LatestModel() {
    }


    protected LatestModel(Parcel in) {
        id = in.readString();
        pic = in.readString();
        title = in.readString();
        body = in.readString();
        date = in.readString();
    }

    public static final Creator<LatestModel> CREATOR = new Creator<LatestModel>() {
        @Override
        public LatestModel createFromParcel(Parcel in) {
            return new LatestModel(in);
        }

        @Override
        public LatestModel[] newArray(int size) {
            return new LatestModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pic);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(date);
    }
}
