package test.ankur.com.prowarenessapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.ankur.com.prowarenessapp.R;
import test.ankur.com.prowarenessapp.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadHomeFragment();
    }

    /*Adding home fragment to main activity
    * */
    public void loadHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, homeFragment, homeFragment.getClass().getName()).commit();

    }

}
