package CMPE133_project.vaxtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class PersonalInfo extends AppCompatActivity {

    private static final String TAG = "PersonalInfo";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

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

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown2.setAdapter(adapter);

        //Date of birth selection
        mDisplayDate = (TextView) findViewById(R.id.pi_tv_dobSel);
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
                String date =   month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }
}