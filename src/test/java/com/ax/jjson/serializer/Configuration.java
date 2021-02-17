package com.ax.jjson.serializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Configuration {

    protected Player createPlayerInstance() {
        return new Player(1L, "Alex", "alex@mymail.com");
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

    protected Set<Player> createSetOfPlayers() {
        Set<Player> players = new HashSet<>(3);

        players.add(new Player(1L, "Alex", "alex@mail.com"));
        players.add(new Player(2l, "Mike", "mike@mail.com"));
        players.add(new Player(3L, "John", "john@mail.com"));

        return players;
    }

    protected OnlineGame createOnlineGameInstance() {
        return new OnlineGame(1L, "Java Game", createSetOfPlayers());
    }

    @Data
    @AllArgsConstructor
    protected static class Player {
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class OnlineGame {
        private long id;
        private String name;
        private Set<Player> players;
    }
}
