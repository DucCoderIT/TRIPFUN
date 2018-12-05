package trungduc.tripfun.Task;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trungduc.tripfun.Activities.FindTripActivity;
import trungduc.tripfun.Activities.HomeActivity;
import trungduc.tripfun.Activities.ShowTripDetailsActivity;
import trungduc.tripfun.Adapters.TripdetailsAdapter;
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.Models.JSONParser;
import trungduc.tripfun.Models.Tripdetails;


public class LoadAllTripByUserIDTask extends AsyncTask<String, String, String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jParser;
    public ArrayList<Tripdetails> listTripdetails;
    JSONArray tripdetails = null;
    String userID;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public LoadAllTripByUserIDTask(Context context,  String userID, ArrayList<Tripdetails> listTripdetails) {
        this.userID = userID;
        this.context = context;
        this.listTripdetails = listTripdetails;
        jParser = new JSONParser();
        this.listTripdetails = new ArrayList<>();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("userID",userID);
        params.add(hashMap);
        //new Jsonobject
        JSONObject jsonObject =
                jParser.makeHttpRequest(Constants.url_all_tripdetails_by_userid, "POST", params);
        try {
            int success = jsonObject.getInt("success");
            if (success == 1) {
                Log.d("All Tripdetails: ", jsonObject.toString());
                tripdetails = jsonObject.getJSONArray(Constants.TAG_TRIPDETAILS);
                for (int i = 0; i < tripdetails.length(); i++) {
                    JSONObject jsonObjectGet = tripdetails.getJSONObject(i);
                    // Storing each json item in variable
                    //============= get value from json object ====================
                    String trip_id = jsonObjectGet.getString(Constants.TAG_TRIPID);
                    String user_id = jsonObjectGet.getString(Constants.TAG_USERID);
                    String origin = jsonObjectGet.getString(Constants.TAG_ORIGIN);
                    String destination = jsonObjectGet.getString(Constants.TAG_DESTINATION);
                    String date = jsonObjectGet.getString(Constants.TAG_DATE);
                    String time = jsonObjectGet.getString(Constants.TAG_TIME);
                    String typevehicle = jsonObjectGet.getString(Constants.TAG_TYPEVEHICLE);
                    String position = jsonObjectGet.getString(Constants.TAG_POSITION);
                    String seatprice = jsonObjectGet.getString(Constants.TAG_SEATPRICE);
                    String emptyseat = jsonObjectGet.getString(Constants.TAG_EMPTYSEAT);
                    String fullseat = jsonObjectGet.getString(Constants.TAG_FULLSEAT);
                    String service = jsonObjectGet.getString(Constants.TAG_SERVICE);
                    String luggage = jsonObjectGet.getString(Constants.TAG_LUGGAGE);
                    String plan = jsonObjectGet.getString(Constants.TAG_PLAN);
                    String wgender = jsonObjectGet.getString(Constants.TAG_WGENDER);
                    String userGender = jsonObjectGet.getString(Constants.TAG_TRIP_USERGENDER);
                    String userEvalua = jsonObjectGet.getString(Constants.TAG_USER_EVALUATION);

                        //format date to dd/MM/yyyy
                        String[] separated = date.split("-");
                        String myDate= separated[2] +"/" +separated[1]+"/" +separated[0];
                        java.util.Date ParseDate = format.parse(myDate);
                    // creating new tripdetail
                        Tripdetails tripdetail = new Tripdetails();
                        tripdetail.setTripID(Integer.parseInt(trip_id));
                        tripdetail.setUserID(Integer.parseInt(user_id));
                        tripdetail.setOrigin(origin);
                        tripdetail.setDestination(destination);
                        tripdetail.setDate(ParseDate);
                        tripdetail.setTime(Time.valueOf(time));
                        tripdetail.setTypevehicle(typevehicle);
                        tripdetail.setPosition(position);
                        tripdetail.setSeatprice(Integer.parseInt(seatprice));
                        tripdetail.setEmptyseat(Integer.parseInt(emptyseat));
                        tripdetail.setFullseat(Integer.parseInt(fullseat));
                        tripdetail.setService(service);
                        tripdetail.setLuggage(luggage);
                        tripdetail.setPlan(plan);
                        tripdetail.setWgender(wgender);
                        tripdetail.setGender(userGender);
                        tripdetail.setEvaluation(userEvalua);

                        listTripdetails.add(tripdetail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        HomeActivity.listTripdetails = listTripdetails;
    }
}
