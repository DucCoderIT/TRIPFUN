package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import trungduc.tripfun.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "MainActivity";
    private Button btnUpTrip_H,btnFindTrip_H; // _H = _Home
    private int countPressBack = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handle();
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
        countPressBack += 1;
        if (countPressBack == 2){
            finish();
            moveTaskToBack(true);
        }else Toast.makeText(this, "Nhấn thêm 1 lần để thoát!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUpTrip_H:
                startActivity(new Intent(getApplicationContext(),UpTripActivity.class));
                break;
            case R.id.btnFindTrip_H:
                startActivity(new Intent(getApplicationContext(),FindTripActivity.class));
                break;
        }
    }
    private void handle(){
        btnUpTrip_H = (Button) findViewById(R.id.btnUpTrip_H);
        btnFindTrip_H = (Button) findViewById(R.id.btnFindTrip_H);
        btnUpTrip_H.setOnClickListener(this);
        btnFindTrip_H.setOnClickListener(this);
    }
}
