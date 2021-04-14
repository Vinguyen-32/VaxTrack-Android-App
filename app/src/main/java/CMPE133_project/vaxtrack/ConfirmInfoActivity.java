package CMPE133_project.vaxtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class ConfirmInfoActivity extends AppCompatActivity {

    private EditText etDob;
    private Button buttonBack;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirminfo);

        buttonBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        etDob = findViewById(R.id.etDob);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(ConfirmInfoActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateString = etDob.getText().toString();
                int year = Integer.parseInt(dateString.substring(dateString.length() - 4));

                Intent intentContinue;

                if((new Date()).getYear() + 1900 - year >= 65) {
                    intentContinue = new Intent(ConfirmInfoActivity.this, EligibleConfirmActivity.class);
                }
                else{
                    intentContinue = new Intent(ConfirmInfoActivity.this, QuestionnaireActivity.class);
                }
                startActivity(intentContinue);
            }

        });
    }
}