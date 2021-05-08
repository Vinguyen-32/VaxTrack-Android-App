package CMPE133_project.vaxtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class Location extends AppCompatActivity {

    Spinner sp_city,sp_location;
    ArrayList<String> arrayList_city;
    ArrayAdapter<String> arrayAdapter_city;
    ArrayList<String> arrayList_sj;
    ArrayList<String> arrayList_nw;
    ArrayList<String> arrayList_hw;
    ArrayList<String> arrayList_sn;
    ArrayList<String> arrayList_cl;
    ArrayAdapter<String> arrayAdapter_location;
    private Button selectbutton;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        sp_city = (Spinner) findViewById(R.id.citysp);
        sp_location = (Spinner) findViewById(R.id.locationsp);
        arrayList_city = new ArrayList<>();
        arrayList_city.add("San Jose");
        arrayList_city.add("Sunnyvale");
        arrayList_city.add("Santa Clara");
        arrayList_city.add("Newark");
        arrayList_city.add("Hayward");
        arrayAdapter_city = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_city);
        sp_city.setAdapter((SpinnerAdapter) arrayAdapter_city);

        arrayList_sj = new ArrayList<>();
        arrayList_sj.add("CVS Pharmacy, 1750 Story Rd");
        arrayList_sj.add("Walgreens Pharmacy, 1756 Story Rd");
        arrayList_sj.add("Walmarts Pharmacy, 777 Story Rd");

        arrayList_cl = new ArrayList<>();
        arrayList_cl.add("Costco Pharmacy,  1601 Coleman Ave ");
        arrayList_cl.add("CWalgreens Pharmacy,  200 N Winchester Blvd  ");
        arrayList_cl.add("Rite Aid Pharmacy,  2620 El Camino Real  ");

        arrayList_nw = new ArrayList<>();
        arrayList_nw.add("Newark Wellness,  6066 Civic Terrace Ave ");
        arrayList_nw.add("  Liberty Clinic,  39500 Liberty St");
        arrayList_nw.add("Bay Area Community Health,  40910 Fremont Blvd ");

        arrayList_hw = new ArrayList<>();
        arrayList_hw.add("Lucky Pharmacy,   40055 Mission Blvd");
        arrayList_hw.add("Walgreens Pharmacy,   2600 Mowry Ave");


        arrayList_sn = new ArrayList<>();
        arrayList_sn.add("CVS Pharmacy,  1130 Bird Ave");
        arrayList_sn.add("Safeway Pharmacy,  1300 W San Carlos St e");
        arrayList_sn.add("Berger Auditorium,  1555 Berger Dr ");
        selectbutton = findViewById(R.id.button);

        selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Location.this, Time.class));
            }
        });
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    arrayAdapter_location = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_sj);
                }
                if (position == 1) {
                    arrayAdapter_location = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_sn);
                }
                if (position == 2) {
                    arrayAdapter_location = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_cl);
                }
                if (position == 3) {
                    arrayAdapter_location = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_nw);
                }
                if (position == 4) {
                    arrayAdapter_location = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_hw);
                }
                sp_location.setAdapter((SpinnerAdapter) arrayAdapter_location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}