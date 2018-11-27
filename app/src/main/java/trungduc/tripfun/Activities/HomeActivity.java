package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import trungduc.tripfun.Fragments.UserInfoFragment;
import trungduc.tripfun.Fragments.ManagerTripFragment;
import trungduc.tripfun.Fragments.TripStatisticsFragment;
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.Models.User;
import trungduc.tripfun.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = "HomeActivity";
    private DrawerLayout drawer;
    public static User userTrip = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: ");
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView tv_username_H = (TextView) header.findViewById(R.id.tv_username_H);
        tv_username_H.setText("Nguyen Trung Duc");

        //get data from previous activity
        Intent intent = getIntent();
        String user_id = intent.getStringExtra(Constants.TAG_USERID);
        String user_name = intent.getStringExtra(Constants.TAG_USERNAME);
        String user_birth = intent.getStringExtra(Constants.TAG_USERBIRTH);
        String user_phonenumber = intent.getStringExtra(Constants.TAG_USERPHONENUMBER);
        String user_gender = intent.getStringExtra(Constants.TAG_USERGENDER);
        String user_email = intent.getStringExtra(Constants.TAG_USEREMAIL);
        String user_status = intent.getStringExtra(Constants.TAG_USERSTATUS);
        Log.d(TAG, "onActivityResult: "+ user_id +user_name+ user_birth+user_phonenumber+user_gender+user_email+user_status);
        userTrip.setUser_id(Integer.parseInt(user_id));
        userTrip.setName(user_name);
        userTrip.setBirth(user_birth);
        userTrip.setPhonenumber(user_phonenumber);
        userTrip.setGender(user_gender);
        userTrip.setEmail(user_email);
        userTrip.setStatus(user_status);
        //
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ManagerTripFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_manager_trip);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_manager_trip:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ManagerTripFragment()).commit();
                break;
            case R.id.nav_trip_statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TripStatisticsFragment()).commit();
                break;
            case R.id.nav_user_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserInfoFragment()).commit();
                break;
            case R.id.nav_app_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ManagerTripFragment()).commit();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Tìm chuyến", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Đăng chuyến", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Đăng xuất", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(this, "Back button is " +
                    "locked!", Toast.LENGTH_SHORT).show();
        }
    }
}