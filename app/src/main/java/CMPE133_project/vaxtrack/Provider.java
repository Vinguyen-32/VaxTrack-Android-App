package CMPE133_project.vaxtrack;

import android.location.Location;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class Provider {
    private String name, address, brand;
    private Location location;

    // Getters and setters.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
