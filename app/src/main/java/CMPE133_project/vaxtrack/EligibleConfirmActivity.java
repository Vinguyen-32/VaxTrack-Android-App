package CMPE133_project.vaxtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EligibleConfirmActivity extends AppCompatActivity {
    private LocationManager mLocationManager;
    private double longitude;
    private double latitude;
    private String vaccineData;
    private String id;
    private String name;
    private String dob;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligible_confirm);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        dob = intent.getStringExtra("dob");
        address = intent.getStringExtra("address");
        vaccineData = intent.getStringExtra("vaccineData");

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        SearchView svSearchLocation = findViewById(R.id.svSearchLocation);
        TextView tvCurrentLocation = findViewById(R.id.tvCurrentLocation);

        Button btnCon = findViewById(R.id.btnCon);
        Button btnCan = findViewById(R.id.btnCan);

        ProgressBar ld_current_location = findViewById(R.id.ld_current_location);

        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(EligibleConfirmActivity.this, QuestionnaireActivity.class);
                startActivity(intentBack);
            }
        });

        svSearchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                double[] coordination = LocationService.geocodeByZipcode(getApplicationContext(), svSearchLocation.getQuery().toString());
                if (coordination != null) {
                    longitude = coordination[0];
                    latitude = coordination[1];
                    System.out.println("Longtitude: " + coordination[0]);
                    System.out.println("Latitude: " + coordination[1]);
                    btnCon.setEnabled(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        tvCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String provider = mLocationManager.getBestProvider(new Criteria(), false);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EligibleConfirmActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            99);
                }
                ld_current_location.setVisibility(View.VISIBLE);
                mLocationManager.requestLocationUpdates(provider, 400, 1, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        System.out.println("Longtitude: " + location.getLongitude());
                        System.out.println("Latitude: " + location.getLatitude());
                        btnCon.setEnabled(true);
                        ld_current_location.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaType JSON = MediaType.get("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();
                    String json = "{\"location\":{\"lat\":" + latitude + ",\"lng\":" + longitude + "},\"vaccineData\":\"" + vaccineData + "\"}";
                    String url = getString(R.string.backend_url) + "/api/v1.0/search";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                            JSONObject result = new JSONObject(responseBody.string());

                            Intent intentContinue = new Intent(EligibleConfirmActivity.this, AppointmentActivity.class);

                            intentContinue.putExtra("locations", result.getJSONArray("locations").toString());
                            startActivity(intentContinue);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}