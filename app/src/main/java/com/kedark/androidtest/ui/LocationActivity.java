package com.kedark.androidtest.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kedark.androidtest.R;
import com.kedark.androidtest.core.API;
import com.kedark.androidtest.model.FromCentral;
import com.kedark.androidtest.model.Location;
import com.kedark.androidtest.model.TransportLocation;
import com.kedark.androidtest.util.CustomException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    List<TransportLocation> transportLoactionList = new ArrayList<>();
    Spinner spinner;
    Location location;
    Button btnNavigate;
    LinearLayout layoutTransportInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        spinner = (Spinner) findViewById(R.id.spinner_location);
        btnNavigate = (Button) findViewById(R.id.btn_navigate);
        layoutTransportInfo = (LinearLayout) findViewById(R.id.layout_transport_info);
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location!=null) {
                    Intent intent = new Intent(LocationActivity.this, MapsActivity.class);
                    intent.putExtra("location", location);
                    startActivity(intent);
                }
            }
        });


        pDialog = new ProgressDialog(LocationActivity.this);
        pDialog.setMessage(LocationActivity.this.getResources().getString(R.string.loader));
        pDialog.setCancelable(false);
        pDialog.show();
        API.getData(LocationActivity.this, new API.OnDataFetcchedCallback() {

            @Override
            public void onSuccess(JSONArray successResponse) {
                pDialog.hide();
                List<FromCentral> fromCentralList = new ArrayList<>();
                try {

                    Location location;
                    for (int i = 0; i < successResponse.length(); i++) {
                        TransportLocation transportLoaction = new TransportLocation();
                        JSONObject jsonObject = successResponse.getJSONObject(i);
                        transportLoaction.setId(jsonObject.getInt("id"));
                        transportLoaction.setName(jsonObject.getString("name"));
                        JSONObject jsonObject1 = jsonObject.getJSONObject("fromcentral");
                        try {
                            FromCentral fromCentral = new FromCentral();
                            if (jsonObject1.has("car")) {
                                fromCentral.setCarMins(jsonObject1.getString("car"));
                            }
                            if (jsonObject1.has("train")) {
                                fromCentral.setTrainMins(jsonObject1.getString("train"));
                            }
                            fromCentralList.add(fromCentral);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        transportLoaction.setFromCentral(fromCentralList);
                        JSONObject jsonLocation = jsonObject.getJSONObject("location");
                        location = new Location();

                        location.setLatitude(jsonLocation.getDouble("latitude"));
                        location.setLongitude(jsonLocation.getDouble("longitude"));
                        transportLoaction.setLocation(location);
                        transportLoactionList.add(transportLoaction);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                inItViews();
            }

            @Override
            public void onFailure(CustomException e) {
                pDialog.hide();
                Toast.makeText(LocationActivity.this, "Sorry something went wrong.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inItViews() {
        final List<String> locations = new ArrayList<>();
        for (int i = 0; i < transportLoactionList.size(); i++) {
            locations.add(transportLoactionList.get(i).getName());
        }
        ArrayAdapter<String> adp = new ArrayAdapter<>(LocationActivity.this, android.R.layout.simple_spinner_dropdown_item, locations);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layoutTransportInfo.removeAllViews();
                FromCentral fromCentral = transportLoactionList.get(position).getFromCentral().get(position);
                View viewTransportInfo = inflater.inflate(R.layout.row, null);
                TextView lblTrainTransportMode = (TextView) viewTransportInfo.findViewById(R.id.lbl_train_transport);
                TextView lblCarTransportMode = (TextView) viewTransportInfo.findViewById(R.id.lbl_car_transport);
                TextView lblCarTransportTime = (TextView) viewTransportInfo.findViewById(R.id.lbl_car_transport_time);
                TextView lblTrainTransportTime = (TextView) viewTransportInfo.findViewById(R.id.lbl_train_transport_time);
                if (fromCentral.getCarMins() != null && !fromCentral.getCarMins().equals("")) {
                    lblCarTransportMode.setText("Car");
                    lblCarTransportTime.setText(fromCentral.getCarMins());
                }
                if (fromCentral.getTrainMins() != null && !fromCentral.getTrainMins().equals("")) {
                    lblTrainTransportMode.setText("Train");
                    lblTrainTransportTime.setText(fromCentral.getTrainMins());
                }
                layoutTransportInfo.addView(viewTransportInfo);
                location = new Location();
                location.setLongitude(transportLoactionList.get(position).getLocation().getLongitude());
                location.setLatitude(transportLoactionList.get(position).getLocation().getLatitude());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
