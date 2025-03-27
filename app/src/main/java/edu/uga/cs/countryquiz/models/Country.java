package edu.uga.cs.countryquiz.models;

import java.io.Serializable;

public class Country implements Serializable {
    private int id;
    private String name;
    private String continent;

    public Country(int id, String name, String continent) {
        this.id = id;
        this.name = name;
        this.continent = continent;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }
}
