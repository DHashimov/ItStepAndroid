package demoapp.itsteps.com.singletons;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by dhashimov on 3/30/17.
 */

public class PicassoInstance {
    private static Picasso instance;

    public static Picasso get(Context context) {
        if (instance == null) {
            OkHttpClient client = new OkHttpClient();
            instance =
                    new Picasso.Builder(context).downloader(new OkHttp3Downloader(client)).build();
        }
        return instance;
    }
}
