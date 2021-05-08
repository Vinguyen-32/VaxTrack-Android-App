package CMPE133_project.vaxtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {
    ConstraintLayout confirm;
    ConstraintLayout death;
    ConstraintLayout test;
    ConstraintLayout vaccine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageButton appointmentButton = (ImageButton)findViewById(R.id.imageButton);
        appointmentButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Location.class);
                startActivity(i);
            }
        });

        confirm = findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this, Detailfirst.class);
                startActivity(i);

            }
        });

        death = findViewById(R.id.death);

        death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this, detailsecond.class);
                startActivity(i);

            }
        });

        test = findViewById(R.id.test);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this, detailthird.class);
                startActivity(i);

            }
        });

        vaccine = findViewById(R.id.vaccine);

        vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this, detaillast.class);
                startActivity(i);

            }
        });

    }


}

