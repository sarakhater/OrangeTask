package services;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Sara on 3/15/17.
 */

public interface GitHupInterface2 {

    @GET("/getLocation")
    public Observable<JsonObject> getLocation();
}
