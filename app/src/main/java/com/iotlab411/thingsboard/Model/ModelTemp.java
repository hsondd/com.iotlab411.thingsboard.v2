package com.iotlab411.thingsboard.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelTemp {
    @SerializedName("temperature")
    @Expose
    private ArrayList<Temperature> temperature = null;

    @SerializedName("humidity")
    @Expose
    private ArrayList<Humidity> humidity = null;

    @SerializedName("dust")
    @Expose
    private ArrayList<Dust> dust = null;

    @SerializedName("Image")
    @Expose
    private ArrayList<Image> image = null;


    public ArrayList<Dust> getDust() {
        return dust;
    }

    public void setDust(ArrayList<Dust> dust) {
        this.dust = dust;
    }

    public ArrayList<Temperature> getTemperature() {
        return temperature;
    }

    public void setTemperature(ArrayList<Temperature> temperature) {
        this.temperature = temperature;
    }

    public ArrayList<Humidity> getHumidity() {
        return humidity;
    }

    public void setHumidity(ArrayList<Humidity> humidity) {
        this.humidity = humidity;
    }

    public ArrayList<Image> getImage() {
        return image;
    }
    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }


    public static class Humidity implements Parcelable{

        @SerializedName("ts")
        @Expose
        private String ts;
        @SerializedName("value")
        @Expose
        private String value;

        protected Humidity(Parcel in) {
            ts = in.readString();
            value = in.readString();
        }

        public static final Creator<Humidity> CREATOR = new Creator<Humidity>() {
            @Override
            public Humidity createFromParcel(Parcel in) {
                return new Humidity(in);
            }

            @Override
            public Humidity[] newArray(int size) {
                return new Humidity[size];
            }
        };

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(ts);
            parcel.writeString(value);
        }
    }

    public static class Temperature implements Parcelable {

        @SerializedName("ts")
        @Expose
        private String ts;
        @SerializedName("value")
        @Expose
        private String value;

        protected Temperature(Parcel in) {
            ts = in.readString();
            value = in.readString();
        }

        public static final Creator<Temperature> CREATOR = new Creator<Temperature>() {
            @Override
            public Temperature createFromParcel(Parcel in) {
                return new Temperature(in);
            }

            @Override
            public Temperature[] newArray(int size) {
                return new Temperature[size];
            }
        };

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(ts);
            parcel.writeString(value);
        }
    }
    public static class Dust  implements  Parcelable{

        @SerializedName("ts")
        @Expose
        private String ts;
        @SerializedName("value")
        @Expose
        private String value;

        protected Dust(Parcel in) {
            ts = in.readString();
            value = in.readString();
        }

        public static final Creator<Dust> CREATOR = new Creator<Dust>() {
            @Override
            public Dust createFromParcel(Parcel in) {
                return new Dust(in);
            }

            @Override
            public Dust[] newArray(int size) {
                return new Dust[size];
            }
        };

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(ts);
            parcel.writeString(value);
        }
    }

    public static class Image {
        @SerializedName("ts")
        @Expose
        private String ts;
        @SerializedName("value")
        @Expose
        private String value;

        public String getTs() {
            return ts;
        }

        public  void setTs(String ts) {
            this.ts = ts;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
