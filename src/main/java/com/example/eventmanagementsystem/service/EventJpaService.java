package com.example.eventmanagementsystem.service;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class EventJpaService implements EventRepository {
    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public ArrayList<Event> getEvents() {
        List<Event> eventList = eventJpaRepository.findAll();
        ArrayList<Event> events = new ArrayList<>(eventList);
        return events;
    }

    @Override
    public Event getEventById(int id) {
        try {
            Event event = eventJpaRepository.findById(id).get();
            return event;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event addEvent(Event event) {
        try {
            List<Integer> sponsorIds = new ArrayList<>();
            for (Sponsor sponsor : event.getSponsors()) {
                sponsorIds.add(sponsor.getSponsorId());
            }
            List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);
            if (sponsorIds.size() != sponsors.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            event.setSponsors(sponsors);

            for (Sponsor sponsor : sponsors) {
                sponsor.getEvents().add(event);
            }
            Event savedEvent = eventJpaRepository.save(event);
            sponsorJpaRepository.saveAll(sponsors);
            return savedEvent;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event updateEvent(Event event, int id) {
        try {
            Event newEvent = eventJpaRepository.findById(id).get();
            if (event.getEventName() != null) {
                newEvent.setEventName(event.getEventName());
            }
            if (event.getDate() != null) {
                newEvent.setDate(event.getDate());
            }
            if (event.getSponsors() != null) {
                List<Sponsor> sponsors = newEvent.getSponsors();
                for (Sponsor sponsor : sponsors) {
                    sponsor.getEvents().remove(newEvent);
                }
                sponsorJpaRepository.saveAll(sponsors);
                List<Integer> newSponsorIds = new ArrayList<>();
                for (Sponsor sponsor : event.getSponsors()) {
                    newSponsorIds.add(sponsor.getSponsorId());
                }

                List<Sponsor> newSponsors = sponsorJpaRepository.findAllById(newSponsorIds);
                if (newSponsorIds.size() != newSponsors.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                for (Sponsor sponsor : newSponsors) {
                    sponsor.getEvents().add(newEvent);
                }
                sponsorJpaRepository.saveAll(newSponsors);

                newEvent.setSponsors(newSponsors);
            }
            return eventJpaRepository.save(newEvent);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteEvent(int id) {
        try {
            Event event = eventJpaRepository.findById(id).get();

            List<Sponsor> sponsors = event.getSponsors();
            for (Sponsor sponsor : sponsors) {
                sponsor.getEvents().remove(event);
            }
            sponsorJpaRepository.saveAll(sponsors);
            eventJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Sponsor> getEventSponsors(int id) {
        Event event = eventJpaRepository.findById(id).get();
        List<Sponsor> sponsors = event.getSponsors();
        return sponsors;
    }

}