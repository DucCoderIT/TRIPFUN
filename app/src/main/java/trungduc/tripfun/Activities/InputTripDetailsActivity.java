package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trungduc.tripfun.R;

public class InputTripDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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

        btn_Uptrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selected_position = group_position_IDe.getCheckedRadioButtonId();
                RadioButton radioButton_position = (RadioButton) findViewById(selected_position);
                int selected_vehicle = group_vehicle_IDe.getCheckedRadioButtonId();
                RadioButton radioButton_vehicle = (RadioButton) findViewById(selected_vehicle);

                Intent intent = getIntent();
                String user_ori = intent.getStringExtra("ori");
                String user_des = intent.getStringExtra("des");
                String user_date = intent.getStringExtra("date");
                String user_time = intent.getStringExtra("time");
                String user_emptyseat = edt_emptyseat.getText().toString();
                String user_fullseat = edt_fullseat.getText().toString();
                String user_seatprice = edt_seatprice.getText().toString();
                String user_position = radioButton_position.getText().toString();
                String user_vehicle = radioButton_vehicle.getText().toString();

                Toast.makeText(getApplicationContext(), user_ori+"  "+user_des+"  "+user_date+"  "+user_time+"  "+
                        user_emptyseat+"  "+user_fullseat+"  "+user_seatprice+"  "+user_position+"  "+user_vehicle+"  "+
                        user_service+"  "+user_luggage+"  "+user_plan+"  "+user_wgender, Toast.LENGTH_LONG).show();
            }
        });
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

    private void setDefaultItem(){ }
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
    }
}
