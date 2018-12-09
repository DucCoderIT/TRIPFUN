package trungduc.tripfun.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import trungduc.tripfun.Activities.HomeActivity;
import trungduc.tripfun.Adapters.TripdetailsAdapter;
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.Models.Tripdetails;
import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadAllTripByUserIDTask;


public class TripStatisticsFragment extends Fragment {
    private ListView listViewDone,listViewActive;
    private TripdetailsAdapter tripdetailsAdapterActive,tripdetailsAdapterDone;
    private ArrayList<Tripdetails> listTripDone = new ArrayList<>();
    private ArrayList<Tripdetails> listTripActive = new ArrayList<>();
    private static final String TAG = "ManagerTripFragment";
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_trip_statistics, container, false);
        listViewActive = (ListView) view.findViewById(R.id.lvAllTripByUserID);
        listViewDone = (ListView) view.findViewById(R.id.lvTripDone);

        if (HomeActivity.listTripdetails.size() > 0){
            Log.d(TAG, "onCreateView: list trip details size = "+HomeActivity.listTripdetails.size());
            //get date and time now =============
            Date dateNow = new Date();
            String date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.YEAR);
            try {
                dateNow = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date timeNow = new Date();
            String time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND);
            try {
                timeNow = formatTime.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ///========== CHECK TRIP PASS OR FUTURE TO ADD LIST
            for (int i=0;i<HomeActivity.listTripdetails.size();i++){
                Log.d(TAG, "onCreateView: dateTrip is today");
                Date dateTrip = HomeActivity.listTripdetails.get(i).getDate();
                if (dateTrip.compareTo(dateNow)>0){
                    Log.d(TAG, "onCreateView: dateTrip is future");
                    listTripActive.add(HomeActivity.listTripdetails.get(i));
                }else if(dateTrip.compareTo(dateNow)<0){
                    listTripDone.add(HomeActivity.listTripdetails.get(i));
                    Log.d(TAG, "onCreateView: dateTrip is pass");
                }else if(dateNow.compareTo(dateTrip)==0){
                    Date timeTrip = HomeActivity.listTripdetails.get(i).getTime();
                    if (timeTrip.compareTo(timeNow)>0){
                        Log.d(TAG, "onCreateView: timeTrip is future");
                        listTripActive.add(HomeActivity.listTripdetails.get(i));
                    }else if(timeTrip.compareTo(timeNow)<0){
                        Log.d(TAG, "onCreateView: timeTrip is pass");
                        listTripDone.add(HomeActivity.listTripdetails.get(i));
                    }
                }
            }
            //=====trip done==
            tripdetailsAdapterDone = new TripdetailsAdapter(getActivity(),listTripDone);
            listViewDone.setAdapter(tripdetailsAdapterDone);
            listViewDone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Dialog dialogTripDetails = new Dialog(getContext());
                    dialogTripDetails.setContentView(R.layout.info_trip_dialog);
                    TextView tvDate_dialog = dialogTripDetails.findViewById(R.id.tvDate_dialog);
                    TextView tvTime_dialog = dialogTripDetails.findViewById(R.id.tvTime_dialog);
                    TextView tvPosition_dialog = dialogTripDetails.findViewById(R.id.tvPosition_dialog);
                    TextView tvVehicle_dialog = dialogTripDetails.findViewById(R.id.tvVehicle_dialog);
                    TextView tvService_dialog = dialogTripDetails.findViewById(R.id.tvService_dialog);
                    TextView tvSeatPrice_dialog = dialogTripDetails.findViewById(R.id.tvSeatPrice_dialog);
                    TextView tvEmptySeat_dialog = dialogTripDetails.findViewById(R.id.tvEmptySeat_dialog);
                    TextView tvFullSeat_dialog = dialogTripDetails.findViewById(R.id.tvFullSeat_dialog);
                    TextView tvLuggage_dialog = dialogTripDetails.findViewById(R.id.tvLuggage_dialog);
                    TextView tvPlan_dialog = dialogTripDetails.findViewById(R.id.tvPlan_dialog);
                    TextView tvWGender_dialog = dialogTripDetails.findViewById(R.id.tvWGender_dialog);

                    tvDate_dialog.setText(format.format(listTripDone.get(position).getDate()));
                    tvTime_dialog.setText(formatTime.format(listTripDone.get(position).getTime()));
                    tvPosition_dialog.setText(listTripDone.get(position).getPosition());
                    tvVehicle_dialog.setText(listTripDone.get(position).getTypevehicle());
                    tvService_dialog.setText(listTripDone.get(position).getService());
                    tvSeatPrice_dialog.setText(listTripDone.get(position).getSeatprice()+"");
                    tvEmptySeat_dialog.setText(listTripDone.get(position).getEmptyseat()+"");
                    tvFullSeat_dialog.setText(listTripDone.get(position).getFullseat()+"");
                    tvLuggage_dialog.setText(listTripDone.get(position).getLuggage());
                    tvPlan_dialog.setText(listTripDone.get(position).getPlan());
                    tvWGender_dialog.setText(listTripDone.get(position).getWgender());

                    dialogTripDetails.setCancelable(true);
                    dialogTripDetails.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogTripDetails.show();
                }
            });
            //====
            tripdetailsAdapterActive = new TripdetailsAdapter(getActivity(),listTripActive);
            listViewActive.setAdapter(tripdetailsAdapterActive);
            listViewActive.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final Dialog dialogLongClick = new Dialog(getContext());
                    dialogLongClick.setContentView(R.layout.have_message_dialog);
                    TextView tvMessage = (TextView) dialogLongClick.findViewById(R.id.tvMessage);
                    tvMessage.setText("Bạn muốn làm gì?");
                    Button btnJoinTrip = (Button) dialogLongClick.findViewById(R.id.btnJoinTrip);
                    btnJoinTrip.setText("Sửa");
                    btnJoinTrip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogLongClick.cancel();
                            Log.d(TAG, "onClick: change");
                            final Dialog dialogChangeTripInfo = new Dialog(getContext());
                            dialogChangeTripInfo.setContentView(R.layout.change_trip_dialog);
                            //dialog handle
                            final EditText edtDate_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtDate_change);
                            final EditText edtTime_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtTime_change);
                            final EditText edtPosition_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtPosition_change);
                            final EditText edtVehicle_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtVehicle_change);
                            final EditText edtEmptySeat_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtEmptySeat_change);
                            final EditText edtFullSeat_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtFullSeat_change);
                            final EditText edtService_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtService_change);
                            final EditText edtSeatPrice_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtSeatPrice_change);
                            final EditText edtLuggage_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtLuggage_change);
                            final EditText edtPlan_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtPlan_change);
                            final EditText edtWGender_change = (EditText) dialogChangeTripInfo.findViewById(R.id.edtWGender_change);
                            //set text
                            edtDate_change.setText(format.format(HomeActivity.listTripdetails.get(position).getDate()));
                            edtTime_change.setText(formatTime.format(HomeActivity.listTripdetails.get(position).getTime()));
                            edtPosition_change.setText(HomeActivity.listTripdetails.get(position).getPosition());
                            edtVehicle_change.setText(HomeActivity.listTripdetails.get(position).getTypevehicle());
                            edtEmptySeat_change.setText(HomeActivity.listTripdetails.get(position).getEmptyseat()+"");
                            edtFullSeat_change.setText(HomeActivity.listTripdetails.get(position).getFullseat()+"");
                            edtService_change.setText(HomeActivity.listTripdetails.get(position).getService());
                            edtSeatPrice_change.setText(HomeActivity.listTripdetails.get(position).getSeatprice()+"");
                            edtLuggage_change.setText(HomeActivity.listTripdetails.get(position).getLuggage());
                            edtPlan_change.setText(HomeActivity.listTripdetails.get(position).getPlan());
                            edtWGender_change.setText(HomeActivity.listTripdetails.get(position).getWgender());
                            Button btnChangeTripDone = (Button) dialogChangeTripInfo.findViewById(R.id.btnChangeTripDone);
                            Button btnChangeTripCancel = (Button) dialogChangeTripInfo.findViewById(R.id.btnChangeTripCancel);
                            btnChangeTripDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final String date = edtDate_change.getText().toString();
                                    String[] separated = date.split("/");
                                    Date ParseDate = null;
                                    try {
                                        ParseDate = format.parse(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    final String trip_date = separated[2]+"-"+separated[1]+"-"+separated[0];
                                    final String trip_time = edtTime_change.getText().toString()+":00";
                                    final String trip_position = edtPosition_change.getText().toString();
                                    final String trip_vehicle = edtVehicle_change.getText().toString();
                                    final String trip_emptySeat = edtEmptySeat_change.getText().toString();
                                    final String trip_fullSeat = edtFullSeat_change.getText().toString();
                                    final String trip_service = edtService_change.getText().toString();
                                    final String trip_seatPrice = edtSeatPrice_change.getText().toString()+" ";
                                    final String trip_luggage = edtLuggage_change.getText().toString();
                                    final String trip_plan = edtPlan_change.getText().toString();
                                    final String trip_wGender = edtWGender_change.getText().toString();

                                    Boolean checkNull;
                                    if (!date.equals("")&&!edtTime_change.getText().toString().equals("")&&!trip_position.equals("")
                                            &&!trip_vehicle.equals("")&&!trip_emptySeat.equals("")&&!trip_fullSeat.equals("")&&!trip_service.equals("")&&!trip_seatPrice.equals("")
                                            &&!trip_luggage.equals("")&&!trip_plan.equals("") &&!trip_wGender.equals("")){
                                        checkNull = true;
                                    }else{
                                        checkNull = false;
                                    }
                                    //===========Validate=========
                                    if (isValidateDate(date)&&isValidateTime(edtTime_change.getText().toString())&&!trip_emptySeat.equals("0")
                                            &&!trip_fullSeat.equals("0")&&checkNull){
                                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_trip_control, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    Dialog dialogMessage = new Dialog(getContext());
                                                    dialogMessage.setContentView(R.layout.message_dialog);
                                                    TextView tvMsg = (TextView) dialogMessage.findViewById(R.id.tvMessageDialog);
                                                    tvMsg.setText(jsonObject.getString(Constants.TAG_SUCCESS));
                                                    dialogMessage.setCancelable(true);
                                                    dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    dialogMessage.show();
                                                    dialogChangeTripInfo.cancel();
                                                } catch (JSONException e) {e.printStackTrace();}
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) { }
                                        }){
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                HashMap<String,String> hashMap = new HashMap<>();
                                                hashMap.put(Constants.TAG_TRIPID,String.valueOf(HomeActivity.listTripdetails.get(position).getTripID()));
                                                hashMap.put(Constants.TAG_ORIGIN,HomeActivity.listTripdetails.get(position).getOrigin());
                                                hashMap.put(Constants.TAG_DESTINATION,HomeActivity.listTripdetails.get(position).getDestination());
                                                hashMap.put(Constants.TAG_DATE,trip_date);
                                                hashMap.put(Constants.TAG_TIME,trip_time);
                                                hashMap.put(Constants.TAG_TYPEVEHICLE,trip_vehicle);
                                                hashMap.put(Constants.TAG_POSITION,trip_position);
                                                hashMap.put(Constants.TAG_EMPTYSEAT,trip_emptySeat);
                                                hashMap.put(Constants.TAG_FULLSEAT,trip_fullSeat);
                                                hashMap.put(Constants.TAG_SEATPRICE,trip_seatPrice);
                                                hashMap.put(Constants.TAG_SERVICE,trip_service);
                                                hashMap.put(Constants.TAG_LUGGAGE,trip_luggage);
                                                hashMap.put(Constants.TAG_PLAN,trip_plan);
                                                hashMap.put(Constants.TAG_WGENDER,trip_wGender);
                                                hashMap.put("updateTrip","Yes");
                                                return hashMap;
                                            }
                                        };requestQueue.add(stringRequest);
                                        HomeActivity.listTripdetails.get(position).setDate(ParseDate);
                                        HomeActivity.listTripdetails.get(position).setTime(Time.valueOf(trip_time));
                                        HomeActivity.listTripdetails.get(position).setTypevehicle(trip_vehicle);
                                        HomeActivity.listTripdetails.get(position).setPosition(trip_position);
                                        HomeActivity.listTripdetails.get(position).setEmptyseat(Integer.parseInt(trip_emptySeat));
                                        HomeActivity.listTripdetails.get(position).setFullseat(Integer.parseInt(trip_fullSeat));
                                        HomeActivity.listTripdetails.get(position).setSeatprice(Integer.parseInt(trip_seatPrice.replaceAll(" ","")));
                                        HomeActivity.listTripdetails.get(position).setService(trip_service);
                                        HomeActivity.listTripdetails.get(position).setLuggage(trip_luggage);
                                        HomeActivity.listTripdetails.get(position).setPlan(trip_plan);
                                        HomeActivity.listTripdetails.get(position).setWgender(trip_wGender);
                                        tripdetailsAdapterActive.notifyDataSetChanged();
                                    }else{
                                        Dialog dialogMessage = new Dialog(getContext());
                                        dialogMessage.setCancelable(true);
                                        dialogMessage.setContentView(R.layout.message_dialog);
                                        dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        TextView tvMessage = (TextView) dialogMessage.findViewById(R.id.tvMessageDialog);
                                        if (!checkNull){
                                            Log.d(TAG, "onClick: error validate null");
                                            tvMessage.setText("Vui lòng không để trống bất kỳ 1 trường nào!");
                                            dialogMessage.show();
                                        }else if (!isValidateDate(date)){
                                            Log.d(TAG, "onClick: error validate date");
                                            tvMessage.setText("Vui lòng nhập đúng định dạng ngày như sau: \n20/10/2018");
                                            dialogMessage.show();
                                        }else if (!isValidateTime(edtTime_change.getText().toString())){
                                            Log.d(TAG, "onClick: error validate time");
                                            tvMessage.setText("Vui lòng nhập đúng định dạng như sau: \n10:30");
                                            dialogMessage.show();
                                        }
                                    }
                                }
                            });
                            btnChangeTripCancel.setOnClickListener(new View.OnClickListener(){
                               @Override
                               public void onClick(View v){
                                    dialogChangeTripInfo.cancel();
                               }
                            });
                            dialogChangeTripInfo.setCancelable(false);
                            dialogChangeTripInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialogChangeTripInfo.show();
                        }
                    });
                    Button btnCancleJoinTrip = (Button) dialogLongClick.findViewById(R.id.btnCancleJoinTrip);
                    btnCancleJoinTrip.setText("Xóa");
                    //===========DELETE TRIP=============//
                    btnCancleJoinTrip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: delete");
                            final Dialog dialogSubmitDelete = new Dialog(getContext());
                            dialogSubmitDelete.setContentView(R.layout.have_message_dialog);
                            TextView tvMessage = (TextView) dialogSubmitDelete.findViewById(R.id.tvMessage);
                            tvMessage.setText("Bạn có chắc chắn muốn xóa chuyến đi này!");
                            Button btnDone = (Button) dialogSubmitDelete.findViewById(R.id.btnJoinTrip);
                            btnDone.setText("OK");
                            Button btnCancel = (Button) dialogSubmitDelete.findViewById(R.id.btnCancleJoinTrip);
                            btnDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_trip_control, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                Dialog dialogMessage = new Dialog(getContext());
                                                dialogMessage.setContentView(R.layout.message_dialog);
                                                TextView tvMsg = (TextView) dialogMessage.findViewById(R.id.tvMessageDialog);
                                                tvMsg.setText(jsonObject.getString(Constants.TAG_SUCCESS));
                                                dialogMessage.setCancelable(true);
                                                dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                dialogMessage.show();
                                                HomeActivity.listTripdetails.remove(position);
                                                tripdetailsAdapterActive.notifyDataSetChanged();
                                            } catch (JSONException e) {e.printStackTrace();}
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) { }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String,String> hashMap = new HashMap<>();
                                            hashMap.put(Constants.TAG_TRIPID,String.valueOf(HomeActivity.listTripdetails.get(position).getTripID()));
                                            hashMap.put("deleteTrip","yes");
                                            return hashMap;
                                        }
                                    };requestQueue.add(stringRequest);
                                    dialogSubmitDelete.cancel();
                                }
                            });
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogSubmitDelete.cancel();
                                }
                            });
                            dialogSubmitDelete.setCancelable(false);
                            dialogSubmitDelete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialogSubmitDelete.show();
                            dialogLongClick.cancel();
                        }
                    });
                    dialogLongClick.setCancelable(true);
                    dialogLongClick.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogLongClick.show();
                    return false;
                }
            });

        }else{
            Log.d(TAG, "onCreateView: list trip details null");

        }
        return view;
    }
    //check date format
    public boolean isValidateDate(String value) {
            String datePattern = "\\d{2}/\\d{2}/\\d{4}";
            Boolean isDate = value.matches(datePattern);
            return isDate; }
    //check time format
    public boolean isValidateTime(String value) {
        String datePattern = "\\d{2}:\\d{2}";
        Boolean isTime = value.matches(datePattern);
        return isTime; }
}