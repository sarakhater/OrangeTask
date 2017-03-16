package models;

import com.google.gson.JsonObject;

/**
 * Created by Sara on 3/15/17.
 */

public class MyModel {

    public MyModel(JsonObject user, JsonObject events) {
        this.events = events;
        this.user = user;
    }

    public JsonObject events;
    public JsonObject user;
}
