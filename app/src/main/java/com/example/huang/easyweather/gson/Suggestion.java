package com.example.huang.easyweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2017/4/22.
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public CarWash carWash;
    @SerializedName("drsg")
    public Dress dress;
    @SerializedName("flu")
    public Influenza influenza;
    public Sport sport;
    @SerializedName("trav")
    public Travel travel;
    @SerializedName("uv")
    public Ultraviolet ultraviolet;
    public class Comfort{
        @SerializedName("brf")
        public String brief;
        @SerializedName("txt")
        public String info;
}
    public class CarWash{
        @SerializedName("brf")
        public String brief;
        @SerializedName("txt")
        public String info;
    }
    public class  Dress{
        @SerializedName("brf")
        public String brief;
        @SerializedName("txt")
        public String info;
    }
    public class Influenza{
        @SerializedName("brf")
        public String brief;
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("brf")
        public String brief;
        @SerializedName("txt")
        public String info;
    }
    public class Travel{
        @SerializedName("brf")
        public String brief;
        @SerializedName("txt")
        public String info;
    }
    public class Ultraviolet{
        @SerializedName("brf")
        public String brief;
        @SerializedName("txt")
        public String info;
    }
}
