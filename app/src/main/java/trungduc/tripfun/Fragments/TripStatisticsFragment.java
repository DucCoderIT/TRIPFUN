package trungduc.tripfun.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import trungduc.tripfun.Activities.HomeActivity;
import trungduc.tripfun.Adapters.TripdetailsAdapter;
import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadAllTripByUserIDTask;


public class TripStatisticsFragment extends Fragment {
    private ListView listView;
    private TripdetailsAdapter tripdetailsAdapter;
    private static final String TAG = "ManagerTripFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_trip_statistics, container, false);
        listView = (ListView) view.findViewById(R.id.lvAllTripByUserID);

        if (HomeActivity.listTripdetails.size() > 0){
            Log.d(TAG, "onCreateView: "+HomeActivity.listTripdetails.get(0).getOrigin());
            tripdetailsAdapter = new TripdetailsAdapter(getActivity(),HomeActivity.listTripdetails);
            listView.setAdapter(tripdetailsAdapter);

        }else{
            Log.d(TAG, "onCreateView: Chua co chuyen di nao ca");
        }
        return view;
    }
}