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
import android.widget.SearchView;
import android.widget.TextView;

public class EligibleConfirmActivity extends AppCompatActivity {
    private LocationManager mLocationManager;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligible_confirm);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        SearchView svSearchLocation = findViewById(R.id.svSearchLocation);
        TextView tvCurrentLocation = findViewById(R.id.tvCurrentLocation);

        Button btnCon = findViewById(R.id.btnCon);
        Button btnCan = findViewById(R.id.btnCan);

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
                mLocationManager.requestLocationUpdates(provider, 400, 1, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        System.out.println("Longtitude: " + location.getLongitude());
                        System.out.println("Latitude: " + location.getLatitude());
                        btnCon.setEnabled(true);
                    }
                });
            }
        });
    }
}