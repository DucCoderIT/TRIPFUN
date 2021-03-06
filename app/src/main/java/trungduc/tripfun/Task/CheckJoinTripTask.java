package trungduc.tripfun.Task;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;
import trungduc.tripfun.Activities.HomeActivity;
import trungduc.tripfun.Activities.MainActivity;
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.R;

public class CheckJoinTripTask extends AsyncTask<String,String,String> {

    private Context context;
    private ProgressDialog progressDialog;
    private Dialog dialogHaveMessage;
    private StringRequest request;
    private RequestQueue requestQueue;
    private int userID;

    public CheckJoinTripTask(Context context,int userID) {
        this.context = context;
        this.userID = userID;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Đang kiểm tra...!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        // check user join trip
        request = new StringRequest(Request.Method.POST, Constants.url_check_join_trip, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals(Constants.TAG_SUCCESS)){
                        Log.d("SUCCESS", "onResponse: "+jsonObject.getString(Constants.TAG_SUCCESS));
                        final String trip_ID = jsonObject.getString(Constants.TAG_TRIPID); //set tripID

                        String message = "Bạn có chuyến đi đang trong hàng chờ!";//set message for dialogHaveMessage
                        //show dialogHaveMessage
                        dialogHaveMessage = new Dialog(context);
                        dialogHaveMessage.setContentView(R.layout.have_message_dialog);
                        TextView tvMsg = dialogHaveMessage.findViewById(R.id.tvMessage);
                        Button btnJoinTrip = dialogHaveMessage.findViewById(R.id.btnJoinTrip);
                        Button btnCancleJoinTrip = dialogHaveMessage.findViewById(R.id.btnCancleJoinTrip);
                        //click go to home=========
                        btnJoinTrip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogHaveMessage.cancel();
                                //get trip details
                                request = new StringRequest(Request.Method.POST, Constants.url_check_join_trip, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObjectAnother = new JSONObject(response);

                                            String user_id = jsonObjectAnother.getString(Constants.TAG_USERID);
                                            String origin = jsonObjectAnother.getString(Constants.TAG_ORIGIN);
                                            String destination = jsonObjectAnother.getString(Constants.TAG_DESTINATION);
                                            String date = jsonObjectAnother.getString(Constants.TAG_DATE);
                                            String time = jsonObjectAnother.getString(Constants.TAG_TIME);
                                            String typevehicle = jsonObjectAnother.getString(Constants.TAG_TYPEVEHICLE);
                                            String position = jsonObjectAnother.getString(Constants.TAG_POSITION);
                                            String seatprice = jsonObjectAnother.getString(Constants.TAG_SEATPRICE);
                                            String emptyseat = jsonObjectAnother.getString(Constants.TAG_EMPTYSEAT);
                                            String fullseat = jsonObjectAnother.getString(Constants.TAG_FULLSEAT);
                                            String service = jsonObjectAnother.getString(Constants.TAG_SERVICE);
                                            String luggage = jsonObjectAnother.getString(Constants.TAG_LUGGAGE);
                                            String plan = jsonObjectAnother.getString(Constants.TAG_PLAN);
                                            String wgender = jsonObjectAnother.getString(Constants.TAG_WGENDER);
                                            String userGender = jsonObjectAnother.getString(Constants.TAG_TRIP_USERGENDER);
                                            String userEvalua = jsonObjectAnother.getString(Constants.TAG_USER_EVALUATION);

                                            Log.d("GET TRIP", "onResponse: "+user_id + origin+destination+date
                                            +time+typevehicle+position+seatprice+emptyseat+fullseat+service+luggage
                                            +plan+wgender+userGender+userEvalua);

                                            Intent intent = new Intent(context,HomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            //send user info
                                            intent.putExtra(Constants.TAG_USERID,String.valueOf(MainActivity.userLocal.getUser_id()));
                                            intent.putExtra(Constants.TAG_USERNAME,MainActivity.userLocal.getName());
                                            intent.putExtra(Constants.TAG_USERBIRTH,MainActivity.userLocal.getBirth());
                                            intent.putExtra(Constants.TAG_USERPHONENUMBER,MainActivity.userLocal.getPhonenumber());
                                            intent.putExtra(Constants.TAG_USERGENDER,MainActivity.userLocal.getGender());
                                            intent.putExtra(Constants.TAG_USEREMAIL,MainActivity.userLocal.getEmail());
                                            intent.putExtra(Constants.TAG_USERSTATUS,MainActivity.userLocal.getStatus());
                                            intent.putExtra(Constants.TAG_EVALUATION,String.valueOf(MainActivity.userLocal.getEvaluation()));
                                            //send trip info
                                            intent.putExtra(Constants.TAG_TRIPID,String.valueOf(trip_ID));
                                            intent.putExtra(Constants.TAG_TRIPUSERID,String.valueOf(user_id));
                                            intent.putExtra(Constants.TAG_ORIGIN, origin);
                                            intent.putExtra(Constants.TAG_DESTINATION, destination);
                                            intent.putExtra(Constants.TAG_DATE, date);
                                            intent.putExtra(Constants.TAG_TIME, time);
                                            intent.putExtra(Constants.TAG_TYPEVEHICLE, typevehicle);
                                            intent.putExtra(Constants.TAG_POSITION, position);
                                            intent.putExtra(Constants.TAG_EMPTYSEAT, seatprice);
                                            intent.putExtra(Constants.TAG_FULLSEAT, emptyseat);
                                            intent.putExtra(Constants.TAG_SEATPRICE, fullseat);
                                            intent.putExtra(Constants.TAG_SERVICE, service);
                                            intent.putExtra(Constants.TAG_LUGGAGE, luggage);
                                            intent.putExtra(Constants.TAG_PLAN, plan);
                                            intent.putExtra(Constants.TAG_WGENDER, wgender);
                                            //send user id in trip active
                                            for (int i = 1; i<10; i++){
                                                if (!jsonObject.getString("userID"+i).equals("null")){
                                                    intent.putExtra("userID"+i,jsonObject.getString("userID"+i));
                                                }
                                            }
                                            context.startActivity(intent);

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
                                        hashMap.put(Constants.TAG_TRIPID,trip_ID);
                                        return hashMap;
                                    }
                                };
                                requestQueue = Volley.newRequestQueue(context);
                                requestQueue.add(request);
                            }
                        });
                        btnCancleJoinTrip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogHaveMessage.cancel();
                            }
                        });
                        tvMsg.setText(message);
                        dialogHaveMessage.setCancelable(false);
                        dialogHaveMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogHaveMessage.show();
                    }else{
                        Log.d("ERROR", "onResponse: "+jsonObject.getString(Constants.TAG_ERROR));
                        String message = "You don't have a message";
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
                hashMap.put(Constants.TAG_USERID,String.valueOf(userID));
                return hashMap;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }
}
