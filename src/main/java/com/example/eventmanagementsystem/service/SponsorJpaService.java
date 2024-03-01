package com.example.eventmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.eventmanagementsystem.repository.*;
import com.example.eventmanagementsystem.model.*;

import java.util.*;

@Service
public class SponsorJpaService implements SponsorRepository {
	@Autowired
	private SponsorJpaRepository sponsorJpaRepository;

	@Autowired
	private EventJpaRepository eventJpaRepository;

	@Override
	public ArrayList<Sponsor> getSponsors() {
		List<Sponsor> sponsorsList = sponsorJpaRepository.findAll();
		ArrayList<Sponsor> sponsors = new ArrayList<>(sponsorsList);
		return sponsors;
	}

	@Override
	public Sponsor getSponsorById(int id) {
		try {
			Sponsor sponsor = sponsorJpaRepository.findById(id).get();
			return sponsor;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Sponsor addSponsor(Sponsor sponsor) {
		try {
			List<Integer> eventIds = new ArrayList<>();
			for (Event event : sponsor.getEvents()) {
				eventIds.add(event.getEventId());
			}
			List<Event> events = eventJpaRepository.findAllById(eventIds);
			if (events.size() != eventIds.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			sponsor.setEvents(events);

			for (Event event : events) {
				event.getSponsors().add(sponsor);
			}
			Sponsor savedSponsor = sponsorJpaRepository.save(sponsor);
			eventJpaRepository.saveAll(events);
			return savedSponsor;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Sponsor updateSponsor(Sponsor sponsor, int id) {
		try {
			Sponsor newSponsor = sponsorJpaRepository.findById(id).get();
			if (sponsor.getSponsorName() != null) {
				newSponsor.setSponsorName(sponsor.getSponsorName());
			}
			if (sponsor.getIndustry() != null) {
				newSponsor.setIndustry(sponsor.getIndustry());
			}
			if (sponsor.getEvents() != null) {
				List<Event> events = newSponsor.getEvents();
				for (Event event : events) {
					event.getSponsors().remove(newSponsor);
				}
				eventJpaRepository.saveAll(events);

				List<Integer> newEventIds = new ArrayList<>();
				for (Event event : sponsor.getEvents()) {
					newEventIds.add(event.getEventId());
				}
				List<Event> newEvents = eventJpaRepository.findAllById(newEventIds);
				if (newEvents.size() != newEventIds.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}
				for (Event event : newEvents) {
					event.getSponsors().add(newSponsor);
				}
				eventJpaRepository.saveAll(newEvents);
				newSponsor.setEvents(newEvents);
			}
			return sponsorJpaRepository.save(newSponsor);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteSponsor(int id) {
		try {
			Sponsor sponsor = sponsorJpaRepository.findById(id).get();

			List<Event> events = sponsor.getEvents();
			for (Event event : events) {
				event.getSponsors().remove(sponsor);
			}
			eventJpaRepository.saveAll(events);
			sponsorJpaRepository.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Event> getSponsorevents(int id) {
		Sponsor sponsor = sponsorJpaRepository.findById(id).get();
		List<Event> events = sponsor.getEvents();
		return events;
	}

}