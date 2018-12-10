package trungduc.tripfun.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Timer;
import java.util.TimerTask;

import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadUserByIDTask;
import trungduc.tripfun.Task.LoadUserTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "LoginActivity";
    private EditText edtUserName, edtPassword;
    private Button btnSignIn,btnSignUp,btnFBsignIn,btnGGsignIn;
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
                final String username = edtUserName.getText().toString();
                final String password = edtPassword.getText().toString();
                if (!username.equals("")&&!password.equals("")) {
                    request = new StringRequest(Request.Method.POST, Constants.url_user_control, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals(Constants.TAG_SUCCESS)) { //get name response after equals success
                                    Toast.makeText(LoginActivity.this, jsonObject.getString(Constants.TAG_SUCCESS), Toast.LENGTH_SHORT).show();

                                    LoadUserTask loadUserTask = new LoadUserTask(LoginActivity.this, username, password);
                                    loadUserTask.execute();
                                } else {
                                    Dialog dialogMessage = new Dialog(LoginActivity.this);
                                    dialogMessage.setContentView(R.layout.message_dialog);
                                    TextView tvMsg = dialogMessage.findViewById(R.id.tvMessageDialog);
                                    tvMsg.setText(jsonObject.getString(Constants.TAG_ERROR));
                                    dialogMessage.setCancelable(true);
                                    dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialogMessage.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put(Constants.TAG_USER_USERNAME, edtUserName.getText().toString());
                            hashMap.put(Constants.TAG_USER_PASSWORD, edtPassword.getText().toString());
                            hashMap.put("login", "yes");
                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                }else{
                        Dialog dialogMessage = new Dialog(LoginActivity.this);
                        dialogMessage.setContentView(R.layout.message_dialog);
                        TextView tvMsg = dialogMessage.findViewById(R.id.tvMessageDialog);
                        dialogMessage.setCancelable(true);
                        dialogMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    if (username.equals("")){
                        tvMsg.setText("Tên tài khoản không được để trống!");
                        dialogMessage.show();
                    }else if(password.equals("")){
                        tvMsg.setText("Mật khẩu không được để trống!");
                        dialogMessage.show();
                    }
                }
                break;
            case R.id.btnSignUp:
                Log.d(TAG, "onClick: sign up");
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_FBsign:
                Log.d(TAG, "onClick: login by facebook");
                final Dialog dialogFBClick = new Dialog(LoginActivity.this);
                dialogFBClick.setContentView(R.layout.have_message_dialog);
                TextView tvMessage = (TextView) dialogFBClick.findViewById(R.id.tvMessage);
                tvMessage.setText("Xin lỗi!\nHiện tại chúng tôi chưa hổ trợ đăng nhập bằng FACE BOOK\nBạn có thể đăng ký tài khoản rất nhanh!");
                Button btnSignUpFB = (Button) dialogFBClick.findViewById(R.id.btnJoinTrip);
                btnSignUpFB.setText("Đăng ký tài khoản");
                btnSignUpFB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFBClick.cancel();
                        Log.d(TAG, "onClick: sign up");
                        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                Button btnCancelGG = (Button) dialogFBClick.findViewById(R.id.btnCancleJoinTrip);
                btnCancelGG.setText("Hủy");
                btnCancelGG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFBClick.cancel();
                    }
                });
                dialogFBClick.setCancelable(false);
                dialogFBClick.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogFBClick.show();
                break;
            case R.id.btn_GGsign:
                Log.d(TAG, "onClick: login by gmail");
                final Dialog dialogGGClick = new Dialog(LoginActivity.this);
                dialogGGClick.setContentView(R.layout.have_message_dialog);
                TextView tvMessageGG = (TextView) dialogGGClick.findViewById(R.id.tvMessage);
                tvMessageGG.setText("Xin lỗi!\nHiện tại chúng tôi chưa hổ trợ đăng nhập bằng GMAIL\nBạn có thể đăng ký tài khoản rất nhanh!");
                Button btnSignUpGG = (Button) dialogGGClick.findViewById(R.id.btnJoinTrip);
                btnSignUpGG.setText("Đăng ký tài khoản");
                btnSignUpGG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogGGClick.cancel();
                        Log.d(TAG, "onClick: sign up");
                        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                Button btnCancleGG = (Button) dialogGGClick.findViewById(R.id.btnCancleJoinTrip);
                btnCancleGG.setText("Hủy");
                btnCancleGG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogGGClick.cancel();
                    }
                });
                dialogGGClick.setCancelable(false);
                dialogGGClick.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogGGClick.show();
                break;
        }
    }

    private void handle(){
        edtUserName = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnFBsignIn = (Button) findViewById(R.id.btn_FBsign);
        btnGGsignIn = (Button) findViewById(R.id.btn_GGsign);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnFBsignIn.setOnClickListener(this);
        btnGGsignIn.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
    }
}
