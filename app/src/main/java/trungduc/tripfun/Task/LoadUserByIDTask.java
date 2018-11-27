package trungduc.tripfun.Task;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trungduc.tripfun.Activities.HomeActivity;
import trungduc.tripfun.Activities.MainActivity;
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.Models.JSONParser;
import trungduc.tripfun.Models.User;


public class LoadUserByIDTask extends AsyncTask<String, String, String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jParser;
    public ArrayList<User> listUserDetails;
    JSONArray userdetails = null;
    String userID ;



    public LoadUserByIDTask(Context context,String userID) {
        this.context = context;
        this.userID = userID;
        jParser = new JSONParser();
        listUserDetails = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        pDialog = new ProgressDialog(context);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);
//        pDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        // Building Parameters
            List<HashMap<String, String>> params = new ArrayList<>();
            HashMap<String,String> hashMap = new HashMap<String, String>();
            hashMap.put("userID",userID);
            params.add(hashMap);

            JSONObject jsonObject =
                    jParser.makeHttpRequest(Constants.url_get_user, "POST", params);
            try {
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    Log.d("User: ", jsonObject.toString());
                    userdetails = jsonObject.getJSONArray(Constants.TAG_USERDETAILS);
                    JSONObject jsonObjectGet = userdetails.getJSONObject(0);
                    // Storing each json item in variable
                    //CHANGE ++++=================================
                    String user_id = jsonObjectGet.getString(Constants.TAG_USERID);
                    String user_name = jsonObjectGet.getString(Constants.TAG_USERNAME);
                    String user_birth = jsonObjectGet.getString(Constants.TAG_USERBIRTH);
                    String user_phonenumber = jsonObjectGet.getString(Constants.TAG_USERPHONENUMBER);
                    String user_gender = jsonObjectGet.getString(Constants.TAG_USERGENDER);
                    String user_email = jsonObjectGet.getString(Constants.TAG_USEREMAIL);
                    String user_status = jsonObjectGet.getString(Constants.TAG_USERSTATUS);

                    Log.d("LOG ORI DES: ", "doInBackground: "+user_id+" "+user_name+user_birth+user_phonenumber+user_gender+user_email+user_status);

                    User userdetail = new User();

                    userdetail.setUser_id(Integer.parseInt(user_id));
                    userdetail.setName(user_name);
                    userdetail.setBirth(user_birth);
                    userdetail.setPhonenumber(user_phonenumber);
                    userdetail.setGender(user_gender);
                    userdetail.setEmail(user_email);
                    userdetail.setStatus(user_status);

                    listUserDetails.add(userdetail);
                }
//            if (listUserDetails.size() == 0){
//                // no products found
//                // Launch Add New product Activity
//                Intent intent = new Intent(context,FindTripActivity.class);
//                // Closing all previous activities
//                context.startActivity(intent);
//            }
            }catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        if (pDialog.isShowing()) {
//            pDialog.dismiss();
//        }
        Intent intent = new Intent(context,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.TAG_USERID,String.valueOf(listUserDetails.get(0).getUser_id()));
        intent.putExtra(Constants.TAG_USERNAME,listUserDetails.get(0).getUsername());
        intent.putExtra(Constants.TAG_USERBIRTH,listUserDetails.get(0).getBirth());
        intent.putExtra(Constants.TAG_USERPHONENUMBER,listUserDetails.get(0).getPhonenumber());
        intent.putExtra(Constants.TAG_USERGENDER,listUserDetails.get(0).getGender());
        intent.putExtra(Constants.TAG_USEREMAIL,listUserDetails.get(0).getEmail());
        intent.putExtra(Constants.TAG_USERSTATUS,listUserDetails.get(0).getStatus());
        context.startActivity(intent);
    }
}
