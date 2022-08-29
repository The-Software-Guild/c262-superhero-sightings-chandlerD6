package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Location;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LocationDaoDBTest {
    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locDao;



    public LocationDaoDBTest(){

    }

    @BeforeAll
    public static void setUpClass(){

    }

    @AfterAll
    public static void tearDownClass(){

    }

    @BeforeEach
    public void setUp() {

        List<Location> locs = locDao.getAllLocations();
        for(Location loc : locs){
            locDao.deleteLocationById(loc.getLocId());
        }

        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero hero : heroes){
            heroDao.deleteHeroById(hero.getHeroId());
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void testAddAndGetLocation(){
        Location loc = new Location();
        loc.setLocName("New York");
        loc.setLocAddress("12345 madeup street");
        loc.setLocDescription("A beautiful town home");
        loc.setLocLat(36.09823f);
        loc.setLocLong(100.30976f);
        loc = locDao.addLocation(loc);

        Location fromDao = locDao.getLocationById(loc.getLocId());

        assertEquals(loc, fromDao);
    }
}
