package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadAllTripdetailsTask;

public class TripDetailsActivity extends AppCompatActivity {
    private ListView lvallTrip;
    LoadAllTripdetailsTask loadAllTripdetailsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        lvallTrip = (ListView) findViewById(R.id.lvAllTrip);
        loadAllTripdetailsTask = new LoadAllTripdetailsTask(TripDetailsActivity.this,lvallTrip);
        loadAllTripdetailsTask.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
}
