package CMPE133_project.vaxtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class QuestionnaireActivity extends AppCompatActivity {
    private int selectPosition;
    private CheckBox checkBox;
    private RadioGroup radioGroup;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Button selectBtn = findViewById(R.id.image_selector);
        Button cancelBtn = findViewById(R.id.btnCancel);

        radioGroup = findViewById(R.id.radioButtonGroup);
        confirmBtn = findViewById(R.id.btnConfirmEligibility);

        Spinner dropdown = findViewById(R.id.spinner1);

        dropdown.setVisibility(View.GONE);

        confirmBtn.setEnabled(false);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int state = checkedId == R.id.radioButton2 ? View.VISIBLE : View.GONE;
                dropdown.setVisibility(state);
                processSelection();
            }
        });

        checkBox = findViewById(R.id.checkBox);
        selectPosition = 0;

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(QuestionnaireActivity.this, ServicesActivity.class);
                startActivity(intent);
            }
        });

        //get the spinner from the xml.
        //create a list of items for the spinner.
        String[] items = new String[]{
                "Select",
                "Teachers and staff in PreK-12 Schools",
                "Childcare centers and staff",
                "Head Start, Part C Intervention & licensed home",
                "Healthcare Worker",
                "EMS",
                "Emergency Services",
                "Public transport, airport or commercial airline",
                "Long-term care or congregate care staff",
                "Long-term care or congregate care resident",
                "Food and agriculture worker"
        };
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Button btn = findViewById(R.id.btnConfirmEligibility);
                selectPosition = position;

                processSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSelection();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Intent intentContinue;
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton3) {
                    intentContinue = new Intent(QuestionnaireActivity.this, NotEligibleConfirmActivity.class);
                }
                else {
                    intentContinue = new Intent(QuestionnaireActivity.this, EligibleConfirmActivity.class);

                    intentContinue.putExtra("id", intent.getStringExtra("id"));
                    intentContinue.putExtra("name", intent.getStringExtra("name"));
                    intentContinue.putExtra("dob", intent.getStringExtra("dob"));
                    intentContinue.putExtra("address", intent.getStringExtra("address"));
                    intentContinue.putExtra("vaccineData", intent.getStringExtra("vaccineData"));
                }
                startActivity(intentContinue);
            }
        });
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


    private void processSelection(){
        boolean enabled =
            checkBox.isChecked() &&
            (
                (radioGroup.getCheckedRadioButtonId() == R.id.radioButton2 && selectPosition != 0) ||
                radioGroup.getCheckedRadioButtonId() != R.id.radioButton2
            );

        Button btn = findViewById(R.id.btnConfirmEligibility);
        btn.setEnabled(enabled);
    }
}