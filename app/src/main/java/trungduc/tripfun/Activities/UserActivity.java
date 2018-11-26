package trungduc.tripfun.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import trungduc.tripfun.R;
import trungduc.tripfun.Task.LoadUserTask;

public class UserActivity extends AppCompatActivity {
    LoadUserTask loadUserTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        final String origin = intent.getStringExtra("ori");
        final String destination = intent.getStringExtra("des");
        loadUserTask = new LoadUserTask(UserActivity.this);
        loadUserTask.execute();
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
