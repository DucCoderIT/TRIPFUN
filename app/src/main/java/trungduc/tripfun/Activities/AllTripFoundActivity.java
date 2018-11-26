package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadFindTripTask;

public class AllTripFoundActivity extends AppCompatActivity {
    private String TAG = "AllTripFoundActivity";
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
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
