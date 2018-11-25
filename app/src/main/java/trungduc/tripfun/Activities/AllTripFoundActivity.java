package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadFindTripTask;

public class AllTripFoundActivity extends AppCompatActivity {
    private ListView lvallTrip;
    LoadFindTripTask loadfindtriptask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trip_found);
        Intent intent = getIntent();
        final String origin = intent.getStringExtra("ori");
        final String destination = intent.getStringExtra("des");
        lvallTrip = (ListView) findViewById(R.id.lvAllTrip_F);
        loadfindtriptask = new LoadFindTripTask(AllTripFoundActivity.this,lvallTrip,origin,destination);
        loadfindtriptask.execute();
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

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "Back button locked"+ loadfindtriptask.listTripdetails.size(), Toast.LENGTH_SHORT).show();

    }
}
