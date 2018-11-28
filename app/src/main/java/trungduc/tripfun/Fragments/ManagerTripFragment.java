package trungduc.tripfun.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import trungduc.tripfun.Activities.HomeActivity;
import trungduc.tripfun.R;


public class ManagerTripFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manager_trip, container, false);

        TextView tvOriginFM = (TextView) v.findViewById(R.id.tvOrigin_FM);
        TextView tvDestinationFM = (TextView) v.findViewById(R.id.tvDestination_FM);

        if (HomeActivity.tripdetail.getOrigin() !=  null){
            tvOriginFM.setText(HomeActivity.tripdetail.getOrigin());
            tvDestinationFM.setText(HomeActivity.tripdetail.getDestination());
        }
        return v;
    }
}