package test.ankur.com.prowarenessapp.app;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class PWNApp extends Application {

    private static PWNApp mAppInstance;

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public static PWNApp getAppInstance() {
        return mAppInstance;
    }

    public static Context getContext() {
        return mAppInstance.getApplicationContext();
    }
}
