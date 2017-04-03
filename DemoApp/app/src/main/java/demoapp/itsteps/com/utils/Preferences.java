package demoapp.itsteps.com.utils;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper for Android Preferences which provides a fluent interface.
 *
 * @author Evgeny Shishkin
 */
public class Preferences {
    static final String TAG = "Prefs";
    private final String SET_VALUES = "String Set";

    static Preferences singleton = null;

    private SharedPreferences preferences;


    private Preferences(Context context) {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);

    }


    public Set<String> getStringSet(String key, Set<String> defValues) {
        return preferences.getStringSet(key, defValues);
    }


    public static Preferences getInstance(Context context) {
        if (singleton == null) {
            singleton = new Preferences(context);
        }
        return singleton;
    }

    public void setStringSet(Set<String> values) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(SET_VALUES);
        editor.apply();
        editor.putStringSet(SET_VALUES, values);
        editor.apply();
        editor.commit();
    }

    public Set<String> getStringSet(){
         return preferences.getStringSet(SET_VALUES , new HashSet<String>());
    }


}