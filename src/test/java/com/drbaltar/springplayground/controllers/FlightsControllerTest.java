package com.drbaltar.springplayground.controllers;

import com.drbaltar.springplayground.models.Flight;
import com.drbaltar.springplayground.models.Person;
import com.drbaltar.springplayground.models.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightsController.class)
public class FlightsControllerTest {

    @Autowired
    MockMvc mvc;

    private Flight getTestFlight() {
        Person testPerson1 = new Person();
        testPerson1.setFirstName("Some name");
        testPerson1.setLastName("Some other name");
        Person testPerson2 = new Person();
        testPerson2.setFirstName("Name B");
        testPerson2.setLastName("Name C");
        Ticket ticket1 = new Ticket(testPerson1, 200);
        Ticket ticket2 = new Ticket(testPerson2, 150);

        var flight = new Flight();
        flight.addTicket(ticket1);
        flight.addTicket(ticket2);

        return flight;
    }

    @Nested
    class Flights {

        @Test
        public void testCreatPersonJSONClass() throws Exception {
            mvc.perform(
                            get("/flights/flight")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.tickets[0].passenger.firstName", is(notNullValue())))
                    .andExpect(jsonPath("$.tickets[0].passenger.lastName", is(notNullValue())))
                    .andExpect(jsonPath("$.tickets[0].price", is(notNullValue())))
                    .andExpect(jsonPath("$.departs", is(notNullValue())));
        }

        @Test
        public void testListOfFlights() throws Exception {
            mvc.perform(
                            get("/flights")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[1].tickets[0].passenger.firstName", is(notNullValue())))
                    .andExpect(jsonPath("$[1].tickets[0].price", is(notNullValue())));
        }

        @Disabled
        @Test
        public void testCreatPersonJSONClassWithCaps() throws Exception {
            mvc.perform(
                            get("/flights/flight")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.Tickets[0].Passenger.FirstName", is(notNullValue())))
                    .andExpect(jsonPath("$.Tickets[0].Passenger.LastName", is(notNullValue())))
                    .andExpect(jsonPath("$.Tickets[0].Price", is(notNullValue())))
                    .andExpect(jsonPath("$.Departs", is(notNullValue())));
        }

        @Disabled
        @Test
        public void testListOfFlightsWithCaps() throws Exception {
            mvc.perform(
                            get("/flights")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[1].Tickets[0].Passenger.FirstName", is(notNullValue())))
                    .andExpect(jsonPath("$[1].Tickets[0].Passenger", not(hasProperty("LastName"))))
                    .andExpect(jsonPath("$[1].Tickets[0].Price", is(notNullValue())));

        }
    }

    @Nested
    class CalculateTicketTotals {
        @Test
        void shouldReturnTotalWhenStringLiteralPassed() throws Exception {
            RequestBuilder request = post("/flights/tickets/total")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                              {
                                "tickets": [
                                  {
                                    "passenger": {
                                      "firstName": "Some name",
                                      "lastName": "Some other name"
                                    },
                                    "price": 200
                                  },
                                  {
                                    "passenger": {
                                      "firstName": "Name B",
                                      "lastName": "Name C"
                                    },
                                    "price": 150
                                  }
                                ]
                              }
                            """);

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result", is(350)));
        }

        @Test
        void shouldReturnTotalWhenUsingObjectMapper() throws Exception {
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(getTestFlight());
            RequestBuilder request = post("/flights/tickets/total")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json);

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result", is(350)));
        }

        @Test
        void shouldReturnTotalWhenUsingJSONFile() throws Exception {
            String json = getJSON("/tickets.json");

            RequestBuilder request = post("/flights/tickets/total")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json);

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result", is(350)));
        }

        private String getJSON(String path) throws Exception {
            URL url = this.getClass().getResource(path);
            return new String(Files.readAllBytes(Paths.get(url.getFile())));
        }

        @Test
        void shouldReturnTotalForMultipleFlights() throws Exception {
            var scheduledFlights = new ArrayList<Flight>();
            scheduledFlights.add(getTestFlight());
            scheduledFlights.add(getTestFlight());

            var mapper = new ObjectMapper();

            var json = mapper.writeValueAsString(scheduledFlights);
            RequestBuilder request = post("/flights/totals")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json);

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result", is(700)));
        }

        @Test
        void shouldReturnTotalWhenStringLiteralPassedForMultipleFlights() throws Exception {
            RequestBuilder request = post("/flights/totals")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                              [{
                                "tickets": [
                                  {
                                    "passenger": {
                                      "firstName": "Some name",
                                      "lastName": "Some other name"
                                    },
                                    "price": 200
                                  },
                                  {
                                    "passenger": {
                                      "firstName": "Name B",
                                      "lastName": "Name C"
                                    },
                                    "price": 150
                                  }
                                ]
                              },
                              {
                                "tickets": [
                                  {
                                    "passenger": {
                                      "firstName": "Some name",
                                      "lastName": "Some other name"
                                    },
                                    "price": 200
                                  },
                                  {
                                    "passenger": {
                                      "firstName": "Name B",
                                      "lastName": "Name C"
                                    },
                                    "price": 150
                                  }
                                ]
                              }]
                            """);

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result", is(700)));
        }
    }
}

