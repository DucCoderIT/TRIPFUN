package trungduc.tripfun.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import trungduc.tripfun.Activities.LoginActivity;
import trungduc.tripfun.Activities.MainActivity;
import trungduc.tripfun.Models.Constants;

public class LoginTask extends AsyncTask<String, String, String> {
    Context context;
    Constants constants;
    StringRequest request;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EditText edtPassword,edtUserName;

    public LoginTask(Context context,  EditText edtUserName, EditText edtPassword) {
        this.context = context;
        this.edtPassword = edtPassword;
        this.edtUserName = edtUserName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
