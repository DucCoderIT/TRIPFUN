package trungduc.tripfun.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import trungduc.tripfun.Activities.HomeActivity;
import trungduc.tripfun.R;


public class ManagerTripFragment extends Fragment implements View.OnClickListener {
    private Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manager_trip, container, false);
        //new a dialog
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.info_trip_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //handle view
        TextView tvOriginFM = (TextView) v.findViewById(R.id.tvOrigin_FM);
        TextView tvDestinationFM = (TextView) v.findViewById(R.id.tvDestination_FM);
        Button btnTripDetailsFM = (Button) v.findViewById(R.id.btnTripDetailsFM);
        Button btnCall = (Button) v.findViewById(R.id.btnCall);
        ImageButton btnMessage = (ImageButton) v.findViewById(R.id.btnMessage);
        ImageButton btnCancleTrip = (ImageButton) v.findViewById(R.id.btnCancleTrip);
        //set on click
        btnTripDetailsFM.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnMessage.setOnClickListener(this);
        btnCancleTrip.setOnClickListener(this);
        //check trip detail is null or not null
        if (HomeActivity.tripdetail.getOrigin() !=  null){
            tvOriginFM.setText(HomeActivity.tripdetail.getOrigin());
            tvDestinationFM.setText(HomeActivity.tripdetail.getDestination());
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTripDetailsFM:
                dialog.show();
                break;
            case R.id.btnCall:
                Log.d("onclick", "onClick: callbtn");
                Intent intentCall = new Intent(Intent.ACTION_CALL);
                intentCall.setData(Uri.parse("tel:03698010106"));
                int checkPermission = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},0);
                }
                startActivity(intentCall);
                break;
            case R.id.btnMessage:
                Log.d("onclick", "onClick: msgbtn");
                Intent smsIntent =  new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto: 0398010106"));
                smsIntent.putExtra("sms_body","Hello guy");
                startActivity(smsIntent);

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
//                {
//                    String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getActivity()); // Need to change the build to API 19
//                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
//                    sendIntent.setType("text/plain");
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, "text");
//
//                    if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
//                    // any app that support this intent.
//                    {
//                        sendIntent.setPackage(defaultSmsPackageName);
//                    }
//                    startActivity(sendIntent);
//                }
//                else // For early versions, do what worked for you before.
//                {
//                    Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
//                    smsIntent.setType("vnd.android-dir/mms-sms");
//                    smsIntent.putExtra("address","phoneNumber");
//                    smsIntent.putExtra("sms_body","message");
//                    startActivity(smsIntent);
//                }
                break;
            case R.id.btnCancleTrip:
                Log.d("onclick", "onClick: cancle");
                break;
        }
    }
}