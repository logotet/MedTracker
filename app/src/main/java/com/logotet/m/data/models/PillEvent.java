package com.logotet.m.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class PillEvent extends EventDay implements Parcelable {

    String name;

    public PillEvent(Calendar day) {
        super(day);
    }

    public PillEvent(Calendar day, int imageResource, String name) {
        super(day, imageResource);
        this.name = name;
    }

    protected PillEvent(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        name = in.readString();
    }

    public static final Creator<PillEvent> CREATOR = new Creator<PillEvent>() {
        @Override
        public PillEvent createFromParcel(Parcel in) {
            return new PillEvent(in);
        }

        @Override
        public PillEvent[] newArray(int size) {
            return new PillEvent[size];
        }
    };

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
