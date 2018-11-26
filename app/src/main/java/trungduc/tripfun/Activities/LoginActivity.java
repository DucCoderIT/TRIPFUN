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
import trungduc.tripfun.Task.LoadUserByIDTask;
import trungduc.tripfun.Task.LoadUserTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "LoginActivity";
    private EditText edtUserName, edtPassword;
    private Button btnSignIn,btnSignUp;
    private RequestQueue requestQueue;
    private StringRequest request;
    private int countPressBack = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handle(); // handle initialize
        if (getIntent() != null){
            Intent intent = getIntent();
            edtUserName.setText(intent.getStringExtra("username"));
            edtPassword.setText(intent.getStringExtra("password"));
        }
    }
    // override back button

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
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
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
            case R.id.btnSignIn:
                request = new StringRequest(Request.Method.POST, Constants.url_user_control, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")){ //get name response after equals success
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                final String username = edtUserName.getText().toString();
                                final String password = edtPassword.getText().toString();
                                LoadUserTask loadUserTask = new LoadUserTask(LoginActivity.this,username,password);
                                loadUserTask.execute();
                            }else{
                                Toast.makeText(getApplicationContext(), "Error"+
                                        jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
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
                        hashMap.put("username",edtUserName.getText().toString());
                        hashMap.put("password",edtPassword.getText().toString());
                        hashMap.put("login","yes");
                        return hashMap;
                    }
                };
                requestQueue.add(request);
                break;
            case R.id.btnSignUp:
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
        }
    }

    private void handle(){
        edtUserName = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
    }
}
