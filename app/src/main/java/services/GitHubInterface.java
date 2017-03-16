package services;


import com.google.gson.JsonObject;


import io.reactivex.Observable;
import models.UserResponce;
import retrofit2.http.GET;

/**
 * Created by Sara on 3/15/17.
 */

public interface GitHubInterface {

    @GET("/getUser")
    public Observable<JsonObject> getOrangeUser();


}
