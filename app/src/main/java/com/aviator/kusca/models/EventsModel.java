package com.aviator.kusca.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aviator on 12/8/2017.
 */
@SuppressWarnings("ALL")
public class EventsModel  implements Parcelable{
    String id,title,pic,story,dh,dp;

    public EventsModel() {
    }

    protected EventsModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        pic = in.readString();
        story = in.readString();
        dh = in.readString();
        dp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(pic);
        dest.writeString(story);
        dest.writeString(dh);
        dest.writeString(dp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EventsModel> CREATOR = new Creator<EventsModel>() {
        @Override
        public EventsModel createFromParcel(Parcel in) {
            return new EventsModel(in);
        }

        @Override
        public EventsModel[] newArray(int size) {
            return new EventsModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
}
