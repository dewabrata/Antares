package com.dewabrata.antares;

public class ModelData {
    public ModelData(String temperature, String humidity, String ph, String saklar, int type) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.ph = ph;
        this.saklar = saklar;
        this.type = type;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getSaklar() {
        return saklar;
    }

    public void setSaklar(String saklar) {
        this.saklar = saklar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String temperature;
    private String humidity;
    private String ph;
    private String saklar;
    private int type;
}
