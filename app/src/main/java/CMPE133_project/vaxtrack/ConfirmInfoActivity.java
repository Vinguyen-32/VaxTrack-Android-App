package CMPE133_project.vaxtrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ConfirmInfoActivity extends AppCompatActivity {

    private EditText etDob;
    private EditText etLastName;
    private EditText etFirstName;
    private EditText etAddress;
    private Button buttonBack;
    private Button btnContinue;

    private String id;
    private String name;
    private String dob;
    private String address;

    private OkHttpClient client;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirminfo);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        client = new OkHttpClient();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        dob = intent.getStringExtra("dob");
        address = intent.getStringExtra("address");

        buttonBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        etDob = findViewById(R.id.etDob);
        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.svSearchLocation);
        etAddress = findViewById(R.id.etAddress);

        String[] nameParts = name.split(" ");
        etDob.setText(dob);
        etLastName.setText(nameParts.length > 1 ? nameParts[1] : "");
        etFirstName.setText(nameParts[0]);
        etAddress.setText(address);

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
                Intent intent = new Intent(ConfirmInfoActivity.this, PastHealthCheckActivity.class);
                intent.putExtra("firstName", etFirstName.getText().toString());
                intent.putExtra("lastName", etLastName.getText().toString());
                intent.putExtra("dob", etDob.getText().toString());
                intent.putExtra("address", etAddress.getText().toString());
                startActivity(intent);
            }
        });
    }
}