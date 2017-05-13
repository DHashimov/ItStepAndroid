package demoapp.itsteps.com;

import android.app.Application;
import android.os.AsyncTask;

import demoapp.itsteps.com.db.DatabaseInstance;

/**
 * Created by dhashimov on 5/10/17.
 */

public class DemoApplication extends Application {

    private static DemoApplication self;

    @Override
    public void onCreate() {
        self = this;
        super.onCreate();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatabaseInstance.getInstance(self);
                return null;
            }
        };
    }
}
