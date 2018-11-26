package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadFindTripTask;
import trungduc.tripfun.Task.LoadUserByIDTask;

public class ShowTripDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ShowTripDetailsActivity";
    private LoadFindTripTask loadFindTripTask;
    private TextView tvOri_STD,tvDes_STD,tvDate_STD,tvTime_STD,tvPosition_STD,tvVehicle_STD,tvService_STD,
                    tvSeatPrice_STD,tvEmptySeat_STD,tvFullSeat_STD,tvLuggage_STD,tvPlan_STD,tvWGender_STD; //STD = Show Trip Details
    private Button btnGo_STD;
    private MainActivity mainActivity;
    private String trip_userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trip_details);
        //init view
        handle();
        //get value
        Intent intent = getIntent();
        String trip_id = intent.getStringExtra(Constants.TAG_TRIPID);
        trip_userID = intent.getStringExtra(Constants.TAG_USERID);
        String trip_ori = intent.getStringExtra(Constants.TAG_ORIGIN);
        String trip_des = intent.getStringExtra(Constants.TAG_DESTINATION);
        String trip_date = intent.getStringExtra(Constants.TAG_DATE);
        String trip_time = intent.getStringExtra(Constants.TAG_TIME);
        String trip_vehicle = intent.getStringExtra(Constants.TAG_TYPEVEHICLE);
        String trip_position = intent.getStringExtra(Constants.TAG_POSITION);
        String trip_emptyseat = intent.getStringExtra(Constants.TAG_EMPTYSEAT);
        String trip_fullseat = intent.getStringExtra(Constants.TAG_FULLSEAT);
        String trip_seatprice = intent.getStringExtra(Constants.TAG_SEATPRICE);
        String trip_service = intent.getStringExtra(Constants.TAG_SERVICE);
        String trip_luggage = intent.getStringExtra(Constants.TAG_LUGGAGE);
        String trip_plan = intent.getStringExtra(Constants.TAG_PLAN);
        String trip_wgender = intent.getStringExtra(Constants.TAG_WGENDER);

        Toast.makeText(this, "ID: "+trip_id, Toast.LENGTH_SHORT).show();
        int tripID = Integer.parseInt(trip_id);
        //get and set value for trip
        tvOri_STD.setText(trip_ori);
        tvDes_STD.setText(trip_des);
        tvDate_STD.setText(trip_date);
        tvTime_STD.setText(trip_time);
        tvPosition_STD.setText(trip_position);
        tvVehicle_STD.setText(trip_vehicle);
        tvService_STD.setText(trip_service);
        tvSeatPrice_STD.setText(trip_seatprice);
        tvEmptySeat_STD.setText(trip_emptyseat);
        tvFullSeat_STD.setText(trip_fullseat);
        tvLuggage_STD.setText(trip_luggage);
        tvPlan_STD.setText(trip_plan);
        tvWGender_STD.setText(trip_wgender);
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
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGo_STD:
                Log.d(TAG, "onClick: "+ mainActivity.userLocal.getUser_id());
                LoadUserByIDTask loadUserByIDTask = new LoadUserByIDTask(getApplicationContext(),trip_userID);
                loadUserByIDTask.execute();
                finish();
                break;
        }
    }

    // initialize for view
    private void handle(){
        tvOri_STD = (TextView) findViewById(R.id.tvOri_STD);
        tvDes_STD = (TextView) findViewById(R.id.tvDes_STD);
        tvDate_STD = (TextView) findViewById(R.id.tvDate_STD);
        tvTime_STD = (TextView) findViewById(R.id.tvTime_STD);
        tvPosition_STD = (TextView) findViewById(R.id.tvPosition_STD);
        tvVehicle_STD = (TextView) findViewById(R.id.tvVehicle_STD);
        tvService_STD = (TextView) findViewById(R.id.tvService_STD);
        tvSeatPrice_STD= (TextView) findViewById(R.id.tvSeatPrice_STD);
        tvEmptySeat_STD= (TextView) findViewById(R.id.tvEmptySeat_STD);
        tvFullSeat_STD= (TextView) findViewById(R.id.tvFullSeat_STD);
        tvLuggage_STD= (TextView) findViewById(R.id.tvLuggage_STD);
        tvPlan_STD= (TextView) findViewById(R.id.tvPlan_STD);
        tvWGender_STD= (TextView) findViewById(R.id.tvWGender_STD);
        btnGo_STD = (Button) findViewById(R.id.btnGo_STD);
        btnGo_STD.setOnClickListener(this);
    }
}
