package com.ax.jjson.serializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Configuration {

    protected Customer createCustomerInstance() {
        return new Customer(1L, "Alex", "alex@mymail.com");
    }

    protected City createCityInstance1() {
        City city = new City();
        city.setId(1L);
        city.setCityName("Bucharest");
        city.setCountry("Romania");

        String visit1 = "The Arc of Tryumph";
        String visit2 = "Cismigiu Gardens";
        String visit3 = "The Romanian Athenaeum";

        List<String> visitList = new ArrayList<>(3);
        visitList.add(visit1);
        visitList.add(visit2);
        visitList.add(visit3);

        city.setVisits(visitList);

        return city;
    }

    protected Set<Customer> createLisOfCustomers() {
        Set<Customer> customers = new HashSet<>(3);

        customers.add(new Customer(1L, "Alex", "alex@mail.com"));
        customers.add(new Customer(2l, "Mike", "mike@mail.com"));
        customers.add(new Customer(3L, "John", "john@mail.com"));

        return customers;
    }



    @Data
    @AllArgsConstructor
    protected static class Customer {
        private long id;
        private String name;
        private String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class City {
        private long id;
        private String cityName;
        private List<String> visits;
        private String country;
    }
}
