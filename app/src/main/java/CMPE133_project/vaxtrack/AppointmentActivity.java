package CMPE133_project.vaxtrack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity implements OnMapReadyCallback {
    private LocationAdapter locationAdapter;
    private ArrayList<Location> locations;
    private RecyclerView rvAppointments;
    private GoogleMap mMap;

    TextView tvChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvChange = findViewById(R.id.tvChange);

        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentActivity.this, EligibleConfirmActivity.class);
                startActivity(intent);
            }
        });

        // initiating the tabhost
        TabHost tabhost = (TabHost) findViewById(R.id.tabhost);

        // setting up the tab host
        tabhost.setup();

        // Code for adding Tab 1 to the tabhost
        TabHost.TabSpec spec = tabhost.newTabSpec("List");
        spec.setContent(R.id.tab1);
        spec.setIndicator("List");
        tabhost.addTab(spec);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Code for adding Tab 2 to the tabhost
        spec = tabhost.newTabSpec("Map");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Map");
        tabhost.addTab(spec);


        Intent intent = getIntent();

        String rawLocationString = intent.getStringExtra("locations");

//        String rawLocationString = "[{\"displayAddress\":\"570 N Shoreline Blvd, Mountain View, CA 94043\",\"distanceInMeters\":2148.77792931,\"extId\":\"a2ut0000006a9S8AAI\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.079,\"lat\":37.403},\"name\":\"SAFEWAY PHARMACY #0705\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"645 San Antonio Rd, Mountain View, CA 94040\",\"distanceInMeters\":3334.60032335,\"extId\":\"a2ut0000006a9WtAAI\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.112,\"lat\":37.4019},\"name\":\"SAFEWAY PHARMACY #2948\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"1030 W. Maude Ave, Sunnyvale, CA, 94085\",\"distanceInMeters\":4743.3622084,\"extId\":\"a2ut0000006eUjZAAU\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-08\",\"endDate\":\"2021-06-30\",\"location\":{\"lng\":-122.044,\"lat\":37.3936},\"name\":\"El Camino Health - Maude Ave\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.elcaminohealth.org\\/covid-19-resource-center\\/schedule\\/myturn?vt=2011015&dept=201001109&linksource=CVMT\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"160 1ST ST., Los Altos, CA 94022\",\"distanceInMeters\":5687.92320827,\"extId\":\"a2ut0000006a9WaAAI\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.119,\"lat\":37.3791},\"name\":\"SAFEWAY PHARMACY #2814\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"639 So Bernardo Ave, Sunnyvale, CA 94087\",\"distanceInMeters\":5905.61720691,\"extId\":\"a2ut0000006a9T8AAI\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.058,\"lat\":37.373},\"name\":\"SAFEWAY PHARMACY #1196\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"484 N MATHILDA AVE, SUNNYVALE, CA 94085\",\"distanceInMeters\":6089.87725281,\"extId\":\"a2ut0000006aA9cAAE\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.03,\"lat\":37.3885},\"name\":\"Lucky Pharmacy 780 (No Pfizer Vaccine)\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/savemartluckysched.rxtouch.com\\/smsched\\/program\\/covid19\\/patient\\/advisory\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"341 Galvez St., Palo Alto, CA 94605-6106\",\"distanceInMeters\":7138.36302041,\"extId\":\"a2ut0000006a9oxAAA\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.164,\"lat\":37.43},\"name\":\"Stanford COVID Vaccination, Palo Alto\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/stanfordhealthcare.org\\/discover\\/covid-19-resource-center\\/patient-care\\/safety-health-vaccine-planning.html\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"150 E. El Camino Real, Sunnyvale, CA 94087\",\"distanceInMeters\":7910.46918832,\"extId\":\"a2ut0000006a9WnAAI\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.031,\"lat\":37.3649},\"name\":\"SAFEWAY PHARMACY #2887\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"2175 GRANT RD, LOS ALTOS, CA 94024\",\"distanceInMeters\":8911.30123388,\"extId\":\"a2ut0000006aABrAAM\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.072,\"lat\":37.3423},\"name\":\"Lucky Pharmacy 723 (No Pfizer Vaccine)\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/savemartluckysched.rxtouch.com\\/smsched\\/program\\/covid19\\/patient\\/advisory\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"525 EL CAMINO REAL, Menlo Park, CA 94025\",\"distanceInMeters\":8986.79006841,\"extId\":\"a2ut0000006a9WQAAY\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.179,\"lat\":37.4505},\"name\":\"SAFEWAY PHARMACY #2719\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"1299-A Oakmead Parkway\\r\\nSunnyvale, CA 94085\",\"distanceInMeters\":9277.9600199,\"extId\":\"a2ut0000004rgS6AAI\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-05-07\",\"endDate\":\"2021-05-14\",\"location\":{\"lng\":-121.989,\"lat\":37.3868},\"name\":\"One Medical - Sunnyvale - Johnson & Johnson\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[{\"days\":[\"tue\"],\"localStart\":\"09:00:00\",\"localEnd\":\"12:15:00\"},{\"days\":[\"fri\"],\"localStart\":\"09:00:00\",\"localEnd\":\"12:15:00\"},{\"days\":[\"thu\"],\"localStart\":\"09:00:00\",\"localEnd\":\"12:15:00\"},{\"days\":[\"wed\"],\"localStart\":\"09:00:00\",\"localEnd\":\"12:15:00\"},{\"days\":[\"mon\"],\"localStart\":\"09:00:00\",\"localEnd\":\"12:15:00\"}],\"type\":\"OnlineBooking\",\"vaccineData\":\"WyJhM3F0MDAwMDAwMDFBc2FBQUUiXQ==\"},{\"displayAddress\":\"785 E. El Camino Real, Sunnyvale, CA 94087\",\"distanceInMeters\":9278.98316263,\"extId\":\"a2ut0000006a9TSAAY\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.018,\"lat\":37.3572},\"name\":\"SAFEWAY PHARMACY #1439\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"20620 Homestead Rd, Cupertino, CA 95014\",\"distanceInMeters\":10477.89124046,\"extId\":\"a2ut0000006a9XHAAY\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.035,\"lat\":37.3362},\"name\":\"SAFEWAY PHARMACY #3251\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"3705 EL CAMINO REAL, SANTA CLARA, CA 95051\",\"distanceInMeters\":10719.11688228,\"extId\":\"a2ut0000006aACzAAM\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-121.998,\"lat\":37.354},\"name\":\"Lucky Pharmacy 773 (No Pfizer Vaccine)\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/savemartluckysched.rxtouch.com\\/smsched\\/program\\/covid19\\/patient\\/advisory\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"4150 N. First Street, San Jose, CA 95134\",\"distanceInMeters\":11519.19381531,\"extId\":\"a2ut0000006eUjUAAU\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-08\",\"endDate\":\"2021-06-30\",\"location\":{\"lng\":-121.954,\"lat\":37.417},\"name\":\"El Camino Health - First Street\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.elcaminohealth.org\\/covid-19-resource-center\\/schedule\\/myturn?vt=2011015&dept=201001107&linksource=CVMT\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"700 Lawrence Expy, Santa Clara, CA, 95051\",\"distanceInMeters\":12160.94144147,\"extId\":\"a2ut0000006eUdgAAE\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-31\",\"endDate\":\"2021-06-30\",\"location\":{\"lng\":-121.999,\"lat\":37.3362},\"name\":\"KAISER PERMANENTE Santa Clara Medical Center\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/healthy.kaiserpermanente.org\\/health-wellness\\/california-covid-19-vaccine-page#source=myturn&zipcode=95051&facilityname=Santa%20Clara%20Medical%20Center%20&region=MRN\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"505 Broadway St., Redwood City, CA 94063-3122\",\"distanceInMeters\":12770.20439629,\"extId\":\"a2ut0000006a9p2AAA\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.205,\"lat\":37.4853},\"name\":\"Stanford COVID Vaccination, Redwood City\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/stanfordhealthcare.org\\/discover\\/covid-19-resource-center\\/patient-care\\/safety-health-vaccine-planning.html\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"2710 Middlefield Rd, Redwood City, CA 94063 , Redwood City, CA 94063\",\"distanceInMeters\":12779.40175245,\"extId\":\"a2ut0000004rpFVAAY\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-05-16\",\"endDate\":\"2021-06-13\",\"location\":{\"lng\":-122.212,\"lat\":37.4757},\"name\":\"Fair Oaks Health Center- Dose 1: 5-16, Dose 2: 6-13 (Moderna 18 years and older)\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[{\"days\":[\"sun\"],\"localStart\":\"11:00:00\",\"localEnd\":\"16:00:00\"}],\"description\":\"Moderna, 18 years and older\",\"type\":\"OnlineBooking\",\"vaccineData\":\"WyJhM3F0MDAwMDAwMDFBZE1BQVUiXQ==\"},{\"displayAddress\":\"850 WOODSIDE ROAD, Redwood City, CA 94061\",\"distanceInMeters\":13421.29992505,\"extId\":\"a2ut0000006a9SLAAY\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-122.225,\"lat\":37.4657},\"name\":\"SAFEWAY PHARMACY #0747\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/www.mhealthappointments.com\\/covidappt\",\"vaccineData\":\"W10=\"},{\"displayAddress\":\"350 E Tasman Dr. San Jose, CA 95134-1406\",\"distanceInMeters\":13629.63575932,\"extId\":\"a2ut0000006a9p7AAA\",\"regionExternalId\":\"a30t0000000O1YXAA0\",\"startDate\":\"2021-03-01\",\"endDate\":\"2022-12-01\",\"location\":{\"lng\":-121.931,\"lat\":37.4123},\"name\":\"Stanford COVID Vaccination, Cisco Garage San Jose\",\"timezone\":\"America\\/Los_Angeles\",\"openHours\":[],\"type\":\"ThirdPartyBooking\",\"externalURL\":\"https:\\/\\/stanfordhealthcare.org\\/discover\\/covid-19-resource-center\\/patient-care\\/safety-health-vaccine-planning.html\",\"vaccineData\":\"W10=\"}]";
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }

            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.marker, null);

                TextView title = (TextView) v.findViewById(R.id.tvMarkerTitle);
                TextView description = (TextView) v.findViewById(R.id.tvMarkerDescription);
                TextView url = (TextView) v.findViewById(R.id.tvMarkerUrl);

                title.setText(marker.getTitle());
                description.setText(marker.getSnippet().split("\n")[0]);
                url.setText(marker.getSnippet().split("\n")[1]);

                return v;
            }
        });

        LatLng coordination = null;
        for (int i = 0; i < locations.size(); i++){
            Location location = locations.get(i);
            coordination = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(coordination)
                    .title(location.getName())
                    .snippet(location.getAddress() + "\n" + location.getUrl()));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordination));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }
}