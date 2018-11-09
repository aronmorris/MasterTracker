package com.example.anthony.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class PassParcelable implements Parcelable {

    private int mData;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<PassParcelable> CREATOR
            = new Parcelable.Creator<PassParcelable>() {
        public PassParcelable createFromParcel(Parcel in) {
            return new PassParcelable(in);
        }

        public PassParcelable[] newArray(int size) {
            return new PassParcelable[size];
        }
    };

    private PassParcelable(Parcel in) {
        mData = in.readInt();
    }

}
