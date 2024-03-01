package com.example.eventmanagementsystem.model;

import com.example.eventmanagementsystem.model.Event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "sponsor")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "industry")
    private String industry;
    @ManyToMany
    @JoinTable(name = "event_sponsor", joinColumns = @JoinColumn(name = "sponsorid"), inverseJoinColumns = @JoinColumn(name = "eventid"))
    @JsonIgnoreProperties("sponsors")
    private List<Event> events = new ArrayList<>();

    public Sponsor() {
    }

    public int getSponsorId() {
        return id;
    }

    public void setSponsorId(int id) {
        this.id = id;
    }

    public String getSponsorName() {
        return name;
    }

    public void setSponsorName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}