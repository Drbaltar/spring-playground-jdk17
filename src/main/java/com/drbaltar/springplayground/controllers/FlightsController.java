package com.drbaltar.springplayground.controllers;

import com.drbaltar.springplayground.models.Flight;
import com.drbaltar.springplayground.models.Person;
import com.drbaltar.springplayground.models.Ticket;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightsController {

    @GetMapping("/flight")
    public Flight getFlight() {
        return getTestFlight("John", "Wilson", 400);
    }

    @GetMapping
    public List<Flight> getFlights() {
        return getTestFlightList();
    }

    @PostMapping("/tickets/total")
    public CalculationResults getTicketTotal(@RequestBody Flight flight) {
        return new CalculationResults(flight.calculateTicketTotals());
    }

    @PostMapping("/totals")
    public CalculationResults getTotalCostFlights(@RequestBody ArrayList<Flight> flights) {
        var total = flights.parallelStream().reduce(0, (subtotal, flight) -> subtotal + flight.calculateTicketTotals(), Integer::sum);

//        var total = 0;
//
//        for (Flight flight : flights)
//            total += flight.calculateTicketTotals();

        return new CalculationResults(total);
    }

    private Flight getTestFlight(String firstName, String lastName, int price) {
        Person passenger = new Person();
        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);

        Calendar departs = Calendar.getInstance();
        departs.set(2021, Calendar.AUGUST, 1);


        Flight testflight = new Flight();
        testflight.setDeparts(departs);
        testflight.addTicket(new Ticket(passenger, price));

        return testflight;
    }

    private List<Flight> getTestFlightList() {
        List<Flight> flights = new ArrayList<>();

        flights.add(getTestFlight("John", "Wilson", 400));
        flights.add(getTestFlight("David", null, 200));

        return flights;
    }

    record CalculationResults(int result) {
    }
}
