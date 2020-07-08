
package com.dewabrata.antares.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M2mCin implements Serializable, Parcelable
{

    @SerializedName("rn")
    @Expose
    private String rn;
    @SerializedName("ty")
    @Expose
    private Integer ty;
    @SerializedName("ri")
    @Expose
    private String ri;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("ct")
    @Expose
    private String ct;
    @SerializedName("lt")
    @Expose
    private String lt;
    @SerializedName("st")
    @Expose
    private Integer st;
    @SerializedName("cnf")
    @Expose
    private String cnf;
    @SerializedName("cs")
    @Expose
    private Integer cs;
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
    private final static long serialVersionUID = 8580996473001118927L;

    protected M2mCin(Parcel in) {
        this.rn = ((String) in.readValue((String.class.getClassLoader())));
        this.ty = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ri = ((String) in.readValue((String.class.getClassLoader())));
        this.pi = ((String) in.readValue((String.class.getClassLoader())));
        this.ct = ((String) in.readValue((String.class.getClassLoader())));
        this.lt = ((String) in.readValue((String.class.getClassLoader())));
        this.st = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.cnf = ((String) in.readValue((String.class.getClassLoader())));
        this.cs = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.con = ((String) in.readValue((String.class.getClassLoader())));
    }

    public M2mCin() {
    }

    public String getRn() {
        return rn;
    }

    public void setRn(String rn) {
        this.rn = rn;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public String getRi() {
        return ri;
    }

    public void setRi(String ri) {
        this.ri = ri;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public Integer getSt() {
        return st;
    }

    public void setSt(Integer st) {
        this.st = st;
    }

    public String getCnf() {
        return cnf;
    }

    public void setCnf(String cnf) {
        this.cnf = cnf;
    }

    public Integer getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rn);
        dest.writeValue(ty);
        dest.writeValue(ri);
        dest.writeValue(pi);
        dest.writeValue(ct);
        dest.writeValue(lt);
        dest.writeValue(st);
        dest.writeValue(cnf);
        dest.writeValue(cs);
        dest.writeValue(con);
    }

    public int describeContents() {
        return  0;
    }

}
