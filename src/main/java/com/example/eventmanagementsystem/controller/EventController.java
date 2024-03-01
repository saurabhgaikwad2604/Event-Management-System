package com.example.eventmanagementsystem.controller;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.service.EventJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class EventController {
    @Autowired
    public EventJpaService eventService;

    @GetMapping("/events")
    public ArrayList<Event> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable("eventId") int id) {
        return eventService.getEventById(id);
    }

    @GetMapping("/events/{eventId}/sponsors")
    public List<Sponsor> getEventSponsors(@PathVariable("eventId") int id){
        return eventService.getEventSponsors(id);
    }

    @PostMapping("/events")
    public Event addEvent(@RequestBody Event event){
        return eventService.addEvent(event);
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable("eventId") int id, @RequestBody Event event){
        return eventService.updateEvent(event, id);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable("eventId") int id){
        eventService.deleteEvent(id);
    }
}