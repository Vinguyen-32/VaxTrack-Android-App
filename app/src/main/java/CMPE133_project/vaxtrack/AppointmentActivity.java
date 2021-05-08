package CMPE133_project.vaxtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {
    private LocationAdapter locationAdapter;
    private ArrayList<Location> locations;
    private RecyclerView rvAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();

        String rawLocationString = intent.getStringExtra("locations");

        // FInd the recycler view
        rvAppointments = findViewById(R.id.rvAppointments);

        //Init the list of tweets and adapter
        locations = new ArrayList<Location>();
        locations = Location.getLocations(locations, rawLocationString);

        locationAdapter = new LocationAdapter(this, locations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // Recycler View setup: layout manager and the adapter
        rvAppointments.setLayoutManager(layoutManager);
        rvAppointments.setAdapter(locationAdapter);
    }
}