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
    EditText firstName, lastName, mobileNumber, address;
    Button insert;
    DatabaseHelper DB;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        firstName = findViewById(R.id.pi_txt_firstName);
        lastName = findViewById(R.id.pi_txt_lastName);
        mobileNumber = findViewById(R.id.pi_txt_phone);
        address = findViewById(R.id.pi_txt_phone);
        //dob = findViewById(R.id.pi_txt_dobSel);

        insert = findViewById(R.id.pi_to_covid_button);
        DB = new DatabaseHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameTXT = firstName.getText().toString();
                String lastNameTXT = lastName.getText().toString();
                String mobileNumberTXT = mobileNumber.getText().toString();
                String addressTXT = address.getText().toString();
                //String dobTXT = dob.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(firstNameTXT, lastNameTXT, mobileNumberTXT, addressTXT);
                if (checkinsertdata == true)
                    Toast.makeText(PersonalInfo.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(PersonalInfo.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });


        //Gender spinner
        Spinner dropdown = findViewById(R.id.pi_spinner_gender);
        String[] items = new String[]{
                "Select",
                "Male",
                "Female",
                "Other",
                "Decline to state"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        //Gender spinner
        Spinner dropdown2 = findViewById(R.id.pi_spinner_ethnicity);
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

        //Date of birth selection
        mDisplayDate = findViewById(R.id.pi_txt_dobSel);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PersonalInfo.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy:" + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }
}