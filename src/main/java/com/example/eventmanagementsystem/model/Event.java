package com.example.eventmanagementsystem.model;

import com.example.eventmanagementsystem.model.Sponsor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private String date;

    @ManyToMany(mappedBy = "events")
    @JsonIgnoreProperties("events")
    private List<Sponsor> sponsors = new ArrayList<>();

    public Event() {
    }

    public int getEventId() {
        return id;
    }

    public void setEventId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return name;
    }

    public void setEventName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }
}