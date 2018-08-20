package com.aviator.kusca.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Stack;

/**
 * Created by Aviator on 11/27/2017.
 */

public class ElectionModel implements Parcelable{
    String position,positionId;
    Stack<Candidate> candidateStack;

    public ElectionModel() {
    }

    protected ElectionModel(Parcel in) {
        position = in.readString();
        positionId = in.readString();
    }

    public static final Creator<ElectionModel> CREATOR = new Creator<ElectionModel>() {
        @Override
        public ElectionModel createFromParcel(Parcel in) {
            return new ElectionModel(in);
        }

        @Override
        public ElectionModel[] newArray(int size) {
            return new ElectionModel[size];
        }
    };

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Stack<Candidate> getCandidateStack() {
        return candidateStack;
    }

    public void setCandidateStack(Stack<Candidate> candidateStack) {
        this.candidateStack = candidateStack;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position);
        dest.writeString(positionId);
    }
}
