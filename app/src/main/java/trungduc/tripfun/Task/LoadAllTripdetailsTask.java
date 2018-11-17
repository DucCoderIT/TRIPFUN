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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trungduc.tripfun.Activities.MapsActivity;
import trungduc.tripfun.Activities.TripDetailsActivity;
import trungduc.tripfun.Adapters.TripdetailsAdapter;
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.Models.JSONParser;
import trungduc.tripfun.Models.Tripdetails;


/**
 * Created by Han on 29/12/2016.
 */
public class LoadAllTripdetailsTask extends AsyncTask<String, String, String> {
    Context context;
    ListView lvTripdetails;
    ProgressDialog pDialog;
    JSONParser jParser;
    ArrayList<Tripdetails> listTripdetails;
    JSONArray tripdetails = null;
    TripdetailsAdapter tripdetailsAdapter;
    public LoadAllTripdetailsTask(Context context, ListView lvTripdetails) {
        this.context = context;
        this.lvTripdetails = lvTripdetails;
        jParser = new JSONParser();
        listTripdetails = new ArrayList<>();
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
        JSONObject jsonObject =
                jParser.makeHttpRequest(Constants.url_all_tripdetails, "GET", params);
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
                    String origin = jsonObjectGet.getString(Constants.TAG_ORIGIN);
                    String destination = jsonObjectGet.getString(Constants.TAG_DESTINATION);
                    String typevehicle = jsonObjectGet.getString(Constants.TAG_TYPEVEHICLE);
                    String seatprice = jsonObjectGet.getString(Constants.TAG_SEATPRICE);
                    // creating new tripdetail
                    Tripdetails tripdetail = new Tripdetails();
                    tripdetail.setTripID(Integer.parseInt(trip_id));
                    tripdetail.setOrigin(origin);
                    tripdetail.setDestination(destination);
                    tripdetail.setTypevehicle(typevehicle);
                    tripdetail.setSeatprice(Integer.parseInt(seatprice));
                    listTripdetails.add(tripdetail);
                }

            } else {
//                // no products found
//                // Launch Add New product Activity
//                Intent intent = new Intent(context,AddProductActivity.class);
//                // Closing all previous activities
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
                Toast.makeText(context, "Không có chuyến đi nào tồn tại!", Toast.LENGTH_SHORT).show();
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
                int trip_id = listTripdetails.get(i).getTripID();
                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra(Constants.TAG_TRIPID, trip_id);
                ((Activity) context).startActivityForResult(intent, 100);
            }
        });
    }
}
