package com.example.benktesh.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    public int id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;

    private Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public Step() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    public String getStepShortDescription() {
        return "Step " + String.valueOf(id) + ": " + shortDescription;
    }
}
