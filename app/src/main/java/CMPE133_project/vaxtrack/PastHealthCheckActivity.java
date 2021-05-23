package CMPE133_project.vaxtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

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

public class PastHealthCheckActivity extends AppCompatActivity {
    private boolean allNo = false;

    private Button continueBtn;
    private Button cancelBtn;
    private String dob;
    private String firstName;
    private String lastName;
    private String address;
    private RadioGroup radio_group_1;
    private RadioGroup radio_group_2;
    private RadioGroup radio_group_3;

    private OkHttpClient client;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_health_check);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        continueBtn = findViewById(R.id.btnCon);
        cancelBtn = findViewById(R.id.btnCan);

        radio_group_1 = findViewById(R.id.radio_group_1);
        radio_group_2 = findViewById(R.id.radio_group_2);
        radio_group_3 = findViewById(R.id.radio_group_3);

        client = new OkHttpClient();

        Intent parentIntent = getIntent();
        firstName = parentIntent.getStringExtra("firstName");
        lastName = parentIntent.getStringExtra("lastName");
        dob = parentIntent.getStringExtra("dob");
        address = parentIntent.getStringExtra("address");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PastHealthCheckActivity.this, ServicesActivity.class);
                startActivity(intent);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateString = dob;
                int year = Integer.parseInt(dateString.substring(dateString.length() - 4));

                String json = "{\"firstName\":\"" + firstName + "\", \"lastName\":\"" + lastName + "\", \"address\": \"" + address + "\", \"dob\": \"" + dob + "\"}";
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

                            Intent intentContinue;

                            if (eligible && allNo) {
                                if ((new Date()).getYear() + 1900 - year >= 65) {
                                    intentContinue = new Intent(PastHealthCheckActivity.this, EligibleConfirmActivity.class);
                                } else {
                                    intentContinue = new Intent(PastHealthCheckActivity.this, QuestionnaireActivity.class);
                                }

//                                intentContinue.putExtra("id", id);
                                intentContinue.putExtra("name", firstName + " " + lastName);
                                intentContinue.putExtra("dob", dob);
                                intentContinue.putExtra("address", address);
                                intentContinue.putExtra("vaccineData", vaccineData);
                            }
                            else {

                                intentContinue = new Intent(PastHealthCheckActivity.this, NotEligibleConfirmActivity.class);
                            }
                            startActivity(intentContinue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        radio_group_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeState();
            }
        });

        radio_group_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeState();
            }
        });

        radio_group_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeState();
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

    private void changeState(){
        int group1CheckedId = radio_group_1.getCheckedRadioButtonId();
        int group2CheckedId = radio_group_2.getCheckedRadioButtonId();
        int group3CheckedId = radio_group_3.getCheckedRadioButtonId();

        allNo =
            group1CheckedId == R.id.radioGroup1_no &&
            group2CheckedId == R.id.radioGroup2_no &&
            group3CheckedId == R.id.radioGroup3_no;

        continueBtn.setEnabled(group1CheckedId != -1 && group2CheckedId != -1 && group3CheckedId != -1);
    }
}