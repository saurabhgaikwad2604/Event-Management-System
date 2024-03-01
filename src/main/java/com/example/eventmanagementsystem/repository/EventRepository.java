package com.example.eventmanagementsystem.repository;

import com.example.eventmanagementsystem.model.*;
import java.util.*;

public interface EventRepository {
    ArrayList<Event> getEvents();

    Event getEventById(int id);

    Event addEvent(Event event);

    Event updateEvent(Event event, int id);

    void deleteEvent(int id);

    List<Sponsor> getEventSponsors(int id);
}