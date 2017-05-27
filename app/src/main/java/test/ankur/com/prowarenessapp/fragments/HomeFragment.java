package test.ankur.com.prowarenessapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test.ankur.com.prowarenessapp.R;
import test.ankur.com.prowarenessapp.Utility.SharedPref;
import test.ankur.com.prowarenessapp.adapters.HomeAdapter;
import test.ankur.com.prowarenessapp.app.PWNApp;
import test.ankur.com.prowarenessapp.constants.StringConstant;
import test.ankur.com.prowarenessapp.models.GetContactModel;
import test.ankur.com.prowarenessapp.models.Result;
import test.ankur.com.prowarenessapp.network.CustomVolleyRequest;

/**
 * Created by ankur.siwach on 5/27/2017.
 */

public class HomeFragment extends Fragment implements Response.Listener, Response.ErrorListener, HomeAdapter.OnUpdateListener {

    private final String TAG = HomeFragment.class.getName();

    private Context mContext;
    private View view;
    private RecyclerView recyclerView;
    private TextView noData;

    private HomeAdapter homeAdapter;
    List<Result> contactData;
    private Set<String> deletedData;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null) {
            view = inflater.inflate(R.layout.home_fragment, container, false);

            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            recyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerView);
            noData = (TextView) view.findViewById(R.id.noData);

        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                callAPI();
            }
        });

        callAPI();

        return view;
    }

    /*Calling API for home Screen data
    * */
    public void callAPI() {

        try {
            Map<String, String> headers = new HashMap<String, String>();
            headers.put(StringConstant.CONTENT_TYPE, StringConstant.CONTENT_TYPE_VALUE);

            Map<String,String> params = new HashMap<String, String>();
            params.put(StringConstant.PARAM_NAME, StringConstant.PARAM_VALUE);

            String url = StringConstant.CONTACT_API;

            CustomVolleyRequest customJSONObjectGetRequest = new CustomVolleyRequest(Request.Method.POST, url, GetContactModel.class, params, this, this, headers);
            Log.d(TAG,customJSONObjectGetRequest.toString());
            PWNApp.getAppInstance().addToRequestQueue(customJSONObjectGetRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i(TAG, error.toString());
    }

    @Override
    public void onResponse(Object response) {

        swipeRefreshLayout.setRefreshing(false);

        GetContactModel getContactModel = (GetContactModel) response;
        deletedData = SharedPref.getAppUserData(mContext);

        if(getContactModel != null) {
            contactData = getContactModel.getResult();

            Collections.sort(contactData, new CustomComparator());

            if(contactData != null && contactData.size() > 0) {
                if(deletedData != null && deletedData.size() > 0 ) {
                    contactData = filterDeletedData();
                }
                populateHomeData(contactData);
            } else {
                recyclerView.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }

        }
    }

    /*Populating data on Home Screen after getting from the API
    * */
    public void populateHomeData(List<Result> contactData) {
        homeAdapter = new HomeAdapter(mContext, contactData);
        homeAdapter.setOnUpdateListner(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onUpdate(int position) {

        deletedData = SharedPref.getAppUserData(mContext);

        if(deletedData != null && deletedData.size() > 0) {
            deletedData.add(contactData.get(position).getUid());
            SharedPref.setAppUserData(mContext, deletedData);
        } else {
            deletedData = new HashSet<String>();
            deletedData.add(contactData.get(position).getUid());
            SharedPref.setAppUserData(mContext, deletedData);
        }

        contactData.remove(position);

        if(contactData != null && contactData.size() < 1) {
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            homeAdapter.notifyDataSetChanged();
        }
    }

    /*Sorting contact data in natural ordering
    * */
    public class CustomComparator implements Comparator<Result> {
        @Override
        public int compare(Result o1, Result o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    /*Deleting saved deleted data, from API data
    * */
    public List<Result> filterDeletedData() {

        Iterator<String> iterator = deletedData.iterator();
        while(iterator.hasNext()) {
            String value = iterator.next();
            for(int i=0; i<contactData.size(); i++) {
                System.out.println("Main data=="+contactData.get(i).getUid());
                if(contactData.get(i).getUid().equalsIgnoreCase(value)) {
                    contactData.remove(i);
                    System.out.println("Data removed=="+contactData.size());
                }
            }
        }
        System.out.println("Size after deletion=="+contactData.size());
        return contactData;
    }
}
