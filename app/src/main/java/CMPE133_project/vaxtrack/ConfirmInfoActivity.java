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
        etFirstName = findViewById(R.id.etFirstName);
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
                String dateString = etDob.getText().toString();
                int year = Integer.parseInt(dateString.substring(dateString.length() - 4));

                String json = "{\"firstName\":\"" + etFirstName.getText() + "\", \"lastName\":\"" + etLastName.getText() + "\", \"address\": \"" + etAddress.getText() + "\", \"dob\": \"" + etDob.getText() + "\"}";
                String url = getString(R.string.backend_url) + "/api/v1.0/eligibility";
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Handler mHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message message) {
                        // This is where you do your work in the UI thread.
                        // Your worker tells you in the message what to do.
                        Toast.makeText(getApplicationContext(), "Not Eligible", Toast.LENGTH_SHORT).show();
                    }
                };

                client.newCall(request).enqueue(new Callback() {
                    @Override public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                            JSONObject result = new JSONObject(responseBody.string());
                            boolean eligible = result.getBoolean("eligible");
                            String vaccineData = result.getString("vaccineData");

                            if (eligible) {
                                Intent intentContinue;
                                if ((new Date()).getYear() + 1900 - year >= 65) {
                                    intentContinue = new Intent(ConfirmInfoActivity.this, EligibleConfirmActivity.class);
                                } else {
                                    intentContinue = new Intent(ConfirmInfoActivity.this, QuestionnaireActivity.class);
                                }

                                intentContinue.putExtra("id", id);
                                intentContinue.putExtra("name", name);
                                intentContinue.putExtra("dob", dob);
                                intentContinue.putExtra("address", address);
                                intentContinue.putExtra("vaccineData", vaccineData);
                                startActivity(intentContinue);
                            }
                            else {
                                // Communicate with UI thread
                                Message message = mHandler.obtainMessage(1, null);
                                message.sendToTarget();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}