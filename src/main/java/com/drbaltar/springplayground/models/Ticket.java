package com.drbaltar.springplayground.models;

import com.drbaltar.springplayground.FlightViews;
import com.fasterxml.jackson.annotation.JsonView;

public class Ticket {
    private final Person passenger;
    @JsonView(FlightViews.PriceView.class)
    private final double price;

    public Ticket(Person passenger, double price) {
        this.passenger = passenger;
        this.price = price;

    }

    public Person getPassenger() {
        return passenger;
    }

    public double getPrice() {
        return price;
    }
}