package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Sighting;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SightingDaoDBTest {
    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    SightingDao sightingDao;



    public SightingDaoDBTest(){

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

        List<Sighting> sightings = sightingDao.getAllSighting();
        for (Sighting sighting : sightings){
            sightingDao.deleteSightingById(sighting.getSightingId());
        }

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void testAddAndGetSighting(){
        Location loc = new Location();
        loc.setLocName("New York");
        loc.setLocAddress("12345 madeup street");
        loc.setLocDescription("A beautiful town home");
        loc.setLocLat(36.09823f);
        loc.setLocLong(100.30976f);
        loc = locDao.addLocation(loc);

        Location fromDao = locDao.getLocationById(loc.getLocId());

        Hero hero = new Hero();
        hero.setHeroName("Thor");
        hero.setHeroDescription("The god of thunder");
        hero.setPower("Lightning");
        hero = heroDao.addHero(hero);

        Hero fromDao2 = heroDao.getHeroById(hero.getHeroId());



        Sighting sighting = new Sighting();
        sighting.setHeroId(hero.getHeroId());
        sighting.setLocId(loc.getLocId());
        sighting.setSightingDate(new Timestamp(System.currentTimeMillis()));
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao3 = sightingDao.getSightingById(sighting.getSightingId());

        assertEquals(hero, fromDao2);
        assertEquals(loc, fromDao);
        assertEquals(sighting, fromDao3);

    }
}
