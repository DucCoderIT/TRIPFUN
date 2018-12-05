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
        ImageButton btnCall = (ImageButton) v.findViewById(R.id.btnCall);
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
                break;
            case R.id.btnMessage:
                break;
            case R.id.btnCancleTrip:
                break;
        }
    }
}