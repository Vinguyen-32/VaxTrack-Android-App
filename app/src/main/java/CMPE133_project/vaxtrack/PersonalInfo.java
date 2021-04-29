package CMPE133_project.vaxtrack;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PersonalInfo extends AppCompatActivity {

    private static final String TAG = "PersonalInfo";
    EditText firstName, lastName, month, day, year, gender, race, email, mobileNumber, streetAddress, city, zipcode;
    Button insert;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
        year = findViewById(R.id.year);
        //gender = findViewById(R.id.gender_spinner);
        //race = findViewById(R.id.race_spinner);
        email = findViewById(R.id.email);
        mobileNumber = findViewById(R.id.phoneNumber);
        streetAddress = findViewById(R.id.streetAddress);
        city = findViewById(R.id.city);
        zipcode = findViewById(R.id.zipCode);

        insert = findViewById(R.id.saveContinue_button);

//        insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String firstNameTXT = firstName.getText().toString();
//                String lastNameTXT = lastName.getText().toString();
//                String mobileNumberTXT = mobileNumber.getText().toString();
//                String addressTXT = address.getText().toString();
//                //String dobTXT = dob.getText().toString();
//
//                Boolean checkinsertdata = DB.insertuserdata(firstNameTXT, lastNameTXT, mobileNumberTXT, addressTXT, dobTXT);
//                if (checkinsertdata == true)
//                    Toast.makeText(PersonalInfo.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(PersonalInfo.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
//            }
//        });


        //Gender spinner start
        Spinner dropdown = findViewById(R.id.gender_spinner);
        String[] items = new String[]{
                "Select",
                "Male",
                "Female",
                "Other",
                "Decline to state"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        //Gender spinner end

        //Ethnicity spinner start
        Spinner dropdown2 = findViewById(R.id.race_spinner);
        String[] items2 = new String[]{
                "Select",
                "American Indian or Alaska Native",
                "Asian",
                "Black or African American",
                "Native Hawaiian or Other Pacific Islander",
                "White",
                "Two or More Races",
                "No Response."
        };

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        //Ethnicity spinner end

    }
}