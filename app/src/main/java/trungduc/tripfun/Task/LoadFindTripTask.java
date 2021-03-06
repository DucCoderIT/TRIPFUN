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
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import trungduc.tripfun.Activities.FindTripActivity;
import trungduc.tripfun.Activities.ShowTripDetailsActivity;
import trungduc.tripfun.Adapters.TripdetailsAdapter;
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.Models.JSONParser;
import trungduc.tripfun.Models.Tripdetails;


public class LoadFindTripTask extends AsyncTask<String, String, String> {
    Activity context;
    ListView lvTripdetails;
    ProgressDialog pDialog;
    JSONParser jParser;
    public ArrayList<Tripdetails> listTripdetails;
    JSONArray tripdetails = null;
    TripdetailsAdapter tripdetailsAdapter;
    String origin_find, destination_find;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

    public LoadFindTripTask(Activity context, ListView lvTripdetails,String origin_find,String destination_find) {
        this.origin_find = origin_find;
        this.destination_find = destination_find;
        this.context = context;
        this.lvTripdetails = lvTripdetails;
        jParser = new JSONParser();
        listTripdetails = new ArrayList<>();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Vui lòng đợi...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("origin",origin_find);
        HashMap<String,String> hashMap2 = new HashMap<String,String>();
        hashMap2.put("destiantion",destination_find);
        params.add(hashMap);
        params.add(hashMap2);
        //new Jsonobject
        JSONObject jsonObject =
                jParser.makeHttpRequest(Constants.url_all_tripdetails_by_ori_des, "POST", params);
        try {
            int success = jsonObject.getInt("success");
            if (success == 1) {
                Log.d("All Tripdetails: ", jsonObject.toString());
                tripdetails = jsonObject.getJSONArray(Constants.TAG_TRIPDETAILS);
                for (int i = 0; i < tripdetails.length(); i++) {
                    JSONObject jsonObjectGet = tripdetails.getJSONObject(i);
                    // Storing each json item in variable
                    //CHANGE ++++=================================
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

                        // creating new tripdetail
                        String[] separated = date.split("-");
                        String myDate= separated[2] +"/" +separated[1]+"/" +separated[0];
                        java.util.Date ParseDate = format.parse(myDate);
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
                    Log.d("LOG", "doInBackground: "+userGender+userEvalua);
                        listTripdetails.add(tripdetail);
                }
            } if (listTripdetails.size() == 0){
                // no products found
                // Launch Add New product Activity
                Intent intent = new Intent(context,FindTripActivity.class);
                // Closing all previous activities
                context.startActivity(intent);
                context.finish();
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
        tripdetailsAdapter = new TripdetailsAdapter(context, listTripdetails);
        lvTripdetails.setAdapter(tripdetailsAdapter);
        lvTripdetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int i, long l)
            {
                String trip_id = String.valueOf(listTripdetails.get(i).getTripID());
                String trip_user_id = String.valueOf(listTripdetails.get(i).getUserID());
                String trip_ori = listTripdetails.get(i).getOrigin();
                String trip_des = listTripdetails.get(i).getDestination();
                //format date and time to dd/MM/yyyy & HH:mm
                String trip_date = format.format(listTripdetails.get(i).getDate());
                String trip_time = formatTime.format(listTripdetails.get(i).getTime());
                String trip_vehicle = listTripdetails.get(i).getTypevehicle();
                String trip_position = listTripdetails.get(i).getPosition();
                String trip_emptyseat = String.valueOf(listTripdetails.get(i).getEmptyseat());
                String trip_fullseat = String.valueOf(listTripdetails.get(i).getFullseat());
                String trip_seatprice = String.valueOf(listTripdetails.get(i).getSeatprice());
                String trip_service = listTripdetails.get(i).getService();
                String trip_luggage = listTripdetails.get(i).getLuggage();
                String trip_plan = listTripdetails.get(i).getPlan();
                String trip_wgender = listTripdetails.get(i).getWgender();

                Intent intent = new Intent(context,ShowTripDetailsActivity.class);
                intent.putExtra(Constants.TAG_TRIPID, trip_id);
                intent.putExtra(Constants.TAG_USERID, trip_user_id);
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

                ((Activity) context).startActivityForResult(intent, 100);
            }
        });
    }
}
