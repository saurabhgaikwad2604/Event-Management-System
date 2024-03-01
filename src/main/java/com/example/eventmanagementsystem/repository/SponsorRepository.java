package com.example.eventmanagementsystem.repository;

import com.example.eventmanagementsystem.model.*;
import java.util.*;

public interface SponsorRepository {
    ArrayList<Sponsor> getSponsors();

    Sponsor getSponsorById(int id);

    Sponsor addSponsor(Sponsor sponsor);

    Sponsor updateSponsor(Sponsor sponsor, int id);

    void deleteSponsor(int id);

    List<Event> getSponsorevents(int id);
}