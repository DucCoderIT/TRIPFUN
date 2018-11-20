package trungduc.tripfun.Activities;


import android.Manifest;
import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trungduc.tripfun.Models.DirectionsParser;
import trungduc.tripfun.R;
import trungduc.tripfun.Adapters.PlaceAutocompleteAdapter;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private GoogleMap mMap;
    private View mapView;
    private GoogleApiClient mGoogleApiClient;
    private Marker mm_ori, mm_des; // create 2 marker
    private Polyline line_direction;
    private static final int LOCATION_REQUEST = 500;
    private static final float DEFAULT_ZOOM = 15f;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private AutoCompleteTextView mSearchTextOri,mSearchTextDes;
    private Boolean mLocationPermissionsGranted = false;
    private String TAG = "MapsActivity";
    public ArrayList<LatLng> listPoints;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));// position where appear when map ready


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mSearchTextOri =(AutoCompleteTextView) findViewById(R.id.edt_ori_search);
        mSearchTextDes =(AutoCompleteTextView) findViewById(R.id.edt_des_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        initMap();
        listPoints = new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);//set location true
        //create new latitude and longitude
        LatLng hue = new LatLng(16.0708414,108.2175421);
        //set camera
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(hue)      // Sets the center of the map to Mountain View
                .zoom(5)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //check permission
        //mMap.getUiSettings().setZoomControlsEnabled(true); //unable button zoom
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
        init();
   // EVENTS
        //lisener long click
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //Reset marker when already 2
                if (listPoints.size() == 2) {
                    listPoints.clear();
                    mMap.clear();
                }
                //Save first point select
                listPoints.add(latLng);
                //Create marker
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if (listPoints.size() == 1) {
                    //Add first marker to the map
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                } else {
                    //Add second marker to the map
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }
                mMap.addMarker(markerOptions);

                if (listPoints.size() == 2) {
                    //Create the URL to get request from first marker to second marker
                    String url = getRequestUrl(listPoints.get(0), listPoints.get(1));
                    Log.i(TAG, "url: "+url);
                    TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                    taskRequestDirections.execute(url);
                }
                Log.i(TAG, "list size: "+listPoints.get(0));
            }
        });
        //Listener click
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideSoftKeyboard(MapsActivity.this);
//                mm_ori.setPosition(latLng);
//                camera_ori(latLng);
            }
        });
    }


    private void geoLocate(AutoCompleteTextView mSearchText){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }
    }
    //GET DIRECTIONS
    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    private String getRequestUrl(LatLng origin, LatLng dest) {
        //Value of origin
        String str_org = "origin=" + origin.latitude +","+origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude+","+dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Apikey
        String apikey = "key="+getResources().getString(R.string.google_maps_key);
        //Build the full param
        String param = str_org +"&" +str_dest +"&"+sensor+"&"+mode+"&"+apikey;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;

        return url;
    }


    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Parse json here
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>> > {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat,lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }

            if (polylineOptions!=null) {
                mMap.addPolyline(polylineOptions);
            } else {
                Toast.makeText(getApplicationContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //-------------//
    //search location
    public void location_search(View view){
        switch (view.getId()){

            case R.id.search_ori:
                AutoCompleteTextView addressFieldOri = (AutoCompleteTextView) findViewById(R.id.edt_ori_search);
                String address_ori = addressFieldOri.getText().toString();

                List<Address> addressList_ori = null;
                if (mm_ori!=null){
                    mm_ori.remove();

                }
                MarkerOptions userMarkerOptions_ori = new MarkerOptions();

                if (!TextUtils.isEmpty(address_ori)){

                    hideSoftKeyboard(MapsActivity.this);
                    Geocoder geocoder_ori = new Geocoder(this);

                    try {
                        addressList_ori = geocoder_ori.getFromLocationName(address_ori, 6);

                        if (addressList_ori != null){
                            for (int i = 0; i < addressList_ori.size() ; i++) {
                                Address userAddress_ori = addressList_ori.get(i);
                                LatLng latLng_ori = new LatLng(userAddress_ori.getLatitude(),userAddress_ori.getLongitude());

                                userMarkerOptions_ori.position(latLng_ori);
                                userMarkerOptions_ori.title(address_ori);
                                userMarkerOptions_ori.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mm_ori = mMap.addMarker(userMarkerOptions_ori);
                                camera_move(latLng_ori);
                            }

                        }else {
                            Toast.makeText(this, "Khong tim thay vi tri nay...!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(this, "Vui long nhap ten vi tri can tim...!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.search_des:
                AutoCompleteTextView addressFieldDes = (AutoCompleteTextView) findViewById(R.id.edt_des_search);
                String address_des = addressFieldDes.getText().toString();

                List<Address> addressList_des = null;

                if (mm_des!=null){
                    mm_des.remove();

                }
                MarkerOptions userMarkerOptions_des = new MarkerOptions();

                if (!TextUtils.isEmpty(address_des)){

                    hideSoftKeyboard(MapsActivity.this);

                    Geocoder geocoder_des = new Geocoder(this);

                    try {
                        addressList_des = geocoder_des.getFromLocationName(address_des, 6);

                        if (addressList_des != null){
                            for (int i = 0; i < addressList_des.size() ; i++) {
                                Address userAddress_des = addressList_des.get(i);
                                LatLng latLng_des = new LatLng(userAddress_des.getLatitude(),userAddress_des.getLongitude());

                                userMarkerOptions_des.position(latLng_des);
                                userMarkerOptions_des.title(address_des);
                                userMarkerOptions_des.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                mm_des = mMap.addMarker(userMarkerOptions_des);
                                camera_move(latLng_des);
                            }

                        }else {
                            Toast.makeText(this, "Khong tim thay vi tri nay...!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(this, "Vui long nhap ten vi tri can tim...!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        if (mm_ori!= null && mm_des!=null) directions();
    }
    //move camera move
    public void camera_move(LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .tilt(30)
                .zoom(16)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    //draw directions
    public void directions(){
        if (line_direction!=null){
            line_direction.remove();
        }
        line_direction = mMap.addPolyline(new PolylineOptions()
        .add(new LatLng(mm_ori.getPosition().latitude,mm_ori.getPosition().longitude), new LatLng(mm_des.getPosition().latitude,mm_des.getPosition().longitude))
        .width(15)
        .color(Color.MAGENTA));
    }
   //init autocomplete
    private void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);
        if (mSearchTextOri.onCheckIsTextEditor()){
            mSearchTextOri.setAdapter(mPlaceAutocompleteAdapter);

            mSearchTextOri.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    //Log.d(TAG, "onEditorAction: ok");
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                            || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                        //Log.d(TAG, "onEditorAction: ok2");
                        //execute our method for searching
                        geoLocate(mSearchTextOri);
                    }
                    return false;
                }
            });
        }
        if (mSearchTextDes.onCheckIsTextEditor()){
            mSearchTextDes.setAdapter(mPlaceAutocompleteAdapter);

            mSearchTextDes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    //Log.d(TAG, "onEditorAction: ok");
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                            || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                        //Log.d(TAG, "onEditorAction: ok2");
                        //execute our method for searching
                        geoLocate(mSearchTextDes);
                    }

                    return false;
                }
            });
        }

    }

    //init map
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(MapsActivity.this);
    }

    //request permission
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_REQUEST:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }
    //hide keyboard
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
