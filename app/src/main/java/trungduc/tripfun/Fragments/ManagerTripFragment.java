package trungduc.tripfun.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import trungduc.tripfun.Activities.HomeActivity;
import trungduc.tripfun.Adapters.CustomerAdapter;
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.Models.Tripdetails;
import trungduc.tripfun.Models.User;
import trungduc.tripfun.R;

public class ManagerTripFragment extends Fragment implements View.OnClickListener {
    private Dialog dialog;
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manager_trip, container, false);
        //new a dialog
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.info_trip_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //handle view
        linearLayout = (LinearLayout) v.findViewById(R.id.linearLayoutHide);
        TextView tvOriginFM = (TextView) v.findViewById(R.id.tvOrigin_FM);
        TextView tvDestinationFM = (TextView) v.findViewById(R.id.tvDestination_FM);
        Button btnTripDetailsFM = (Button) v.findViewById(R.id.btnTripDetailsFM);
        Button btnCall = (Button) v.findViewById(R.id.btnCall);
        ImageButton btnMessage = (ImageButton) v.findViewById(R.id.btnMessage);
        ImageButton btnCancelTrip = (ImageButton) v.findViewById(R.id.btnCancelTrip);
        //set textview hide when user don't join any trip
        if (HomeActivity.tripdetail.getOrigin()==null){
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        //set on click
        btnTripDetailsFM.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnMessage.setOnClickListener(this);
        btnCancelTrip.setOnClickListener(this);
        //check trip detail is null or not null
        if (HomeActivity.tripdetail.getOrigin() !=  null){
            tvOriginFM.setText(HomeActivity.tripdetail.getOrigin());
            tvDestinationFM.setText(HomeActivity.tripdetail.getDestination());
        }
        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //=========button show trip details===========//
            case R.id.btnTripDetailsFM:
                dialog.show();
                break;
            //=========button CALL===========//
            case R.id.btnCall:

                Log.d("onclick", "onClick: CALL");
                final Intent intentCall = new Intent(Intent.ACTION_CALL);

                if (HomeActivity.userLocal.getUser_id() == HomeActivity.TripUser.getUser_id()){
                    final ArrayList<User> listCustomer = new ArrayList<>();
                    if (HomeActivity.Customer1.getName()!=null){
                        listCustomer.add(HomeActivity.Customer1);
                    }if (HomeActivity.Customer2.getName()!=null){
                        listCustomer.add(HomeActivity.Customer2);
                    }if (HomeActivity.Customer3.getName()!=null){
                        listCustomer.add(HomeActivity.Customer3);
                    }if (HomeActivity.Customer4.getName()!=null){
                        listCustomer.add(HomeActivity.Customer4);
                    }if (HomeActivity.Customer5.getName()!=null){
                        listCustomer.add(HomeActivity.Customer5);
                    }if (HomeActivity.Customer6.getName()!=null){
                        listCustomer.add(HomeActivity.Customer6);
                    }if (HomeActivity.Customer7.getName()!=null){
                        listCustomer.add(HomeActivity.Customer7);
                    }if (HomeActivity.Customer8.getName()!=null){
                        listCustomer.add(HomeActivity.Customer8);
                    }if (HomeActivity.Customer9.getName()!=null){
                        listCustomer.add(HomeActivity.Customer9);
                    }
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.show_customer_dialog);
                    ListView lvCustomer = dialog.findViewById(R.id.lvShowCustomer);
                    CustomerAdapter customerAdapter = new CustomerAdapter(getContext(),listCustomer);
                    lvCustomer.setAdapter(customerAdapter);
                    lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String UserPhoneNumber = listCustomer.get(position).getPhonenumber();
                            Log.d("Phone Number",UserPhoneNumber);
                            intentCall.setData(Uri.parse("tel:"+UserPhoneNumber));
                            int checkPermission = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE);
                            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},0);
                            }
                            startActivity(intentCall);
                            dialog.cancel();
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }else{
                    String UserPhoneNumber = HomeActivity.TripUser.getPhonenumber();
                    Log.d("Phone Number",UserPhoneNumber);
                    intentCall.setData(Uri.parse("tel:"+UserPhoneNumber));
                    int checkPermission = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},0);
                    }
                    startActivity(intentCall);
                }
                break;
            //=========button send message===========//
            case R.id.btnMessage:
                Log.d("onclick", "onClick: Message");
                final Intent intentMessage =  new Intent(Intent.ACTION_SENDTO);
                if (HomeActivity.TripUser.getUser_id() == HomeActivity.userLocal.getUser_id()){
                    final ArrayList<User> listCustomer = new ArrayList<>();
                    if (HomeActivity.Customer1.getName()!=null){
                        listCustomer.add(HomeActivity.Customer1);
                    }if (HomeActivity.Customer2.getName()!=null){
                        listCustomer.add(HomeActivity.Customer2);
                    }if (HomeActivity.Customer3.getName()!=null){
                        listCustomer.add(HomeActivity.Customer3);
                    }if (HomeActivity.Customer4.getName()!=null){
                        listCustomer.add(HomeActivity.Customer4);
                    }if (HomeActivity.Customer5.getName()!=null){
                        listCustomer.add(HomeActivity.Customer5);
                    }if (HomeActivity.Customer6.getName()!=null){
                        listCustomer.add(HomeActivity.Customer6);
                    }if (HomeActivity.Customer7.getName()!=null){
                        listCustomer.add(HomeActivity.Customer7);
                    }if (HomeActivity.Customer8.getName()!=null){
                        listCustomer.add(HomeActivity.Customer8);
                    }if (HomeActivity.Customer9.getName()!=null){
                        listCustomer.add(HomeActivity.Customer9);
                    }
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.show_customer_dialog);
                    ListView lvCustomer = dialog.findViewById(R.id.lvShowCustomer);
                    CustomerAdapter customerAdapter = new CustomerAdapter(getContext(),listCustomer);
                    lvCustomer.setAdapter(customerAdapter);
                    lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String UserPhoneNumber = listCustomer.get(position).getPhonenumber();
                            Log.d("Phone Number",UserPhoneNumber);
                            intentMessage.setData(Uri.parse("smsto: "+UserPhoneNumber));
                            intentMessage.putExtra("sms_body","Xin chào "+listCustomer.get(position).getName()+"\nTôi là "+HomeActivity.userLocal.getName()+"\nBạn đã đặt chuyến đi cùng với tôi đúng không?\nMong có phản hồi từ bạn!");
                            int checkPermission = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE);
                            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},0);
                            }
                            startActivity(intentMessage);
                            dialog.cancel();
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }else{
                    intentMessage.setData(Uri.parse("smsto: "+HomeActivity.TripUser.getPhonenumber()));
                    intentMessage.putExtra("sms_body","Chào "+HomeActivity.TripUser.getName()+"\nTôi là "+HomeActivity.userLocal.getName()+"\nTôi đã đặt chuyến đi từ\n"+ HomeActivity.tripdetail.getOrigin()+"\nđến\n"+HomeActivity.tripdetail.getDestination() + "\nHãy trả lời tôi khi bạn có thể nha!");
                    startActivity(intentMessage);
                }
                break;
                //=========button CANCEL===========//
            case R.id.btnCancelTrip:
                int cancel_tripID = HomeActivity.tripdetail.getTripID();
                int cancel_UserID = HomeActivity.tripdetail.getUserID();
                Log.d("onclick", "onClick: cancel "+cancel_tripID+" "+cancel_UserID);
                //if owner click cancel
                if (HomeActivity.TripUser.getUser_id() == HomeActivity.userLocal.getUser_id()){
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_join_trip, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Dialog dialog = new Dialog(getContext());
                                dialog.setContentView(R.layout.message_dialog);
                                TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMessageDialog);
                                tvMsg.setText(jsonObject.getString(Constants.TAG_SUCCESS));
                                dialog.setCancelable(true);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                HomeActivity.tripdetail.setOrigin(null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put(Constants.TAG_TRIPID,String.valueOf(HomeActivity.tripdetail.getTripID()));
                            hashMap.put(Constants.TAG_TRIPUSERID,String.valueOf(HomeActivity.tripdetail.getUserID()));
                            hashMap.put("delete","yes");
                            return hashMap;
                        }
                    };requestQueue.add(stringRequest);
                }
                //if customer click cancel
                else{
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_join_trip, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Dialog dialog = new Dialog(getContext());
                                dialog.setContentView(R.layout.message_dialog);
                                TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMessageDialog);
                                tvMsg.setText(jsonObject.getString(Constants.TAG_SUCCESS));
                                dialog.setCancelable(true);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                HomeActivity.tripdetail.setOrigin(null);
                            } catch (JSONException e) {e.printStackTrace();}
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put(Constants.TAG_TRIPID,String.valueOf(HomeActivity.tripdetail.getTripID()));
                            hashMap.put(Constants.TAG_USERID,String.valueOf(HomeActivity.userLocal.getUser_id()));
                            hashMap.put("update","yes");
                            return hashMap;
                        }
                    };requestQueue.add(stringRequest);
                }
                break;
        }
    }
}