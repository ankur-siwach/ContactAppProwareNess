package test.ankur.com.prowarenessapp.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by ankur.siwach on 5/27/2017.
 */

public class SharedPref {

    public static final String PREF_FILE_NAME = "app_contacts";
    public static final String APP_DELETED_DATA = "deleted_data";

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, 0);
    }

    //Getting and Setting is user exist
    public static Set<String> getAppUserData(Context context) {
        return getPrefs(context).getStringSet(APP_DELETED_DATA, null);
    }

    public static void setAppUserData(Context context, Set<String> list) {
        getPrefs(context).edit().putStringSet(APP_DELETED_DATA, list).commit();
    }
}
