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


public class UserInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);
        TextView tvName = (TextView) v.findViewById(R.id.tvName_FU);
        TextView tvBirth = (TextView) v.findViewById(R.id.tvBirth_FU);
        TextView tvGender = (TextView) v.findViewById(R.id.tvGender_FU);
        TextView tvPhonenumber = (TextView) v.findViewById(R.id.tvPhonenumber_FU);
        TextView tvEmail = (TextView) v.findViewById(R.id.tvEmail_FU);
        TextView tvEvaluation = (TextView) v.findViewById(R.id.tvEvalua_FU);

        tvName.setText(HomeActivity.userLocal.getName());
        tvBirth.setText(HomeActivity.userLocal.getBirth());
        tvGender.setText(HomeActivity.userLocal.getGender());
        tvPhonenumber.setText(HomeActivity.userLocal.getPhonenumber());
        tvEmail.setText(HomeActivity.userLocal.getEmail());
        tvEvaluation.setText(String.valueOf(HomeActivity.userLocal.getEvaluation()));

        return v;
    }


}