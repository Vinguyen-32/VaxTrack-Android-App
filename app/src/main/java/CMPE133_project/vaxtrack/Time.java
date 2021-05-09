package CMPE133_project.vaxtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class Time extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button timeButton;
    private Button selbutton1;
    Spinner sp_time;
    ArrayList<String> arrayList_time;
    ArrayAdapter<String> arrayAdapter_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        sp_time = (Spinner) findViewById(R.id.spinner);
        arrayList_time = new ArrayList<>();
        arrayList_time.add("7:30 AM-8:00 AM");
        arrayList_time.add("8:30 AM-9:00 AM");
        arrayList_time.add("9:30 AM-10:00 AM");
        arrayList_time.add("10:30 AM-11:00AM");
        arrayList_time.add("11:30 AM-12:00 PM");
        arrayList_time.add("12:30 PM-1:00 PM");
        arrayList_time.add("1:30 PM-2:00 PM");
        arrayList_time.add("2:30 PM-3:00 PM");
        arrayList_time.add("3:30 PM-4:00 PM");
        arrayList_time.add("4:30 PM-5:00 PM");
        arrayList_time.add("5:30 PM-6:00 PM");
        selbutton1 = findViewById(R.id.button2);

        selbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Time.this, Home.class));
            }
        });
        arrayAdapter_time = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_time);
        sp_time.setAdapter((SpinnerAdapter) arrayAdapter_time);
        intitDatePicker();
        dateButton = findViewById(R.id.datePickerbutton);
        dateButton.setText(getTodaysDate());
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    private void intitDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);

            }

        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }


}