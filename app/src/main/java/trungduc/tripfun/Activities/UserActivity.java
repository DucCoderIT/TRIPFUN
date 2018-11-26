package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadFindTripTask;
import trungduc.tripfun.Task.LoadUserTask;

public class UserActivity extends AppCompatActivity {
    LoadUserTask loadUserTask;
    LoadFindTripTask loadFindTripTask;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        final String origin = intent.getStringExtra("ori");
        final String destination = intent.getStringExtra("des");
        listView = (ListView) findViewById(R.id.lvAllTrip_U) ;
        //loadUserTask = new LoadUserTask(UserActivity.this,"a","a");
        //loadUserTask.execute();
//        loadFindTripTask = new LoadFindTripTask(UserActivity.this,listView,"hue","da nang");
//        loadFindTripTask.execute();
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
        super.onBackPressed();
    }
}
