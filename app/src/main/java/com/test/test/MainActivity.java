package com.test.test;

import android.database.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.DataAdapter;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import models.MyModel;
import models.User;
import models.UserResponce;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import services.GitHubInterface;
import services.GitHupInterface2;
import services.RequestInterface;

import static android.support.v7.recyclerview.R.styleable.RecyclerView;


public class MainActivity extends AppCompatActivity {


    public static final String BASE_URL = "http://demo1043736.mockable.io/";

    private RecyclerView mRecyclerView;

    private CompositeDisposable mCompositeDisposable;

    private DataAdapter mAdapter;

    private ArrayList<UserResponce> mAndroidArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCompositeDisposable = new CompositeDisposable();
        initRecyclerView();
       // loadJSON();
        doSomeWork();
    }


    private void doSomeWork() {


        Retrofit repo = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        io.reactivex.Observable<JsonObject> userObservable = repo
                .create(GitHubInterface.class)
                .getOrangeUser()
                .subscribeOn(Schedulers.newThread());

        io.reactivex.Observable<JsonObject> locationObservable = repo
                .create(GitHupInterface2.class)
                .getLocation()
                .subscribeOn(Schedulers.newThread());


        io.reactivex.Observable.zip(userObservable, locationObservable,
                new BiFunction<JsonObject, JsonObject, ArrayList<JsonObject> >() {
                    @Override
                    public ArrayList<JsonObject> apply(JsonObject cricketFans, JsonObject footballFans) throws Exception {
                        ArrayList<JsonObject> x = new ArrayList<>();
                        x.add(cricketFans);
                        x.add(footballFans);
                        return x;
                    }
                })
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread

                .observeOn(Schedulers.newThread())
                .subscribe(getObserver());
    }


    private Observer<ArrayList<JsonObject> > getObserver() {
        return new Observer<ArrayList<JsonObject>>() {


            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("value is==>  onSubscribe");

            }

            @Override
            public void onNext(ArrayList<JsonObject> value) {
                System.out.println("value is==>  "+value.get(0) + "  " +  value.get(1));
            }


            @Override
            public void onError(Throwable e) {
                System.out.println("value is==>  onError");
            }

            @Override
            public void onComplete() {
                System.out.println("value is==>  onComplete");

            }
        };}


//
//    private void fetchMultipleReq() {
//
//        private Observable<String> externalCall(String url, int delayMilliseconds) {
//            return Observable.create(
//                    subscriber -> {
//                        try {
//                            Thread.sleep(delayMilliseconds); //simulate long operation
//                            subscriber.onNext("response(" + url + ") ");
//
//                        } catch (InterruptedException e) {
//                            subscriber.onError(e);
//                        }
//                    }
//            );
//        }
//    }

    private void initRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

//    private void loadJSON() {
//
//        RequestInterface requestInterface = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(RequestInterface.class);
//
//        mCompositeDisposable.add(requestInterface.getUserList()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse,this::handleError));
//    }

    private void handleResponse(UserResponce androidList) {
        System.out.println("list" + androidList.getData().get(0).getName() +"     "+ androidList.getData().get(0).getUser_id());


//        mAndroidArrayList = new ArrayList<>(androidList);
//        mAdapter = new DataAdapter(mAndroidArrayList);
//        mRecyclerView.setAdapter(mAdapter);
    }

    private void handleError(Throwable error) {

        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        System.out.println("Error "+error.getLocalizedMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
