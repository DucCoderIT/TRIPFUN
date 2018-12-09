package trungduc.tripfun.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.R;

public class InputTripDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    private String TAG = "InputTripDetailsActivity";
    private RadioGroup group_position_IDe,group_vehicle_IDe;
    private RadioButton rdBtn_owner,rdBtn_customer,rdBtn_car,rdBtn_moto;
    private Spinner sp_service,sp_luggage,sp_plan,sp_wgender;
    private EditText edt_emptyseat,edt_fullseat,edt_seatprice;
    private Button btn_Uptrip;
    private String[] wgender = {"Sao cũng được!","Nam","Nữ"};
    private String[] luggage = {"Thoải mái!","Có hành lý!","Không mang theo hành lý!","Vừa phải thôi!"};
    private String[] service = {"Free","Xăng xe","Tiền công + xăng xe","Tiền công"};
    private String[] plan = {"Du lịch","Về quê","Đi hóng gió","Khác"};
    private String user_service;
    private String user_luggage;
    private String user_plan;
    private String user_wgender;
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_trip_details);

        handle();
        setItemforSpinner(sp_service,service);
        setItemforSpinner(sp_luggage,luggage);
        setItemforSpinner(sp_plan,plan);
        setItemforSpinner(sp_wgender,wgender);
        user_service = service[0];
        user_luggage = luggage[0];
        user_service = plan[0];
        user_wgender = wgender[0];
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_Uptrip_IDe:
                Log.d(TAG, "onClick: ");
                //get data from previous activity
                Intent intent = getIntent();
                final String user_ori = intent.getStringExtra("ori");
                final String user_des = intent.getStringExtra("des");

                int selected_position = group_position_IDe.getCheckedRadioButtonId();
                RadioButton radioButton_position = (RadioButton) findViewById(selected_position);
                int selected_vehicle = group_vehicle_IDe.getCheckedRadioButtonId();
                RadioButton radioButton_vehicle = (RadioButton) findViewById(selected_vehicle);
                //get user info from userLocal
                final String user_id = String.valueOf(MainActivity.userLocal.getUser_id());
                final String user_gender = MainActivity.userLocal.getGender();
                final String user_evaluation = String.valueOf(MainActivity.userLocal.getEvaluation());
                //format date
                String date = intent.getStringExtra("date");
                String[] splitDate = date.split("/");
                final String user_date = splitDate[2]+"-"+splitDate[1]+"-"+splitDate[0];
                final String user_time = intent.getStringExtra("time");

                final String user_emptyseat = edt_emptyseat.getText().toString();
                final String user_fullseat = edt_fullseat.getText().toString();
                String seatprice = edt_seatprice.getText().toString(); // can not input 0
                //set seatprice  = 0+" "
                final String user_seatprice;
                if (seatprice.equals("0")){
                    user_seatprice = seatprice +" ";
                }else {user_seatprice = seatprice; }
                if (radioButton_position!=null && radioButton_vehicle!=null && !user_ori.equals("") && !user_des.equals("")
                        && !user_date.equals("") && !user_time.equals("") && !user_emptyseat.equals("")
                        && !user_fullseat.equals("") && !user_seatprice.equals("") ){
                    final String user_position = radioButton_position.getText().toString();
                    final String user_vehicle = radioButton_vehicle.getText().toString();
//                    Log.d(TAG, "onClick: OK NOT NULL");
//                        Log.d(TAG, "onClick: oke");
                    //=============request create trip ================
                        request = new StringRequest(Request.Method.POST, Constants.url_trip_control, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    if (object.names().get(0).equals("success")){
                                        Log.d(TAG, "done: created trip");
                                        final Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra(Constants.TAG_USERID,String.valueOf(MainActivity.userLocal.getUser_id()));
                                        intent.putExtra(Constants.TAG_USERNAME,MainActivity.userLocal.getName() );
                                        intent.putExtra(Constants.TAG_USERBIRTH,MainActivity.userLocal.getBirth());
                                        intent.putExtra(Constants.TAG_USERPHONENUMBER,MainActivity.userLocal.getPhonenumber());
                                        intent.putExtra(Constants.TAG_USERGENDER,MainActivity.userLocal.getGender());
                                        intent.putExtra(Constants.TAG_USEREMAIL,MainActivity.userLocal.getEmail());
                                        intent.putExtra(Constants.TAG_USERSTATUS,MainActivity.userLocal.getStatus());
                                        intent.putExtra(Constants.TAG_USEREMAIL,MainActivity.userLocal.getEmail());
                                        intent.putExtra(Constants.TAG_USERSTATUS,MainActivity.userLocal.getStatus());
                                        intent.putExtra(Constants.TAG_EVALUATION,String.valueOf(MainActivity.userLocal.getEvaluation()));
                                        //show message
                                        Dialog dialogMessage = new Dialog(InputTripDetailsActivity.this);
                                        dialogMessage.setContentView(R.layout.message_dialog);
                                        TextView tvMessage = (TextView) dialogMessage.findViewById(R.id.tvMessageDialog);
                                        tvMessage.setText(object.getString(Constants.TAG_SUCCESS));
                                        dialogMessage.setCancelable(true);
                                        dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialogMessage.show();
                                        Timer timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                startActivity(intent);
                                                finish();
                                            }
                                        },2000);
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
                                HashMap<String,String> hashMap = new HashMap<String, String>();
                                hashMap.put("userID",user_id);
                                hashMap.put("origin",user_ori);
                                hashMap.put("destination",user_des);
                                hashMap.put("date",user_date);
                                hashMap.put("time",user_time);
                                hashMap.put("typevehicle",user_vehicle);
                                hashMap.put("position",user_position);
                                hashMap.put("emptyseat",user_emptyseat);
                                hashMap.put("fullseat",user_fullseat);
                                hashMap.put("seatprice",user_seatprice);
                                hashMap.put("service",user_service);
                                hashMap.put("luggage",user_luggage);
                                hashMap.put("plan",user_plan);
                                hashMap.put("wgender",user_wgender);
                                hashMap.put("userGender",user_gender);
                                hashMap.put("userEvalua",user_evaluation);
                                hashMap.put("createUser","Yes");
                                return hashMap;
                            }
                        };
                        requestQueue.add(request);
                }else{
                    //Log.d(TAG, "onClick: NULL");
                    if (radioButton_position==null){
                        showMessage("Bạn chưa chọn: Bạn là gì?");
                    }else if (radioButton_vehicle==null){
                        showMessage("Bạn chưa chọn: Phương tiện là gì?");
                    }else if (user_emptyseat.equals("")){
                        showMessage("Vui lòng nhập số ghế trống!");
                    }else if (user_fullseat.equals("")){
                        showMessage("Vui lòng nhập tổng số ghế!");
                    }else if (seatprice.equals("")){
                        showMessage("Vui lòng nhập giá tiền trên 1 ghế!");
                    }
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinnerService = (Spinner)parent;
        Spinner spinnerLuggage = (Spinner)parent;
        Spinner spinnerPlan = (Spinner)parent;
        Spinner spinnerWGender = (Spinner)parent;

        if (spinnerService.getId() == R.id.sp_service_IDe){
            user_service = service[position];
        }
        if (spinnerLuggage.getId() == R.id.sp_luggage_IDe){
            user_luggage= luggage[position];
        }
        if (spinnerPlan.getId() == R.id.sp_plan_IDe){
            user_plan = plan[position];
        }
        if (spinnerWGender.getId() == R.id.sp_wgender_IDe){
            user_wgender = wgender[position];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void setItemforSpinner(Spinner spinner,String[] list){
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter_service = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter_service.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter_service);
    }
    private void handle(){
        group_position_IDe = (RadioGroup) findViewById(R.id.group_position_IDe);
        group_vehicle_IDe = (RadioGroup) findViewById(R.id.group_vehicle_IDe);
        rdBtn_owner = (RadioButton) findViewById(R.id.rdbtn_owner_IDe);
        rdBtn_customer = (RadioButton) findViewById(R.id.rdbtn_customer_IDe);
        rdBtn_car = (RadioButton) findViewById(R.id.rdBtn_car_IDe);
        rdBtn_moto = (RadioButton) findViewById(R.id.rdBtn_Moto_IDe);
        sp_luggage = (Spinner)  findViewById(R.id.sp_luggage_IDe);
        sp_plan = (Spinner) findViewById(R.id.sp_plan_IDe);
        sp_service = (Spinner) findViewById(R.id.sp_service_IDe);
        sp_wgender = (Spinner) findViewById(R.id.sp_wgender_IDe);
        edt_emptyseat = (EditText) findViewById(R.id.edt_emptyseat_IDe);
        edt_fullseat = (EditText) findViewById(R.id.edt_fullseat_IDe);
        edt_seatprice = (EditText) findViewById(R.id.edt_seatprice_IDe);
        btn_Uptrip = (Button)   findViewById(R.id.btn_Uptrip_IDe);
        btn_Uptrip.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
    }
    private void showMessage(String s){
        Dialog dialogMessage = new Dialog(InputTripDetailsActivity.this);
        dialogMessage.setContentView(R.layout.message_dialog);
        TextView tvMessage = (TextView) dialogMessage.findViewById(R.id.tvMessageDialog);
        tvMessage.setText(s);
        dialogMessage.setCancelable(true);
        dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogMessage.show();
    }
}
