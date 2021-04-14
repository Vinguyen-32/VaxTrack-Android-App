package CMPE133_project.vaxtrack;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Locale;

public class LocationService {
    public static double[] geocodeByZipcode(Context context, String input) {
        double[] found = null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> locations = geocoder.getFromLocationName(input, 5);
            if (locations.size() > 0) {
                Address address = locations.get(0);
                found = new double[]{address.getLongitude(), address.getLatitude()};
            }
        } catch (Exception e) {
        }
        return found;
    }
}
