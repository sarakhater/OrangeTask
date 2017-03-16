package services;

/**
 * Created by Sara on 3/15/17.
 */



import java.util.List;

import io.reactivex.Observable;
import models.Android;
import models.UserResponce;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("android/jsonarray/")
    Observable<List<Android>> register();

    @GET("/getUser")
    public Observable<UserResponce>getOrangeUser();

    @GET("/getLocation")
    public Observable<UserResponce>getLocation();

    @GET("/getUserList")
    public Observable<UserResponce>getUserList();
}
