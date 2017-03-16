package com.test.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import models.UserResponce;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.GitHupInterface2;


public class MainActivity extends AppCompatActivity {

    //Define variables:

    private ProgressBar progressBar;
    private TextView txt_user_details, txt_location, txt_list_user;
    public static final String BASE_URL = "http://demo1043736.mockable.io/";
    private CompositeDisposable mCompositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViews:
        findViews();

        //calling 3 api:
        FetchMultipleRequest();
    }

    private void findViews() {
        mCompositeDisposable = new CompositeDisposable();
        progressBar = (ProgressBar) findViewById(R.id.progressDialog);
        txt_user_details = (TextView) findViewById(R.id.txt_user_detail_id);
        txt_location = (TextView) findViewById(R.id.GetLocation_id);
        txt_list_user = (TextView) findViewById(R.id.GetListOfUser_id);

    }


    private void FetchMultipleRequest() {


        Retrofit repo = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        io.reactivex.Observable<JsonObject> userObservable = repo
                .create(GitHupInterface2.class)
                .getOrangeUser()
                .subscribeOn(Schedulers.newThread());

        io.reactivex.Observable<JsonObject> locationObservable = repo
                .create(GitHupInterface2.class)
                .getLocation()
                .subscribeOn(Schedulers.newThread());

        io.reactivex.Observable<JsonObject> listOfUserObservable = repo
                .create(GitHupInterface2.class)
                .getUserList()
                .subscribeOn(Schedulers.newThread());


        io.reactivex.Observable.zip(userObservable, locationObservable, listOfUserObservable,
                new Function3<JsonObject, JsonObject, JsonObject, ArrayList<JsonObject>>() {
                    @Override
                    public ArrayList<JsonObject> apply(JsonObject userObj, JsonObject locObj, JsonObject listUserObj) throws Exception {
                        ArrayList<JsonObject> x = new ArrayList<>();
                        x.add(userObj);
                        x.add(locObj);
                        x.add(listUserObj);
                        return x;
                    }
                })
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread

                .observeOn(Schedulers.newThread())
                .subscribe(getObserver());
    }


    private Observer<ArrayList<JsonObject>> getObserver() {
        return new Observer<ArrayList<JsonObject>>() {


            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("value is==>  onSubscribe");

            }

            @Override
            public void onNext(final ArrayList<JsonObject> value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progressBar.setVisibility(View.GONE);

                        showResult(value);

                    }
                });

                System.out.println("value is==>  " + value.get(0) + "  " + value.get(1) + "  " + value.get(2));
            }


            @Override
            public void onError(Throwable e) {
                System.out.println("value is==>  onError");
            }

            @Override
            public void onComplete() {
                System.out.println("value is==>  onComplete");

            }
        };
    }

    private void showResult(ArrayList<JsonObject> value) {

        Gson gson = new Gson();
        UserResponce json = gson.fromJson(value.get(0), UserResponce.class);
        String name = json.getName();
        int user_id = json.getUser_id();
        String job_Title = json.getJob_title();
        txt_user_details.setText("3id ==> " + user_id + " & name ==>  " + name + "& job ==> " + job_Title);

        UserResponce json2 = gson.fromJson(value.get(1), UserResponce.class);
        String lat = "", lang = "";
        for (int i = 0; i < json2.getData().size(); i++) {
            lat += String.valueOf(json2.getData().get(i).getLatitude()) + "  &   ";
            lang += String.valueOf(json2.getData().get(i).getLongitude()) + "\n ";
            txt_location.setText("Latitude  ==> " + lat + " & Longitude ==>  " + lang);
        }

        UserResponce json3 = gson.fromJson(value.get(2), UserResponce.class);
        String list_name = " ", list_user_id = " ";

        for (int j = 0; j < json3.getData().size(); j++) {
            list_user_id += String.valueOf(json3.getData().get(j).getUser_id()) + " & ";
            list_name += json3.getData().get(j).getName() + " \n ";
            txt_list_user.setText("3id ==> " + list_user_id + " & name ==>  " + list_name);
        }




    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
