package models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sara on 14/03/2017.
 */
public class User {

//    "3id": 2365,
//            "name" : "Orange User",
//            "job_title": "Software Developer"

    @SerializedName("3id")
    private int user_id;
    @SerializedName("name")
    private String name ;
    @SerializedName("job_title")
    private String job_title;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private  double latitude;

    public User(int user_id, String name, String job_title, double longitude, double latitude) {
        this.user_id = user_id;
        this.name = name;
        this.job_title = job_title;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public User(int user_id, String name) {

        this.user_id = user_id;
        this.name = name;
    }

    public User(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String hob_title) {
        this.job_title = hob_title;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
