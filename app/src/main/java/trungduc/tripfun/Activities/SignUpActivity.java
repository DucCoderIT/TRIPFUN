package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "SignUpActivity";
    private int countPressBack = 0;
    private Button btnSignIn,btnCreateUser;
    private EditText edtName_SUp,edtUsername_SUp,edtEmail_SUp,edtPhoneNumber_SUp,
                        edtPassword_SUp,edtBrith_SUp,edtGender_SUp; // SUp = 'Sign Up';
    private RequestQueue requestQueue;
    private StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        handle();
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
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onBackPressed(){
        countPressBack += 1;
        if (countPressBack == 2){
            finish();
            moveTaskToBack(true);
        }else Toast.makeText(this, "Nhấn thêm 1 lần để thoát!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCreateUser:
                final String name = edtName_SUp.getText().toString();
                final String birth = edtBrith_SUp.getText().toString();
                final String phonenumber = edtPhoneNumber_SUp.getText().toString();
                final String gender = edtGender_SUp.getText().toString();
                final String username = edtUsername_SUp.getText().toString();
                final String password = edtPassword_SUp.getText().toString();
                final String email = edtEmail_SUp.getText().toString();
                if (!(name.equals("") && email.equals("")&& username.equals("") && password.equals("") && phonenumber.equals("")
                        && birth.equals("") && gender.equals(""))){
                    request = new StringRequest(Request.Method.POST, Constants.url_user_control, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals(Constants.TAG_SUCCESS)){
                                    Toast.makeText(SignUpActivity.this, jsonObject.getString(Constants.TAG_SUCCESS), Toast.LENGTH_SHORT).show();
                                    goBackSignIn(username,password);
                                }else{
                                    Toast.makeText(SignUpActivity.this, jsonObject.getString(Constants.TAG_ERROR), Toast.LENGTH_SHORT).show();
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
                            hashMap.put("name",name);
                            hashMap.put("birth",birth);
                            hashMap.put("phonenumber",phonenumber);
                            hashMap.put("gender",gender);
                            hashMap.put("username",username);
                            hashMap.put("password",password);
                            hashMap.put("email",email);
                            hashMap.put("createUser","Yes");
                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                }
                break;
            case R.id.btnSignIn_SUp:
                goBackSignIn("","");
                break;
        }
    }


    private void handle(){
        btnSignIn = (Button) findViewById(R.id.btnSignIn_SUp);
        btnCreateUser = (Button) findViewById(R.id.btnCreateUser);
        edtName_SUp = (EditText) findViewById(R.id.edtName_SUp);
        edtPhoneNumber_SUp = (EditText) findViewById(R.id.edtPhoneNumber_SUp);
        edtUsername_SUp = (EditText) findViewById(R.id.edtUsername_SUp);
        edtEmail_SUp = (EditText) findViewById(R.id.edtEmail_SUp);
        edtPhoneNumber_SUp = (EditText) findViewById(R.id.edtPhoneNumber_SUp);
        edtPassword_SUp = (EditText) findViewById(R.id.edtPassword_SUp);
        edtBrith_SUp = (EditText) findViewById(R.id.edtBirth_SUp);
        edtGender_SUp = (EditText) findViewById(R.id.edtGender_SUp);
        btnCreateUser.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
    }
    private void goBackSignIn(String username,String password){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        startActivity(intent);
        finish();
    }
}
