package com.example.eventmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.service.SponsorJpaService;

import java.util.*;

@RestController
public class SponsorController {
    @Autowired
    public SponsorJpaService sponsorService;

    @GetMapping("/events/sponsors")
    public ArrayList<Sponsor> getSponsors() {
        return sponsorService.getSponsors();
    }

    @GetMapping("/events/sponsors/{sponsorId}")
    public Sponsor getSponsorById(@PathVariable("sponsorId") int id) {
        return sponsorService.getSponsorById(id);
    }

    @GetMapping("/sponsors/{sponsorId}/events")
    public List<Event> getSponsorevents(@PathVariable("sponsorId") int id) {
        return sponsorService.getSponsorevents(id);
    }

    @PostMapping("/events/sponsors")
    public Sponsor addSponsor(@RequestBody Sponsor sponsor) {
        return sponsorService.addSponsor(sponsor);
    }

    @PutMapping("/events/sponsors/{sponsorId}")
    public Sponsor updateSponsor(@PathVariable("sponsorId") int id, @RequestBody Sponsor sponsor) {
        return sponsorService.updateSponsor(sponsor, id);
    }

    @DeleteMapping("/events/sponsors/{sponsorId}")
    public void deleteSponsor(@PathVariable("sponsorId") int id) {
        sponsorService.deleteSponsor(id);
    }
}