package demoapp.itsteps.com.webservices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import demoapp.itsteps.com.callbacks.Callback;
import demoapp.itsteps.com.models.UsersResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;


/**
 * Wraps all the web services, away from the UI.
 */
public class Wrapper {
    private static final String CONTENT_TYPE = "application/json";
    @SuppressLint("StaticFieldLeak") //Application context is used.
    private static Wrapper self;
    private Context context;
    private Services services;
    private Retrofit retrofit;
    @SuppressWarnings("FieldCanBeLocal")
    private Gson gson;


    @SuppressLint("SimpleDateFormat")
    private Wrapper() {
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        clientBuilder.addInterceptor(getHttpLoggingInterceptor());
        retrofit = new Retrofit.Builder().baseUrl("https://private-9d496-itstep.apiary-mock.com").
                client(clientBuilder.build()).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        services = retrofit.create(Services.class);
    }

    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("HTTP", message);
                    }
                });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    /**
     * This is the starting point for all Wrapper calls.
     *
     * @param context Application context.
     * @return instance of this Wrapper.
     */
    public static synchronized Wrapper getInstance(Context context) {
        if (self == null) {
            self = new Wrapper();
        }
        if (self.context == null) {
            self.context = context.getApplicationContext();
        }
        return self;
    }


    /**
     * Get the users which are followed by an user.
     */
    public void getUserFollowingList(final Callback<UsersResponse> callback) {
        services.getUserFollowingList(CONTENT_TYPE).enqueue(new retrofit2.Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call,
                                   Response<UsersResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
               callback.onError("Error");
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
               callback.onError("Error");
            }
        });
    }

    /**
     * RetroFit's API description of the Server.
     */
    private interface Services {

        @GET("/itstep/users")
        Call<UsersResponse> getUserFollowingList(@Header("Content-Type") String contentType);

    }
}
