package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sara on 14/03/2017.
 */
public class UserResponce {

    @SerializedName("3id")
    private int user_id;
    @SerializedName("name")
    private String name  = " ";
    @SerializedName("job_title")
    private String job_title = " ";
    @SerializedName("data")
    private List<User> data;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private  double latitude;

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

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
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
