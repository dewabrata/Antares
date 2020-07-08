
package com.dewabrata.antares.model.post;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AntaresPostData implements Serializable, Parcelable
{

    @SerializedName("m2m:cin")
    @Expose
    private M2mCin m2mCin;
    public final static Creator<AntaresPostData> CREATOR = new Creator<AntaresPostData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AntaresPostData createFromParcel(Parcel in) {
            return new AntaresPostData(in);
        }

        public AntaresPostData[] newArray(int size) {
            return (new AntaresPostData[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6378428658533364033L;

    protected AntaresPostData(Parcel in) {
        this.m2mCin = ((M2mCin) in.readValue((M2mCin.class.getClassLoader())));
    }

    public AntaresPostData() {
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
