package com.drbaltar.springplayground.controllers;

import com.drbaltar.springplayground.FlightViews;
import com.drbaltar.springplayground.models.Flight;
import com.drbaltar.springplayground.models.Person;
import com.drbaltar.springplayground.models.Ticket;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class PlaygroundController {

    @GetMapping("/view/flight/priceOnly")
    @JsonView(FlightViews.PriceView.class)
    public Flight getFlightWithTicketPriceOnly() {
        return getTestFlight();
    }

    private Flight getTestFlight() {
        Person passenger = new Person();
        passenger.setFirstName("Kyle");
        passenger.setLastName("McCain");

        Calendar departs = Calendar.getInstance();
        departs.set(2021, Calendar.AUGUST, 1);


        Flight testflight = new Flight();
        testflight.setDeparts(departs);
        testflight.addTicket(new Ticket(passenger, 550));

        return testflight;
    }
}
