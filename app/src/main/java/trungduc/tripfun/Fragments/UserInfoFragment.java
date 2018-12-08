package trungduc.tripfun.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import trungduc.tripfun.Models.Constants;
import trungduc.tripfun.R;


public class UserInfoFragment extends Fragment implements View.OnClickListener {
    private View v;
    private Button btnChangeUserInfo,btnChangePassword;
    private TextView tvName,tvBirth,tvGender,tvPhoneNumber,tvEmail,tvEvaluation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_user_info, container, false);

        ViewHandle();

        tvName.setText(HomeActivity.userLocal.getName());
        tvBirth.setText(HomeActivity.userLocal.getBirth());
        tvGender.setText(HomeActivity.userLocal.getGender());
        tvPhoneNumber.setText(HomeActivity.userLocal.getPhonenumber());
        tvEmail.setText(HomeActivity.userLocal.getEmail());
        tvEvaluation.setText(String.valueOf(HomeActivity.userLocal.getEvaluation()));

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //click button change user info===================
            case R.id.btnChangeUserInfo:
                final Dialog dialogChangeInfo = new Dialog(getActivity());
                dialogChangeInfo.setContentView(R.layout.change_info_dialog);
                //dialog handle
                final EditText edtName_change = (EditText) dialogChangeInfo.findViewById(R.id.edtName_change);
                final EditText edtBirth_change = (EditText) dialogChangeInfo.findViewById(R.id.edtBirth_change);
                final EditText edtGender_change = (EditText) dialogChangeInfo.findViewById(R.id.edtGender_change);
                final EditText edtEmail_change = (EditText) dialogChangeInfo.findViewById(R.id.edtEmail_change);
                final EditText edtPhoneNumber_change = (EditText) dialogChangeInfo.findViewById(R.id.edtPhoneNumber_change);
                Button btnChangeDone = (Button) dialogChangeInfo.findViewById(R.id.btnChangeDone);
                Button btnChangeCancel = (Button) dialogChangeInfo.findViewById(R.id.btnChangeCancel);
                btnChangeDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String Name = edtName_change.getText().toString();
                        final String Birth = edtBirth_change.getText().toString();
                        final String Gender = edtGender_change.getText().toString();
                        final String Email = edtEmail_change.getText().toString();
                        final String PhoneNumber = edtPhoneNumber_change.getText().toString();

                        if (Name.equals(HomeActivity.userLocal.getName())&&Birth.equals(HomeActivity.userLocal.getBirth())&&
                                Gender.equals(HomeActivity.userLocal.getGender())&&Email.equals(HomeActivity.userLocal.getEmail())&&
                                PhoneNumber.equals(HomeActivity.userLocal.getPhonenumber())){
                            dialogChangeInfo.cancel();
                        }else {
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_user_control, new Response.Listener<String>() {
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
                                        dialogChangeInfo.cancel();
                                    } catch (JSONException e) {e.printStackTrace();}
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) { }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put(Constants.TAG_USERID,String.valueOf(HomeActivity.userLocal.getUser_id()));
                                    hashMap.put(Constants.TAG_USERNAME,Name);
                                    hashMap.put(Constants.TAG_USERBIRTH,Birth);
                                    hashMap.put(Constants.TAG_USERGENDER,Gender);
                                    hashMap.put(Constants.TAG_USEREMAIL,Email);
                                    hashMap.put(Constants.TAG_USERPHONENUMBER,PhoneNumber);
                                    hashMap.put("update","yes");
                                    return hashMap;
                                }
                            };requestQueue.add(stringRequest);
                        }
                    }
                });
                btnChangeCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogChangeInfo.cancel();
                    }
                });
                //set value for edt in dialog
                edtName_change.setText(HomeActivity.userLocal.getName());
                edtBirth_change.setText(HomeActivity.userLocal.getBirth());
                edtGender_change.setText(HomeActivity.userLocal.getGender());
                edtEmail_change.setText(HomeActivity.userLocal.getEmail());
                edtPhoneNumber_change.setText(HomeActivity.userLocal.getPhonenumber());
                //show dialog
                dialogChangeInfo.setCancelable(false);
                dialogChangeInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogChangeInfo.show();
                break;
                //click button change password=============
            case R.id.btnChangePassword:
                final Dialog dialogChangePassWord = new Dialog(getActivity());
                dialogChangePassWord.setContentView(R.layout.change_password_dialog);
                //dialog handle
                final EditText edtOldPassword_change = (EditText) dialogChangePassWord.findViewById(R.id.edtOldPassword_change);
                final EditText edtNewPassword_change = (EditText) dialogChangePassWord.findViewById(R.id.edtNewPassword_change);
                final EditText edtReInput_change = (EditText) dialogChangePassWord.findViewById(R.id.edtReInput_change);
                Button btnChangePassWordDone = (Button) dialogChangePassWord.findViewById(R.id.btnChangePassWordDone);
                Button btnChangePassWordCancel = (Button) dialogChangePassWord.findViewById(R.id.btnChangePassWordCancel);
                btnChangePassWordDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String OldPassword = edtOldPassword_change.getText().toString();
                        final String NewPassword = edtNewPassword_change.getText().toString();
                        final String ReInputPassword = edtReInput_change.getText().toString();
                        if (NewPassword.equals(ReInputPassword)){
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_user_control, new Response.Listener<String>() {
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
                                        dialogChangePassWord.cancel();
                                    } catch (JSONException e) {e.printStackTrace();}
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) { }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put(Constants.TAG_USERID,String.valueOf(HomeActivity.userLocal.getUser_id()));
                                    hashMap.put("oldpassword",OldPassword);
                                    hashMap.put("newpassword",NewPassword);
                                    hashMap.put("updatePassword","yes");
                                    return hashMap;
                                }
                            };requestQueue.add(stringRequest);
                        } else{
                            Dialog dialog = new Dialog(getContext());
                            dialog.setContentView(R.layout.message_dialog);
                            TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMessageDialog);
                            tvMsg.setText("Mật khẩu nhập lại không trùng khớp!");
                            dialog.setCancelable(true);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                    }
                });
                btnChangePassWordCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogChangePassWord.cancel();
                    }
                });
                //show dialog
                dialogChangePassWord.setCancelable(false);
                dialogChangePassWord.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogChangePassWord.show();
                break;
        }
    }
    public void ViewHandle(){
        tvName = (TextView) v.findViewById(R.id.tvName_FU);
        tvBirth = (TextView) v.findViewById(R.id.tvBirth_FU);
        tvGender = (TextView) v.findViewById(R.id.tvGender_FU);
        tvPhoneNumber = (TextView) v.findViewById(R.id.tvPhonenumber_FU);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail_FU);
        tvEvaluation = (TextView) v.findViewById(R.id.tvEvalua_FU);
        btnChangeUserInfo = (Button) v.findViewById(R.id.btnChangeUserInfo);
        btnChangePassword = (Button) v.findViewById(R.id.btnChangePassword);

        btnChangeUserInfo.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
    }
}