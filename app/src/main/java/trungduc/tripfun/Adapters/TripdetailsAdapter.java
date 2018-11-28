package trungduc.tripfun.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import trungduc.tripfun.R;
import trungduc.tripfun.Models.Tripdetails;


public class TripdetailsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Tripdetails> listTripdetails;
    public TripdetailsAdapter(Context context, ArrayList<Tripdetails> listTripdetails)
    {
        this.context = context;
        this.listTripdetails = listTripdetails;
    }
    @Override
    public int getCount() {
        return listTripdetails.size();
    }
    @Override
    public Object getItem(int i) {
        return listTripdetails.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    public static class ViewHolder {
        //CHANGE--------------------
        TextView tvTripID,tvOri,tvDes,tvVehicle,tvSeatPrice,tvDriveGender,tvEvalua;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item, null);
            /////CHANGE----------================================
            viewHolder.tvTripID = (TextView) view.findViewById(R.id.tvTripID);
            viewHolder.tvOri = (TextView) view.findViewById(R.id.tvOri);
            viewHolder.tvDes = (TextView) view.findViewById(R.id.tvDes);
            viewHolder.tvVehicle = (TextView) view.findViewById(R.id.tvVehicle);
            viewHolder.tvSeatPrice = (TextView) view.findViewById(R.id.tvSeatPrice);
            viewHolder.tvDriveGender = (TextView) view.findViewById(R.id.tvDriveGender);
            viewHolder.tvEvalua = (TextView) view.findViewById(R.id.tvEvalua);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        ///CHANGE =========================================
        Tripdetails tripdetails = listTripdetails.get(i);
        viewHolder.tvTripID.setText(tripdetails.getTripID()+"");
        viewHolder.tvOri.setText(tripdetails.getOrigin());
        viewHolder.tvDes.setText(tripdetails.getDestination());
        viewHolder.tvVehicle.setText(tripdetails.getTypevehicle());
        viewHolder.tvSeatPrice.setText(tripdetails.getSeatprice()+"");
        viewHolder.tvDriveGender.setText(tripdetails.getGender());
        viewHolder.tvEvalua.setText(tripdetails.getEvaluation());
        return view;
    }
}

