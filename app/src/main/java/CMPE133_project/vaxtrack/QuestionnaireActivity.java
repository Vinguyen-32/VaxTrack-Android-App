package CMPE133_project.vaxtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class QuestionnaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        Button selectBtn = findViewById(R.id.image_selector);
        Button cancelBtn = findViewById(R.id.btnCancel);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                openImageSelector();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(QuestionnaireActivity.this, ConfirmInfoActivity.class);
                startActivity(intent);
            }
        });

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
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
                "Food and agriculture worker",
                "None of the above"
        };
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                View uploadLayout = findViewById(R.id.uploadLayout);
                View employerLayout = findViewById(R.id.employerLayout);
                Button btn = findViewById(R.id.btnConfirmEligibility);
                uploadLayout.setVisibility(position == 0 || position == items.length - 1 ? View.GONE : View.VISIBLE);
                employerLayout.setVisibility(position == 0 || position == items.length - 1 ? View.GONE : View.VISIBLE);
                btn.setEnabled(position != 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openImageSelector(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Add Photo");
        alertDialog.setCancelable(true);

        alertDialog.setNeutralButton(
                "\uD83D\uDCF7 Use Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            startActivityForResult(takePictureIntent, 54);
                        } catch (ActivityNotFoundException e) {
                            // display error state to the user
                        }
                    }
                });

        alertDialog.setNegativeButton(
                "\uD83D\uDCE4 Choose Image",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 45);
                    }
                });

        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }
}