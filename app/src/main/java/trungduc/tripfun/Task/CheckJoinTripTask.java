package trungduc.tripfun.Task;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class CheckJoinTripTask extends AsyncTask<String,String,String> {

    private Context context;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    public CheckJoinTripTask(Context context) {
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Checking join trip!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
