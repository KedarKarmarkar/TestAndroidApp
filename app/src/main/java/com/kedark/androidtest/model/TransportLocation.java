package com.kedark.androidtest.model;



import java.util.List;

/**
 * Created by kedarkarmarkar on 3/8/16.
 */
public class TransportLocation {
    int id;
    String name;
    List<FromCentral>fromCentral;
    Location location;

    public TransportLocation() {
    }

    public TransportLocation(int id, List<FromCentral> fromCentral, Location location,String name) {
        this.id = id;
        this.fromCentral = fromCentral;
        this.location = location;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FromCentral> getFromCentral() {
        return fromCentral;
    }

    public void setFromCentral(List<FromCentral> fromCentral) {
        this.fromCentral = fromCentral;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
