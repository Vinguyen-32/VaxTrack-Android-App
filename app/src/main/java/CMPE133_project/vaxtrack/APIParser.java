package CMPE133_project.vaxtrack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIParser extends AsyncTask<String, String, Void> {

    private String apiUrl = "https://www.vaccinespotter.org/api/v0/states/CT.json";
   // private ListView listView;
   // private double[] locationService;
    // For parsing data
    public List<Provider> providers = new ArrayList<>();
    private BufferedInputStream inputStream;
    String result = "";

    // XML stuff
    public ProgressDialog progressDialog;
    private ListView listView;
    private Activity activity;
    private Context context;

    public APIParser(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
        this.progressDialog = new ProgressDialog(this.context);
        //locationService = LocationService.geocodeByZipcode(context, )
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.dismiss();
        progressDialog.setMessage("Fetching available providers...");
        progressDialog.show();
        progressDialog.setOnCancelListener((dialogInterface) -> {APIParser.this.cancel(true);});
    }

    @Override
    protected Void doInBackground(String... strings) {
       HttpURLConnection httpURLConnection = null;
       try{
           URL url = new URL(apiUrl);
           httpURLConnection = (HttpURLConnection) url.openConnection();
           inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
           result = readStream();
          // result = result.substring(result.indexOf("(") + 1, result.lastIndexOf(")"));
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
        return null;
    }

    private String readStream() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            JSONObject featureCollection = new JSONObject(result);
            if (featureCollection != null){
                JSONArray features = featureCollection.getJSONArray("features");
                for (int i = 0; i < features.length(); i++){
                    JSONObject feature = features.getJSONObject(i);
                    // Parse Location.
                    JSONObject point = feature.getJSONObject("geometry");
                    JSONArray coordinates = point.getJSONArray("coordinates");
                    Location providerLocation = new Location("");
                    providerLocation.setLatitude(coordinates.getDouble(0));
                    providerLocation.setLongitude(coordinates.getDouble(1));
                    // Parse properties
                    JSONObject properties = feature.getJSONObject("properties");
                    // Check if provider has appointments.
                    if (properties.getBoolean("appointments_available")){
                        String brands = "";
                        if(properties.getJSONObject("appointment_vaccine_types").has("moderna")) {
                            brands += "Moderna ";
                        }
                        if(properties.getJSONObject("appointment_vaccine_types").has("jj")){
                            brands += "Johnson & Johnson ";
                        }
                        if(properties.getJSONObject("appointment_vaccine_types").has("pfizer")){
                            brands += "Pfizer ";
                        }
                        // Create the Provider class to represent the location.
                        Provider provider = new Provider();
                        provider.setName(properties.getString("provider_brand_name"));
                        provider.setAddress(properties.getString("address"));
                        provider.setLocation(providerLocation);
                        provider.setBrand(brands);
                        providers.add(provider);
                    }
                }
            }
            listView = (ListView) this.activity.findViewById(R.id.providerList);
            CustomAdapter adapter = new CustomAdapter(this.context, providers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
