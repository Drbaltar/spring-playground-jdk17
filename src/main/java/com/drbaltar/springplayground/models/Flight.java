package com.drbaltar.springplayground.models;

import com.drbaltar.springplayground.FlightViews;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.Calendar;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flight {
    @JsonView(FlightViews.PriceView.class)
    ArrayList<Ticket> tickets = new ArrayList<>();
    Calendar departs;

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public int calculateTicketTotals() {
        var total = 0;

        for (Ticket ticket : tickets)
            total += ticket.getPrice();

        return total;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "America/Chicago")
    public Calendar getDeparts() {
        return departs;
    }

    public void setDeparts(Calendar departs) {
        this.departs = departs;
    }
}