package CMPE133_project.vaxtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button selectBtn = findViewById(R.id.image_selector);
        Button submitBtn = findViewById(R.id.imgSubmitBtn);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                openImageSelector();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfirmInfoActivity.class);
                startActivity(intent);
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