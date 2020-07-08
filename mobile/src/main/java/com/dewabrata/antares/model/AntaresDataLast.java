
package com.dewabrata.antares.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AntaresDataLast implements Serializable, Parcelable
{

    @SerializedName("m2m:cin")
    @Expose
    private M2mCin m2mCin;
    public final static Creator<AntaresDataLast> CREATOR = new Creator<AntaresDataLast>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AntaresDataLast createFromParcel(Parcel in) {
            return new AntaresDataLast(in);
        }

        public AntaresDataLast[] newArray(int size) {
            return (new AntaresDataLast[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8260473785661409842L;

    protected AntaresDataLast(Parcel in) {
        this.m2mCin = ((M2mCin) in.readValue((M2mCin.class.getClassLoader())));
    }

    public AntaresDataLast() {
    }

    public M2mCin getM2mCin() {
        return m2mCin;
    }

    public void setM2mCin(M2mCin m2mCin) {
        this.m2mCin = m2mCin;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(m2mCin);
    }

    public int describeContents() {
        return  0;
    }

}
