
package com.dewabrata.antares.model.post;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M2mCin implements Serializable, Parcelable
{

    @SerializedName("con")
    @Expose
    private String con;
    public final static Creator<M2mCin> CREATOR = new Creator<M2mCin>() {


        @SuppressWarnings({
            "unchecked"
        })
        public M2mCin createFromParcel(Parcel in) {
            return new M2mCin(in);
        }

        public M2mCin[] newArray(int size) {
            return (new M2mCin[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8419413522837910234L;

    protected M2mCin(Parcel in) {
        this.con = ((String) in.readValue((String.class.getClassLoader())));
    }

    public M2mCin() {
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(con);
    }

    public int describeContents() {
        return  0;
    }

}
