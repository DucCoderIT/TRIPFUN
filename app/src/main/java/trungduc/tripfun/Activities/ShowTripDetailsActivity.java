package trungduc.tripfun.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.Models.User;
import trungduc.tripfun.R;

public class ShowTripDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ShowTripDetailsActivity";
    private TextView tvOri_STD,tvDes_STD,tvDate_STD,tvTime_STD,tvPosition_STD,tvVehicle_STD,tvService_STD,
                    tvSeatPrice_STD,tvEmptySeat_STD,tvFullSeat_STD,tvLuggage_STD,tvPlan_STD,tvWGender_STD; //STD = Show Trip Details
    private Button btnGo_STD;
    private String trip_userID,trip_id,trip_ori,trip_des,trip_date,trip_time,trip_vehicle,trip_position,
            trip_emptyseat,trip_fullseat,trip_seatprice,trip_service,trip_luggage,trip_plan,trip_wgender;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    public static User UserLocal = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trip_details);
        //init view
        handle();
        UserLocal = MainActivity.userLocal;
        Log.d(TAG, "UserLocal: "+UserLocal.getUser_id()+UserLocal.getBirth()+UserLocal.getName()+UserLocal.getGender()+UserLocal.getPhonenumber());
        //get value
        Intent intent = getIntent();
        trip_id = intent.getStringExtra(Constants.TAG_TRIPID);
        trip_userID = intent.getStringExtra(Constants.TAG_USERID);
        trip_ori = intent.getStringExtra(Constants.TAG_ORIGIN);
        trip_des = intent.getStringExtra(Constants.TAG_DESTINATION);
        trip_date = intent.getStringExtra(Constants.TAG_DATE);
        trip_time = intent.getStringExtra(Constants.TAG_TIME);
        trip_vehicle = intent.getStringExtra(Constants.TAG_TYPEVEHICLE);
        trip_position = intent.getStringExtra(Constants.TAG_POSITION);
        trip_emptyseat = intent.getStringExtra(Constants.TAG_EMPTYSEAT);
        trip_fullseat = intent.getStringExtra(Constants.TAG_FULLSEAT);
        trip_seatprice = intent.getStringExtra(Constants.TAG_SEATPRICE);
        trip_service = intent.getStringExtra(Constants.TAG_SERVICE);
        trip_luggage = intent.getStringExtra(Constants.TAG_LUGGAGE);
        trip_plan = intent.getStringExtra(Constants.TAG_PLAN);
        trip_wgender = intent.getStringExtra(Constants.TAG_WGENDER);

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
                final int tripUserID = Integer.parseInt(trip_userID);
                if (UserLocal.getUser_id() == tripUserID ){
                    Dialog dialogMessage = new Dialog(ShowTripDetailsActivity.this);
                    dialogMessage.setContentView(R.layout.message_dialog);
                    TextView tvMsg = dialogMessage.findViewById(R.id.tvMessageDialog);
                    tvMsg.setText("Đây là chuyến do bạn đăng!\nKhông thể book!\nHãy tìm chuyến đi khác");
                    dialogMessage.setCancelable(true);
                    dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogMessage.show();
                }else{
                    stringRequest = new StringRequest(Request.Method.POST, Constants.url_join_trip, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals(Constants.TAG_SUCCESS)){
                                    final Dialog dialogMessage = new Dialog(ShowTripDetailsActivity.this);
                                    dialogMessage.setContentView(R.layout.message_dialog);
                                    TextView tvMsg = dialogMessage.findViewById(R.id.tvMessageDialog);
                                    tvMsg.setText(jsonObject.getString(Constants.TAG_SUCCESS));
                                    dialogMessage.setCancelable(false);
                                    dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialogMessage.show();
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            dialogMessage.cancel();
                                            gotoHome();
                                        }
                                    },2000);

                                }
                                else {
                                    Dialog dialogMessage = new Dialog(ShowTripDetailsActivity.this);
                                    dialogMessage.setContentView(R.layout.message_dialog);
                                    TextView tvMsg = dialogMessage.findViewById(R.id.tvMessageDialog);
                                    tvMsg.setText(jsonObject.getString(Constants.TAG_ERROR));
                                    dialogMessage.setCancelable(true);
                                    dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialogMessage.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put(Constants.TAG_TRIPID,trip_id);
                            hashMap.put(Constants.TAG_TRIPUSERID,trip_userID);
                            hashMap.put(Constants.TAG_USERID,String.valueOf(UserLocal.getUser_id()));
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    break;
                }
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

        requestQueue = Volley.newRequestQueue(this);
    }
    private void gotoHome(){
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        // send user info
        intent.putExtra(Constants.TAG_USERID,String.valueOf(UserLocal.getUser_id()));
        intent.putExtra(Constants.TAG_USERNAME,UserLocal.getName() );
        intent.putExtra(Constants.TAG_USERBIRTH,UserLocal.getBirth());
        intent.putExtra(Constants.TAG_USERPHONENUMBER,UserLocal.getPhonenumber());
        intent.putExtra(Constants.TAG_USERGENDER,UserLocal.getGender());
        intent.putExtra(Constants.TAG_USEREMAIL,UserLocal.getEmail());
        intent.putExtra(Constants.TAG_USERSTATUS,UserLocal.getStatus());
        intent.putExtra(Constants.TAG_USEREMAIL,UserLocal.getEmail());
        intent.putExtra(Constants.TAG_USERSTATUS,UserLocal.getStatus());
        intent.putExtra(Constants.TAG_EVALUATION,String.valueOf(UserLocal.getEvaluation()));
        //send trip info
        intent.putExtra(Constants.TAG_TRIPID,String.valueOf(trip_id));
        intent.putExtra(Constants.TAG_TRIPUSERID,String.valueOf(trip_userID));
        intent.putExtra(Constants.TAG_ORIGIN, trip_ori);
        intent.putExtra(Constants.TAG_DESTINATION, trip_des);
        intent.putExtra(Constants.TAG_DATE, trip_date);
        intent.putExtra(Constants.TAG_TIME, trip_time);
        intent.putExtra(Constants.TAG_TYPEVEHICLE, trip_vehicle);
        intent.putExtra(Constants.TAG_POSITION, trip_position);
        intent.putExtra(Constants.TAG_EMPTYSEAT, trip_emptyseat);
        intent.putExtra(Constants.TAG_FULLSEAT, trip_fullseat);
        intent.putExtra(Constants.TAG_SEATPRICE, trip_seatprice);
        intent.putExtra(Constants.TAG_SERVICE, trip_service);
        intent.putExtra(Constants.TAG_LUGGAGE, trip_luggage);
        intent.putExtra(Constants.TAG_PLAN, trip_plan);
        intent.putExtra(Constants.TAG_WGENDER, trip_wgender);
        startActivity(intent);
        finish();
    }
}
