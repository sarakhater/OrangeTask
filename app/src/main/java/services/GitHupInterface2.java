package services;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import models.UserResponce;
import retrofit2.http.GET;

/**
 * Created by Sara on 3/15/17.
 */

public interface GitHupInterface2 {


    @GET("/getUser")
    public Observable<JsonObject> getOrangeUser();

    @GET("/getLocation")
    public Observable<JsonObject> getLocation();


    @GET("/getUserList")
    public Observable<JsonObject>getUserList();
}
