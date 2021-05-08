package CMPE133_project.vaxtrack;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Location {
    private String address;
    private String distance;
    private String id;
    private String regionId;
    private String startDate;
    private String endDate;
    private double longitude;
    private double latitude;
    private String name;
    private String url;

    public Location(String address, String distance, String id, String regionId, String startDate, String endDate, double longitude, double latitude, String name, String url) {
        this.address = address;
        this.distance = distance;
        this.id = id;
        this.regionId = regionId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static ArrayList<Location> getLocations(ArrayList<Location> locations, String jsonString) {
        try {
            JSONArray rawLocationArray = new JSONArray(jsonString);
            for (int i = 0; i < rawLocationArray.length(); i++){
                JSONObject rawLocation = rawLocationArray.getJSONObject(i);
                locations.add(new Location(
                        rawLocation.getString("displayAddress"),
                        rawLocation.getString("distanceInMeters"),
                        rawLocation.getString("extId"),
                        rawLocation.getString("regionExternalId"),
                        rawLocation.getString("startDate"),
                        rawLocation.getString("endDate"),
                        rawLocation.getJSONObject("location").getDouble("lng"),
                        rawLocation.getJSONObject("location").getDouble("lat"),
                        rawLocation.getString("name"),
                        rawLocation.getString("externalURL")
                ));
            }
        }
        catch(Exception e){

        }
        return locations;
    }
}

